package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import no.syse.ectool.app.Cache
import no.syse.ectool.app.ToolApp
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.CutData
import no.syse.ectool.domain.CutDataModel
import no.syse.ectool.domain.Material
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorFeedsAndSpeeds : Fragment("Feeds and Speeds") {
    val db: DBController by inject()
    val tool: ToolModel by inject()
    val currentCutData = CutDataModel()
    val currentMaterial = SimpleObjectProperty<Material>()
    val cutdataList = FXCollections.observableArrayList<CutData>()

    override fun onTabSelected() {
        cutdataList.asyncItems { db.listCutDataForTool(tool.item) }
    }

    init {
        currentMaterial.value = Cache.materials.first()
    }

    //mill_feed_type_mm_min.png
    //mill_feed_type_mm_rev.png
    override val root = form {
        fieldset {
            field("Material") {
                combobox(currentMaterial, Cache.materials)
            }
            field("VC") {
                spinner(0.0, 120.0, property = tool.diameter, enableScroll = true, amountToStepBy = 1.0) {
                    isEditable = true
                    prefWidth = 70.0
//                    helpIcon("diameter.png")

                }
                label(tool.mmOrInch)
            }
        }
    }
}