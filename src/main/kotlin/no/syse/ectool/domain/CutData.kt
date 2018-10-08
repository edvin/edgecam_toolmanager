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
            override fun fromString(string: String?) = string?.let { Application.values().find { a -> a.name == string } }
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

    val feedrateProperty = SimpleDoubleProperty()
    var feedrate by feedrateProperty

    val plungeFeedrateProperty = SimpleDoubleProperty()
    var plungeFeedrate by plungeFeedrateProperty

    val speedProperty = SimpleDoubleProperty()
    var speed by speedProperty

    val stepoverProperty = SimpleDoubleProperty()
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
class CutDataModel(cutData: CutData, var toolModel: ToolModel? = null) : ItemViewModel<CutData>(cutData) {
    val id = bind(CutData::idProperty)
    val application = bind(CutData::applicationProperty)
    val tool = bind(CutData::toolProperty)
    val material = bind(CutData::materialProperty)
    val feedrate = bind(CutData::feedrateProperty)
    val plungeFeedrate = bind(CutData::plungeFeedrateProperty)
    val speed = bind(CutData::speedProperty)
    val stepover = bind(CutData::stepoverProperty)

    private val _vc = SimpleDoubleProperty()
    private val _fz = SimpleDoubleProperty()

    val vc = bind { _vc }
    val fz = bind { _fz }

    init {
        // Add to list of modified cut data so we can save them when the tool is saved
        dirty.onChange {
            if (it && toolModel?.hasModifiedCutDataFor(item) == false) {
                toolModel?.modifiedCutData?.add(item)
            }
        }

        itemProperty.onChange {
            setVcFromSpeed()
            setFzFromFeedRate()
        }
    }

    private val d: Double get() = toolModel?.item?.diameter ?: tool.value?.diameter ?: 1.0
    private val teeth: Int get() = toolModel?.item?.teeth ?: tool.value?.teeth ?: 1

    fun setFzFromFeedRate() {
        _fz.value = roundDouble(feedrate.value.toDouble()  / (speed.value.toDouble() * teeth), 4)
    }

    fun setVcFromSpeed() {
        _vc.value = Math.round(Math.PI * d * speed.value.toDouble() / 1000).toDouble()
    }

    fun setSpeedFromVc() {
        speed.value = Math.round((vc.value?.toDouble() ?: 0.0 * 12) / (Math.PI * d) * 1000).toDouble()
    }

    fun setFeedRateFromFz() {
        feedrate.value = Math.round(speed.value.toDouble() * fz.value.toDouble() * teeth).toDouble()
    }
}
