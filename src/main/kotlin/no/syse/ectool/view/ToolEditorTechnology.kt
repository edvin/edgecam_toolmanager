package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.converter.IntegerStringConverter
import no.syse.ectool.app.ToolApp
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*


class ToolEditorTechnology : View("Technology") {
    val tool: ToolModel by inject()

    private val helpImage = SimpleObjectProperty<Image>()

    private val enableTipEditing = booleanBinding(tool, tool.millType, tool.holeType) {
        listOf(Tool.MillType.FormMill, Tool.MillType.ThreadMill, Tool.MillType.Taper).contains(millType.value)
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
                                            text = it.description
                                            graphic = ToolApp.icon(it, 24)
                                        }
                                        enhanceAndHelp(helpImage, "Tool_type_mill.png")
                                        requestFocus()
                                    }
                                }
                                Tool.Category.Hole -> {
                                    combobox(tool.holeType, Tool.HoleType.values().toList()) {
                                        cellFormat(true) {
                                            text = it.name
                                            graphic = ToolApp.icon(it, 24)
                                        }
                                        enhanceAndHelp(helpImage, "Tool_type_hole.png")
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
                                enhanceAndHelp(helpImage, "number_of_teeth.png")
                            }
                        }
                        field("Roughing") {
                            checkbox(property = tool.roughing) {
                                enhanceAndHelp(helpImage, "roughing_tool.png")
                            }
                        }
                        field("Finishing") {
                            checkbox(property = tool.finishing) {
                                enhanceAndHelp(helpImage, "finishing_tool.png")
                            }
                        }
                    }
                    row {
                        field("Units") {
                            combobox(tool.units, Tool.Units.values().toList()) {
                                enhanceAndHelp(helpImage, "blank.png")
                            }
                        }
                        field("Coolant") {
                            checkbox(property = tool.coolant) {
                                enhanceAndHelp(helpImage, "coolant_flood.png")
                            }
                        }
                        field("ATC pos / Offset") {
                            spinner(1, 12, property = tool.turretPosition, enableScroll = true, amountToStepBy = 1) {
                                isEditable = true
                                prefWidth = 70.0
                                enhanceAndHelp(helpImage, "code_id.png")
                            }
                            textfield(tool.offset, IntegerStringConverter()) {
                                prefColumnCount = 2
                                enhanceAndHelp(helpImage, "mill_offset.png")
                            }
                        }
                        field("Centre cutting") {
                            checkbox(property = tool.centreCutting) {
                                enhanceAndHelp(helpImage, "centre_cutting_tool.png", "Mill")
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
                borderWidth += box(1.px)
                borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT)
            }
        }
        fieldset("Geometry") {
            paddingTop = 10
            hbox(30) {
                vbox {
                    field("Diameter") {
                        spinner(0.0, 120.0, property = tool.diameter, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "diameter.png")

                        }
                        label(tool.mmOrInch)
                    }
                    field("Corner Radius") {
                        spinner(0.0, 5.0, property = tool.cornerRadius, enableScroll = true, amountToStepBy = 0.1) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "corner_radius.png")
                        }
                        label(tool.mmOrInch)
                        enableWhen(enableCornerRadius)
                    }
                    field("Flute length") {
                        spinner(1.0, 200.0, property = tool.fluteLength, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "flute_length.png")
                        }
                        label(tool.mmOrInch)
                    }
                    field("Shank length") {
                        spinner(1.0, 200.0, property = tool.shankLength, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "shank_definition_shank_length.png")
                        }
                        label(tool.mmOrInch)
                    }
                    field("Shank width") {
                        spinner(1.0, 120.0, property = tool.shankWidth, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "shank_definition_diameter.png")
                        }
                        label(tool.mmOrInch)
                    }
                }
                vbox {
                    field("Reach") {
                        spinner(1.0, 200.0, property = tool.reach, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "reach.png")
                        }
                        label(tool.mmOrInch)
                    }
                    field("Gauge height") {
                        spinner(0, 300, property = tool.gaugeZ, enableScroll = true, amountToStepBy = 1) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "mill_Z_gauge.png")
                        }
                        label(tool.mmOrInch)
                    }
                    field("Tip angle") {
                        spinner(0.0, 180.0, property = tool.tipAngle, enableScroll = true, amountToStepBy = 1.0) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "tip_angle.png")
                        }
                        label("°")
                        enableWhen(enableTipEditing)
                    }
                    field("Tip length") {
                        (inputContainer as HBox).alignment = Pos.CENTER_LEFT
                        label(tool.tipLength)
                        label(tool.mmOrInch)
                        enableWhen(enableTipEditing)
                    }
                    field("Thread pitch") {
                        spinner(0.0, 100.0, property = tool.threadPitch, enableScroll = true, amountToStepBy = 0.1) {
                            isEditable = true
                            prefWidth = 70.0
                            enhanceAndHelp(helpImage, "thread_pitch.png")
                        }
                        label(tool.mmOrInch)
                        enableWhen(enableThreadPitch)
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

}
