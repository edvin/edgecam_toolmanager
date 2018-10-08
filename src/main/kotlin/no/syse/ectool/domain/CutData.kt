package no.syse.ectool.domain

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.util.StringConverter
import tornadofx.*

class CutData {
    enum class Application(val id: Int) {
        Finishing(4),
        Roughing(3);

        object Converter : StringConverter<Application>() {
            override fun toString(application: Application?) = application?.name
            override fun fromString(string: String?) = string?.let { Application.values().find { it.name == string } }
        }
    }

    val idProperty = SimpleObjectProperty<Int>()
    var id by idProperty

    val applicationProperty = SimpleObjectProperty<Application>()
    var application by applicationProperty

    val toolProperty = SimpleObjectProperty<Tool>()
    var tool by toolProperty

    val materialProperty = SimpleObjectProperty<Material>()
    var material by materialProperty

    val feedrateProperty = SimpleObjectProperty<Double>()
    var feedrate by feedrateProperty

    val plungeFeedrateProperty = SimpleObjectProperty<Double>()
    var plungeFeedrate by plungeFeedrateProperty

    val speedProperty = SimpleObjectProperty<Int>()
    var speed by speedProperty

    val stepoverProperty = SimpleObjectProperty<Double>()
    var stepover by stepoverProperty

    fun setApplicationId(id: Int) {
        application = Application.values().find { it.id == id }
    }

    fun getApplicationId() = application.id


    override fun toString(): String {
        return "CutData(id=$id, application=${application.name}, tool=${tool.description}, material=${material.description}, feedrate=$feedrate, plungeFeedrate=$plungeFeedrate, speed=$speed, stepover=$stepover)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CutData

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

// We need to support passing in ToolModel so we can get live changes for the speed calculations
class CutDataModel(var toolModel: ToolModel? = null) : ItemViewModel<CutData>() {
    val id = bind(CutData::idProperty)
    val application = bind(CutData::applicationProperty)
    val tool = bind(CutData::toolProperty)
    val material = bind(CutData::materialProperty)
    val feedrate = bind(CutData::feedrateProperty)
    val plungeFeedrate = bind(CutData::plungeFeedrateProperty)
    val speed = bind(CutData::speedProperty)
    val stepover = bind(CutData::stepoverProperty)

    val vc = SimpleDoubleProperty()

    init {
        setVcFromSpeed()
    }

    private val d: Double get() = toolModel?.item?.diameter ?: tool.value?.diameter ?: 1.0

    fun setVcFromSpeed() {
        vc.value = Math.round(Math.PI * d * speed.value / 1000).toDouble()
    }

    fun setSpeedFromVc() {
        speed.value = Math.round((vc.value ?: 0.0 * 12) / (Math.PI * d) * 1000).toInt()
    }
}
