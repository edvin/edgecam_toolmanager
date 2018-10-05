package no.syse.ectool.view

import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorTechnology : Fragment("Technology") {
    val tool: ToolModel by inject()

    override val root = form {
        fieldset {
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

            field("Description") {
                textfield(tool.description)
            }
            field("Manufacturer") {
                textfield(tool.manufacturer)
            }
            field("Item ID") {
                textfield(tool.itemId)
            }
            field("URL") {
                textfield(tool.url)
            }
        }
    }
}