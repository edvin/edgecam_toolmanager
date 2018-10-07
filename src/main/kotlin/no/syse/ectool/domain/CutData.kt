package no.syse.ectool.domain

import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class CutData {
    enum class Application(val id: Int) {
        Finishing(4),
        Roughing(3)
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

    val speedProperty = SimpleObjectProperty<Double>()
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


}

class CutDataModel : ItemViewModel<CutData>() {
    val id = bind(CutData::idProperty)
    val application = bind(CutData::applicationProperty)
    val tool = bind(CutData::toolProperty)
    val material = bind(CutData::materialProperty)
    val feedrate = bind(CutData::feedrateProperty)
    val plungeFeedrate = bind(CutData::plungeFeedrateProperty)
    val speed = bind(CutData::speedProperty)
    val stepover = bind(CutData::stepoverProperty)
}
