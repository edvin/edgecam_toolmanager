package no.syse.ectool.view

import no.syse.ectool.app.ToolApp
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorFeedsAndSpeeds : Fragment("Feeds and Speeds") {
    val tool: ToolModel by inject()

    //mill_feed_type_mm_min.png
    //mill_feed_type_mm_rev.png
    override val root = form {
        fieldset {
            field("Type") {
                combobox(tool.millType, Tool.MillType.values().toList()) {
                    cellFormat(true) {
                        text = it.name
                        graphic = ToolApp.icon(it, 24)
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