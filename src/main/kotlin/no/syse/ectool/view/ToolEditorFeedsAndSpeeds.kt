package no.syse.ectool.view

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.util.converter.DoubleStringConverter
import javafx.util.converter.IntegerStringConverter
import javafx.util.converter.NumberStringConverter
import no.syse.ectool.app.Cache
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.CutData
import no.syse.ectool.domain.CutDataModel
import no.syse.ectool.domain.Material
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorFeedsAndSpeeds : Fragment("Feeds and Speeds") {
    val db: DBController by inject()
    val tool: ToolModel by inject()
    val roughing = CutDataModel(tool)
    val finishing = CutDataModel(tool)
    val currentMaterial = SimpleObjectProperty<Material>()
    val cutdataList = FXCollections.observableArrayList<CutData>()

    override fun onTabSelected() {
        runAsync {
            cutdataList.setAll(db.listCutDataForTool(tool.item))
            setCutDataForCurrentMaterial()
        }
    }

    init {
        currentMaterial.value = Cache.materials.first()
    }

    private fun setCutDataForCurrentMaterial() {
        // Find cut data or create new instance of no profile for the material/application combination exists
        roughing.item = cutdataList
                .firstOrNull { it.material == currentMaterial.value && it.application == CutData.Application.Roughing }
                ?: CutData().apply {
            application = CutData.Application.Roughing
            material = currentMaterial.value
            tool = this@ToolEditorFeedsAndSpeeds.tool.item
        }

        finishing.item = cutdataList
                .firstOrNull { it.material == currentMaterial.value && it.application == CutData.Application.Finishing }
                ?: CutData().apply {
            application = CutData.Application.Finishing
            material = currentMaterial.value
            tool = this@ToolEditorFeedsAndSpeeds.tool.item
        }
    }

    override val root = borderpane {
        top {
            form {
                fieldset {
                    field("Material") {
                        combobox(currentMaterial, Cache.materials)
                    }
                }
            }
        }
        center {
            hbox(30) {
                add(find<SpeedForm>(Scope(roughing)))
                add(find<SpeedForm>(Scope(finishing)))
            }
        }
    }
}

//mill_feed_type_mm_min.png
//mill_feed_type_mm_rev.png

class SpeedForm : View() {
    val cutData: CutDataModel by inject()

    override val root = form {
        fieldset {
            textProperty.bindBidirectional(cutData.application, CutData.Application.Converter)

            field("Speed") {
                textfield(cutData.speed, IntegerStringConverter()) {
                    prefWidth = 70.0
                    textProperty().onChange {
                        if (isFocused) {
                            cutData.setVcFromSpeed()
                        }
                    }
                }
                label("RPM")
            }
            field("Vc") {
                textfield(cutData.vc, NumberStringConverter()) {
                    textProperty().onChange {
                        if (isFocused) {
                            cutData.setSpeedFromVc()
                        }
                    }
                    prefWidth = 70.0
                }
                label(cutData.toolModel!!.item.smmOrSfm)
            }
        }
    }
}