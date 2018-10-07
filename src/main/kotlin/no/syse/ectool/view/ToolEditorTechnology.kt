package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.converter.IntegerStringConverter
import no.syse.ectool.app.ToolApp
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorTechnology : Fragment("Technology") {
    val tool: ToolModel by inject()

    private val helpImage = SimpleObjectProperty<Image>()

    private val enableTipEditing = booleanBinding(tool, tool.millType, tool.holeType) {
        listOf(Tool.MillType.FormMill, Tool.MillType.ThreadMill).contains(millType.value)
                || holeType.value != null
    }

    private val enableCornerRadius = booleanBinding(tool, tool.millType, tool.holeType) {
        millType.value != null
    }

    private val enableThreadPitch = booleanBinding(tool, tool.millType, tool.holeType) {
        listOf(Tool.MillType.FormMill, Tool.MillType.ThreadMill).contains(millType.value)
                || holeType.value == Tool.HoleType.Tap
    }

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            hbox(40) {
                gridpane {
                    hgap = 30.0
                    row {
                        field("${tool.category.value} Type") {
                            when (tool.category.value) {
                                Tool.Category.Milling -> {
                                    combobox(tool.millType, Tool.MillType.values().toList()) {
                                        cellFormat(true) {
                                            text = it.name
                                            graphic = ToolApp.icon(it, 24)
                                        }
                                        helpIcon("Tool_type_mill.png")
                                        requestFocus()
                                    }
                                }
                                Tool.Category.Hole -> {
                                    combobox(tool.holeType, Tool.HoleType.values().toList()) {
                                        cellFormat(true) {
                                            text = it.name
                                            graphic = ToolApp.icon(it, 24)
                                        }
                                        helpIcon("Tool_type_hole.png")
                                        requestFocus()
                                    }
                                }
                                Tool.Category.Probe -> {
                                    label("")
                                }
                            }

                        }
                        field("Flutes") {
                            spinner(1, 12, property = tool.teeth, enableScroll = true) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("number_of_teeth.png")
                            }
                        }
                        field("Roughing") {
                            checkbox(property = tool.roughing) {
                                helpIcon("roughing_tool.png")
                            }
                        }
                        field("Finishing") {
                            checkbox(property = tool.finishing) {
                                helpIcon("finishing_tool.png")
                            }
                        }
                    }
                    row {
                        field("Units") {
                            combobox(tool.units, Tool.Units.values().toList()) {
                                helpIcon("blank.png")
                            }
                        }
                        field("Coolant") {
                            checkbox(property = tool.coolant) {
                                helpIcon("coolant_flood.png")
                            }
                        }
                        field("ATC pos / Offset") {
                            spinner(1, 12, property = tool.turretPosition, enableScroll = true, amountToStepBy = 1) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("code_id.png")
                            }
                            textfield(tool.offset, IntegerStringConverter()) {
                                prefColumnCount = 2
                                helpIcon("mill_offset.png")
                            }
                        }
                        field("Centre cutting") {
                            checkbox(property = tool.centreCutting) {
                                helpIcon("centre_cutting_tool.png", "Mill")
                            }
                        }
                    }
                }

                imageview(helpImage) {
                    fitHeight = 128.0
                    fitWidth = 128.0
                }
            }
        }
        pane {
            style {
                padding = box(2.px, 0.px, 10.px, 0.px)
                borderWidth += box(1.px)
                borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT)
            }
        }
        fieldset("Geometry") {
            hbox(10) {
                gridpane {
                    hgap = 40.0
                    row {
                        field("Diameter") {
                            spinner(0.0, 120.0, property = tool.diameter, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("diameter.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Reach") {
                            spinner(1.0, 200.0, property = tool.reach, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("reach.png")
                            }
                            label(tool.mmOrInch)
                        }
                    }
                    row {
                        field("Corner Radius") {
                            spinner(0.0, 5.0, property = tool.cornerRadius, enableScroll = true, amountToStepBy = 0.1) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("corner_radius.png")
                            }
                            label(tool.mmOrInch)
                            enableWhen(enableCornerRadius)
                        }
                        field("Gauge Z") {
                            spinner(0, 300, property = tool.gaugeZ, enableScroll = true, amountToStepBy = 1) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("mill_Z_gauge.png")
                            }
                            label(tool.mmOrInch)
                        }
                    }
                    row {
                        field("Flute length") {
                            spinner(1.0, 200.0, property = tool.fluteLength, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("flute_length.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Tip angle") {
                            spinner(0.0, 180.0, property = tool.tipAngle, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("tip_angle.png")
                            }
                            label("°")
                            enableWhen(enableTipEditing)
                        }
                    }
                    row {
                        field("Shank length") {
                            spinner(1.0, 200.0, property = tool.shankLength, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("shank_definition_shank_length.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Tip length") {
                            (inputContainer as HBox).alignment = Pos.CENTER_LEFT
                            label(tool.tipLength)
                            label(tool.mmOrInch)
                            enableWhen(enableTipEditing)
                        }
                    }
                    row {
                        field("Shank width") {
                            spinner(1.0, 120.0, property = tool.shankWidth, enableScroll = true, amountToStepBy = 1.0) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("shank_definition_diameter.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Thread pitch") {
                            spinner(0.0, 100.0, property = tool.threadPitch, enableScroll = true, amountToStepBy = 0.1) {
                                isEditable = true
                                prefWidth = 70.0
                                helpIcon("thread_pitch.png")
                            }
                            label(tool.mmOrInch)
                            enableWhen(enableThreadPitch)
                        }
                    }
                }
                stackpane {
                    setMinSize(220.0, 200.0)
                    style {
                        backgroundColor += c("98bedc")
                        borderColor += box(Color.BLACK)
                    }
                    add(tool.toolGraphics.value!!)
                    tool.toolGraphics.onChange {
                        clear()
                        add(it!!)
                    }
                }
            }

        }
    }

    private fun Node.helpIcon(iconName: String, library: String = "Tool") {
        // Trigger help icon on focus
        focusedProperty().onChange {
            helpImage.value = ToolApp.icon(iconName, 32, library).image
        }
        // Focus follows mouse
        setOnMouseEntered {
            requestFocus()
            // Spinner needs a little help to select the text
            ((this as? Spinner<*>)?.childrenUnmodifiable?.first() as? TextField)?.selectAll()
        }
    }
}