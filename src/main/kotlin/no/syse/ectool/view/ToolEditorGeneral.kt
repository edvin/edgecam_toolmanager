package no.syse.ectool.view

import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorGeneral : View("General") {
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
            field("Comment") {
                textfield(tool.comment)
            }
        }
    }
}