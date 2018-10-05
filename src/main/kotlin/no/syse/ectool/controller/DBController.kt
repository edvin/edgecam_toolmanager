package no.syse.ectool.controller

import no.syse.ectool.domain.CutData
import no.syse.ectool.domain.Material
import no.syse.ectool.domain.Tool
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import tornadofx.*

class DBController : Controller() {
    private val mybatisConfig = Resources.getResourceAsStream("maps/mybatis.xml")
    private val sessionFactory = SqlSessionFactoryBuilder().build(mybatisConfig)

    fun updateTool(tool: Tool) {
        session {
            val oldTool = getTool(tool.id)

            update("Tool.updateTool", tool)

            // Ensure an entry for <All Kit> in the mounting table with data taken from the tool
            if (update("Tool.updateAllKitMounting", tool) == 0)
                insert("Tool.insertAllKitMounting", tool)

            // Update reference to non normal entries
            if (tool.description != oldTool.description) {
                update("Tool.updateCutDataReference", mapOf("new" to tool.description, "old" to oldTool.description))
            }

            commit()
        }
    }

    fun deleteTool(tool: Tool) {
        session {
            delete("Tool.deleteMounting", tool)
            delete("CutData.deleteForTool", tool)
            delete("Tool.deleteTool", tool)
            commit()
        }
    }

    fun insertTool(tool: Tool) {
        session {
            update("Tool.insertTool", tool)

            // Ensure an entry for <All Kit> in the mounting table with data taken from the tool
            insert("Tool.insertAllKitMounting", tool)

            commit()
        }
    }

    fun saveCutData(cutData: CutData) {
        session {
            if (update("CutData.update", cutData) == 0)
                insert("CutData.insert", cutData)

            commit()
        }
    }

    fun listCutDataForTool(tool: Tool) = list<CutData>("CutData.listByTool", tool)

    fun listTools() = list<Tool>("Tool.tools")
    fun listMaterials() = list<Material>("Material.materials")

    private inline fun <reified T> list(key: String, param: Any? = null) = session { list<T>(key, param).observable() }
    private inline fun <reified T> SqlSession.list(key: String, param: Any? = null) = selectList<T>(key, param).observable()

    private inline fun <reified T> one(key: String, param: Any? = null) = session { one<T>(key, param) }
    private inline fun <reified T> SqlSession.one(key: String, param: Any? = null) = selectOne<T>(key, param)

    private fun update(key: String, obj: Any): Int = session { update(key, obj) }
    private fun SqlSession.update(key: String, obj: Any): Int = update(key, obj)

    private fun insert(key: String, obj: Any): Int = session { insert(key, obj) }
    private fun SqlSession.insert(key: String, obj: Any): Int = insert(key, obj)

    private fun <T> session(op: SqlSession.() -> T) = sessionFactory.openSession().use(op)

    fun getTool(id: Int) = one<Tool>("Tool.toolById", id)
}