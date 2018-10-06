package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.util.converter.IntegerStringConverter
import javafx.util.converter.NumberStringConverter
import no.syse.ectool.app.ToolApp
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorTechnology : Fragment("Technology") {
    val tool: ToolModel by inject()

    private val helpImage = SimpleObjectProperty<Image>()

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            hbox(40) {
                gridpane {
                    hgap = 30.0
                    row {
                        field("Type") {
                            combobox(tool.millType, Tool.MillType.values().toList()) {
                                cellFormat(true) {
                                    text = it.name
                                    graphic = ToolApp.icon(it, 24)
                                }
                                helpIcon("Tool_type_mill.png")
                                requestFocus()
                            }
                        }
                        field("Flutes") {
                            spinner(1, 12, property = tool.teeth) {
                                isEditable = true
                                prefWidth = 75.0
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
                            textfield(tool.turretPosition, IntegerStringConverter()) {
                                prefColumnCount = 3
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
            hbox(20) {
                gridpane {
                    hgap = 50.0
                    row {
                        field("Diameter") {
                            textfield(tool.diameter, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("diameter.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Gauge Z") {
                            textfield(tool.gaugeZ, IntegerStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("mill_Z_gauge.png")
                            }
                            label(tool.mmOrInch)
                        }
                    }
                    row {
                        field("Corner Radius") {
                            textfield(tool.cornerRadius, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("corner_radius.png")
                            }
                            label(tool.mmOrInch)
                        }
                    }
                    row {
                        field("Flute length") {
                            textfield(tool.fluteLength, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("flute_length.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Reach") {
                            textfield(tool.reach, IntegerStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("reach.png")
                            }
                            label(tool.mmOrInch)
                        }
                    }
                    row {
                        field("Thread pitch") {
                            textfield(tool.threadPitch, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("thread_pitch.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Tip angle") {
                            textfield(tool.tipAngle, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("tip_angle.png")
                            }
                            label("degrees")
                        }
                    }
                    row {
                        field("Shank width") {
                            textfield(tool.shankWidth, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("shank_definition_diameter.png")
                            }
                            label(tool.mmOrInch)
                        }
                        field("Shank length") {
                            textfield(tool.shankLength, NumberStringConverter()) {
                                prefColumnCount = 4
                                helpIcon("shank_definition_shank_length.png")
                            }
                            label(tool.mmOrInch)
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