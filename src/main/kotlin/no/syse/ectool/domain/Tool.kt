package no.syse.ectool.domain

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.image.ImageView
import tornadofx.*
import java.nio.file.Paths

class Tool {
    enum class Category(val id: Int) {
        Milling(0),
        Turning(1),
        Hole(2),
        Probe(3)
    }

    enum class HoleType(val id: Int) {
        Drill(0),
        Ream(1),
        Tap(2),
        Bore(3),
        Countersink(4),
        CentreDrill(5),
        SpotDrill(6),
        BackBore(7),
        FormedHole(8)
    }

    enum class MillType(val id: Int, val iconName: String) {
        Endmill(0, "end_mill.png"),
        Bullnose(1, "bull_nose.png"),
        Ballnose(2, "ball_nose.png"),
        SlotDrill(3, "slot_drill.png"),
        Taper(4, "tap_taper_angle.png"),
        Facemill(5, "face_mill.png"),
        TSlot(6, "T_slot.png"),
        Lollipop(7, "lollipop.png"),
        ThreadMill(8, "tap.png"),
        FormMill(9, "small_diameter.png")
    }

    enum class Units(val id: Int) {
        Inches(0),
        Millimeters(1)
    }

    companion object {
        val millIconBase = Paths.get("C:/Program Files/Vero Software/Edgecam 2018 R2/cam/illustrate/Tool/")
        fun icon(millType: MillType) = ImageView(millIconBase.resolve(millType.iconName).toUri().toURL().toExternalForm()).apply {
            fitHeight = 32.0
            fitWidth = 32.0
        }
    }

    val idProperty = SimpleObjectProperty<Int>()
    var id by idProperty

    val descriptionProperty = SimpleStringProperty()
    var description by descriptionProperty

    val categoryProperty = SimpleObjectProperty<Category>()
    var category by categoryProperty

    val holeTypeProperty = SimpleObjectProperty<HoleType>()
    var holeType by holeTypeProperty

    val millTypeProperty = SimpleObjectProperty<MillType>()
    var millType by millTypeProperty

    val unitsProperty = SimpleObjectProperty<Units>()
    var units by unitsProperty

    val turretPositionProperty = SimpleObjectProperty<Int>()
    var turretPosition by turretPositionProperty

    val gaugeZProperty = SimpleObjectProperty<Int>()
    var gaugeZ by gaugeZProperty

    val zoffsetProperty = SimpleObjectProperty<Int>()
    var zoffset by zoffsetProperty

    val reachProperty = SimpleObjectProperty<Int>()
    var reach by reachProperty

    val roughingProperty = SimpleBooleanProperty()
    var roughing by roughingProperty

    val finishingProperty = SimpleBooleanProperty()
    var finishing by finishingProperty

    val centreCuttingProperty = SimpleBooleanProperty()
    var centreCutting by centreCuttingProperty

    val coolantProperty = SimpleBooleanProperty()
    var coolant by coolantProperty

    val offsetProperty = SimpleObjectProperty<Int>()
    var offset by offsetProperty

    val teethProperty = SimpleObjectProperty<Int>()
    var teeth by teethProperty

    val diameterProperty = SimpleDoubleProperty()
    var diameter by diameterProperty

    val cornerRadiusProperty = SimpleDoubleProperty()
    var cornerRadius by cornerRadiusProperty

    val fluteLengthProperty = SimpleDoubleProperty()
    var fluteLength by fluteLengthProperty

    val threadPitchProperty = SimpleDoubleProperty()
    var threadPitch by threadPitchProperty

    val tipAngleProperty = SimpleDoubleProperty()
    var tipAngle by tipAngleProperty

    val shankLengthProperty = SimpleDoubleProperty()
    var shankLength by shankLengthProperty

    val shankWidthProperty = SimpleDoubleProperty()
    var shankWidth by shankWidthProperty

    val manufacturerProperty = SimpleStringProperty()
    var manufacturer by manufacturerProperty

    val itemIdProperty = SimpleStringProperty()
    var itemId by itemIdProperty

    val urlProperty = SimpleStringProperty()
    var url by urlProperty

    val commentProperty = SimpleStringProperty()
    var comment by commentProperty


    fun setRoughingInt(i: Int?) {
        roughing = i == 1
    }

    fun getRoughingInt() = if (roughingProperty.value == true) 1 else 0

    fun setFinishingInt(i: Int?) {
        finishing = i == 1
    }

    fun getFinishingInt() = if (finishingProperty.value == true) 1 else 0

    fun setCoolantInt(i: Int?) {
        coolant = i == 1
    }

    fun getCoolantInt() = if (coolantProperty.value == true) 1 else 0

    fun setCategoryById(id: Int) {
        category = Category.values().find { it.id == id }
    }

    fun getCategoryById() = category?.id

    fun setUnitsById(id: Int) {
        units = Units.values().find { it.id == id }
    }

    fun getUnitsById() = units.id

    fun setHoleTypeById(id: Int) {
        holeType = HoleType.values().find { it.id == id }
    }

    fun getHoleTypeById() = holeType?.id

    fun setMillTypeById(id: Int) {
        millType = MillType.values().find { it.id == id }
    }

    fun getMillTypeById() = millType?.id

    override fun toString(): String {
        return "Tool(id=$id, description=$description, category=$category, holeType=$holeType, millType=$millType, units=$units, turretPosition=$turretPosition, gaugeZ=$gaugeZ, zoffset=$zoffset, reach=$reach, roughing=$roughing, finishing=$finishing, coolant=$coolant, offset=$offset, teeth=$teeth, diameter=$diameter, cornerRadius=$cornerRadius, fluteLength=$fluteLength, threadPitch=$threadPitch, tipAngle=$tipAngle, shankLength=$shankLength, shankWidth=$shankWidth, manufacturer=$manufacturer, itemId=$itemId, url=$url, comment=$comment)"
    }
}

class ToolModel(tool: Tool? = null) : ItemViewModel<Tool>(tool) {
    val id = bind(Tool::idProperty)
    val description = bind(Tool::descriptionProperty)
    val category = bind(Tool::categoryProperty)
    val holeType = bind(Tool::holeTypeProperty)
    val millType = bind(Tool::millTypeProperty)
    val units = bind(Tool::unitsProperty)
    val turretPosition = bind(Tool::turretPositionProperty)
    val gaugeZ = bind(Tool::gaugeZProperty)
    val zoffset = bind(Tool::zoffsetProperty)
    val reach = bind(Tool::reachProperty)
    val roughing = bind(Tool::roughingProperty)
    val finishing = bind(Tool::finishingProperty)
    val centreCutting = bind(Tool::centreCuttingProperty)
    val coolant = bind(Tool::coolantProperty)
    val offset = bind(Tool::offsetProperty)
    val teeth = bind(Tool::teethProperty)
    val diameter = bind(Tool::diameterProperty)
    val cornerRadius = bind(Tool::cornerRadiusProperty)
    val fluteLength = bind(Tool::fluteLengthProperty)
    val threadPitch = bind(Tool::threadPitchProperty)
    val tipAngle = bind(Tool::tipAngleProperty)
    val shankLength = bind(Tool::shankLengthProperty)
    val shankWidth = bind(Tool::shankWidthProperty)
    val manufacturer = bind(Tool::manufacturerProperty)
    val itemId = bind(Tool::itemIdProperty)
    val url = bind(Tool::urlProperty)
    val comment = bind(Tool::commentProperty)
}

class ToolQuery() {
    var category: Tool.Category? = null
    val millTypes = FXCollections.observableSet<Tool.MillType>()
}
