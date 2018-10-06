package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Orientation
import javafx.scene.Node
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

    private val mmOrInch = tool.units.stringBinding { if (it == Tool.Units.Inches) "inch" else "mm" }
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
                            }
                        }
                        field("Number of teeth") {
                            spinner(1, 12, property = tool.teeth) {
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
                            combobox(tool.units, Tool.Units.values().toList())
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
                            label(mmOrInch)
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
                paddingVertical = 10
                borderWidth += box(1.px)
                borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT)
            }
        }
        fieldset("Geometry") {
            gridpane {
                hgap = 50.0
                row {
                    field("Diameter") {
                        textfield(tool.diameter, NumberStringConverter()) {
                            prefColumnCount = 4
                        }
                        label(mmOrInch)
                    }
                    field("Gauge Z") {
                        textfield(tool.gaugeZ, IntegerStringConverter()) {
                            prefColumnCount = 4
                        }
                        label(mmOrInch)
                    }
                }
                row {
                    field("Corner Radius") {
                        textfield(tool.cornerRadius, NumberStringConverter()) {
                            prefColumnCount = 4
                            helpIcon("corner_radius.png")
                        }
                        label(mmOrInch)
                    }
                }
                row {
                    field("Flute length") {
                        textfield(tool.fluteLength, NumberStringConverter()) {
                            prefColumnCount = 4
                            helpIcon("flute_length.png")
                        }
                        label(mmOrInch)
                    }
                    field("Z Gauge") {
                        textfield(tool.gaugeZ, IntegerStringConverter()) {
                            prefColumnCount = 4
                            helpIcon("mill_Z_gauge.png")
                        }
                        label(mmOrInch)
                    }
                    field("Reach") {
                        textfield(tool.reach, IntegerStringConverter()) {
                            prefColumnCount = 4
                            helpIcon("mill_Z_gauge.png")
                        }
                        label(mmOrInch)
                    }
                }
                row {
                    field("Thread pitch") {
                        textfield(tool.threadPitch, NumberStringConverter()) {
                            prefColumnCount = 4
                        }
                        label(mmOrInch)
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
                        }
                        label(mmOrInch)
                    }
                    field("Shank length") {
                        textfield(tool.shankLength, NumberStringConverter()) {
                            prefColumnCount = 4
                        }
                        label(mmOrInch)
                    }
                }
            }

        }
    }

    private fun Node.helpIcon(iconName: String, library: String = "Tool") {
        focusedProperty().onChange {
            helpImage.value = ToolApp.icon(iconName, 32, library).image
        }
    }
}