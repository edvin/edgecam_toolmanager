package no.syse.ectool.view

import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorGeneral : Fragment("General") {
    val tool: ToolModel by inject()

    override val root = form {
        fieldset {
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