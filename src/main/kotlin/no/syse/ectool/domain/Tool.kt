package no.syse.ectool.domain

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.Group
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import tornadofx.*

class Tool {
    enum class Category(val id: Int, val iconName: String, val library: String) {
        Milling(0, "end_mill.png", "Tool"),
        Turning(1, "TurnPart.png", "FeatureFinder"),
        Hole(2, "drill.png", "Tool"),
        Probe(3, "tool_type_probe.png", "Tool")
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

    enum class MillType(val id: Int, val iconName: String, val description: String) {
        Endmill(0, "end_mill.png", "Endmill"),
        Bullnose(1, "bull_nose.png", "Bullnose"),
        Ballnose(2, "ball_nose.png", "Ballnose"),
        SlotDrill(3, "slot_drill.png", "Slot Drill"),
        Taper(4, "tap_taper_angle.png", "Taper"),
        Facemill(5, "face_mill.png", "Facemill"),
        TSlot(6, "T_slot.png", "T-Slot"),
        Lollipop(7, "lollipop.png", "Lollipop"),
        ThreadMill(8, "tap.png", "Threadmill"),
        FormMill(9, "small_diameter.png", "Formed Mill")
    }

    enum class Units(val id: Int) {
        Inches(0),
        Millimeters(1)
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

    val zoffsetProperty = SimpleObjectProperty<Int>(1)
    var zoffset by zoffsetProperty

    val reachProperty = SimpleDoubleProperty()
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

    val mmOrInch = unitsProperty.stringBinding { if (it == Tool.Units.Inches) "\"" else "mm" }

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
    // Reflect changes to mmOrInch directly
    val units = bind(Tool::unitsProperty, autocommit = true)
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
    val mmOrInch = select { it.mmOrInch }

    val tipLength = doubleBinding(tipAngle, diameter) {
        val angle = (tipAngle.value ?: 0.0).toDouble()
        if (angle > 0) {
            val d = diameter.value?.toDouble() ?: 0.0
            val radius: Double = d / 2.0
            round(radius / Math.tan(Math.toRadians(angle)), 3)
        } else {
            0.0
        }
    }

    private fun round(value: Double, places: Int): Double {
        var v = value
        if (places < 0) throw IllegalArgumentException()

        val factor = Math.pow(10.0, places.toDouble()).toLong()
        v = v * factor
        val tmp = Math.round(v)
        return tmp.toDouble() / factor
    }

    val toolGraphics = objectBinding(this, diameter, fluteLength, shankLength, shankWidth, tipAngle) {
        toolGraphics()
    }

    private fun toolGraphics() = Group().apply {
        val frameHeight = 190
        val frameWidth = 200
        val hcenter = frameWidth / 2

        // Determine scale factor

        // Scale values
        val flD = (diameter.value ?: 1).toDouble()
        val shD = (shankWidth.value ?: 1).toDouble()
        val flLength = (fluteLength.value ?: 1).toDouble()
        val shLength = (shankLength.value ?: 1).toDouble()

        // Shank
        rectangle(hcenter - (shD / 2), 0, shD, shLength) {
            val stops = listOf(
                    Stop(0.0, c("#b1b1b1")),
                    Stop(0.01, c("#d2d2d2")),
                    Stop(0.02, c("#f3f3f3")),
                    Stop(0.03, c("#ffffff")),
                    Stop(0.8, c("#858585")),
                    Stop(1.0, c("#dbdbdb"))
            )
            fill = LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, stops)
        }

        val stops = listOf(
                Stop(0.0, c("#2f77c7")),
                Stop(0.01, c("#5aa0df")),
                Stop(0.02, c("#84c9f6")),
                Stop(0.03, c("#84c9f6")),
                Stop(0.8, c("#145cac")),
                Stop(1.0, c("#1986fe"))
        )
        val fluteFill = LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, stops)

        // Flutes
        rectangle(hcenter - (flD / 2), shLength, flD, flLength) {
            fill = fluteFill
        }

        // Tip angle
        val tipH = tipLength.value ?: 0.0

        if (tipH > 0) {
            val y = shLength + flLength

            polygon(hcenter - (flD / 2), y, hcenter, y + tipH, hcenter + (flD / 2), y) {
                fill = fluteFill
            }
        }

        // Scale to fit the frame while keeping aspect ratio
        val totalHeight = flLength + shLength + tipH
        val maxWidth = Math.max(flD, shD)

        val scaleFactor = Math.min(frameHeight / totalHeight, frameWidth / maxWidth)

        scaleX = scaleFactor
        scaleY = scaleFactor
    }
}

class ToolQuery() {
    var category: Tool.Category? = null
    val millTypes = FXCollections.observableSet<Tool.MillType>()
}
