package no.syse.ectool.view

import javafx.geometry.Orientation
import javafx.scene.paint.Color
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorTechnology : Fragment("Technology") {
    val tool: ToolModel by inject()

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            gridpane {
                hgap = 50.0
                row {
                    field("Type") {
                        combobox(tool.millType, Tool.MillType.values().toList()) {
                            cellFormat(true) {
                                text = it.name
                                graphic = Tool.icon(it).apply {
                                    fitHeight = 24.0
                                    fitWidth = 24.0
                                }
                            }
                        }
                    }
                    field("Number of teeth") {
                        spinner(1, 12, property = tool.teeth) {
                            prefWidth = 75.0
                        }
                    }
                }
                row {
                    field("Units") {
                        combobox(tool.units, Tool.Units.values().toList())
                    }
                    field("Coolant") {
                        checkbox(property = tool.coolant)
                    }
                    style {
                        borderWidth += box(2.px)
                        borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT)
                    }
                }
            }
        }
    }
}