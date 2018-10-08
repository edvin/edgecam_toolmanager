package no.syse.ectool.view

import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.Spinner
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.util.converter.NumberStringConverter
import no.syse.ectool.app.Cache
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.CutData
import no.syse.ectool.domain.CutDataModel
import no.syse.ectool.domain.Material
import no.syse.ectool.domain.ToolModel
import tornadofx.*

class ToolEditorFeedsAndSpeeds : View("Feeds and Speeds") {
    val db: DBController by inject()
    val tool: ToolModel by inject()
    val roughing = CutDataModel(CutData(), tool)
    val finishing = CutDataModel(CutData(), tool)

    val currentMaterial = SimpleObjectProperty<Material>()
    val cutdataList = FXCollections.observableArrayList<CutData>()

    private val helpImage = SimpleObjectProperty<Image>()

    override fun onTabSelected() {
        runAsync {
            cutdataList.setAll(db.listCutDataForTool(tool.item))
        } ui {
            currentMaterial.value = Cache.materials.first()
        }

    }

    init {
        currentMaterial.onChange {
            setCutDataForCurrentMaterial()
        }
    }

    private fun setCutDataForCurrentMaterial() {
        // Commit prior changed data
        commitCutData()

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

    fun commitCutData() {
        if (roughing.isDirty)
            roughing.commit()

        if (finishing.isDirty)
            finishing.commit()
    }

    override val root = borderpane {
        top {
            vbox {
                hbox {
                    paddingBottom = 10
                    form {
                        hgrow = Priority.ALWAYS
                        fieldset {
                            field("Material") {
                                combobox(currentMaterial, Cache.materials) {
                                    enhanceAndHelp(helpImage, "modifier_select_stock.jpg", ".")
                                    runLater { requestFocus() }
                                }
                            }
                        }
                    }
                    imageview(helpImage) {
                        padding = insets(5, 10, 0, 40)
                        alignment = Pos.CENTER_RIGHT
                        fitHeight = 128.0
                        fitWidth = 128.0
                    }
                }
                pane {
                    style {
                        borderWidth += box(1.px)
                        borderColor += box(Color.TRANSPARENT, Color.TRANSPARENT, Color.BLACK, Color.TRANSPARENT)
                    }
                }
            }
        }
        center {
            hbox(30) {
                val helpContainer = HelpContainer(helpImage)
                add(find<SpeedForm>(Scope(roughing, helpContainer)))
                add(find<SpeedForm>(Scope(finishing, helpContainer)))
            }
        }
    }
}

class HelpContainer(val image: Property<Image>) : ViewModel()

class SpeedForm : View() {
    val cutData: CutDataModel by inject()
    val helpContainer: HelpContainer by inject()
    private val helpImage = helpContainer.image

    override val root = form {
        fieldset {
            textProperty.bindBidirectional(cutData.application, CutData.Application.Converter)

            field("n (Spindle Speed)") {
                spinner(0.0, 100000.0, property = cutData.speed, amountToStepBy = 100.0, enableScroll = true) {
                    isEditable = true
                    defaultSize()
                    valueProperty().onChange {
                        if (isFocused && cutData.isDirty) {
                            runLater {
                                cutData.setVcFromSpeed()
                            }
                        }
                    }
                    enhanceAndHelp(helpImage, "speed.png", "Mill")
                }
                label("rpm")
            }
            field("Vc (Cutting Speed)") {
                spinner(0.0, 2000.0, property = cutData.vc, amountToStepBy = 10.0, enableScroll = true) {
                    isEditable = true
                    defaultSize()
                    valueProperty().onChange {
                        if (isFocused && cutData.isDirty) {
                            runLater {
                                cutData.setSpeedFromVc()
                            }
                        }
                    }
                    enhanceAndHelp(helpImage, "constant_cut_on.png", "Mill")
                }
                label(cutData.toolModel!!.item.smmOrSfm)
            }
            field("Vf (Feed Rate)") {
                spinner(0.0, 48000.0, property = cutData.feedrate, amountToStepBy = 100.0, enableScroll = true) {
                    isEditable = true
                    valueProperty().onChange {
                        if (isFocused && cutData.isDirty) {
                            runLater {
                                cutData.setFzFromFeedRate()
                            }
                        }
                    }
                    enhanceAndHelp(helpImage, "feedrate.png", "Mill")
                    defaultSize()
                }
                label(cutData.toolModel!!.item.mmOrInchTooth)
            }
            field("fz (Feed per tooth)") {
                textfield(cutData.fz, NumberStringConverter()) {
                    isEditable = true
                    textProperty().onChange {
                        if (isFocused && cutData.isDirty) {
                            runLater {
                                cutData.setFeedRateFromFz()
                            }
                        }
                    }
                    setOnScroll { event ->
                        if (event.deltaY > 0) cutData.fz.value = cutData.fz.value.toDouble() + 0.001
                        if (event.deltaY < 0) cutData.fz.value = cutData.fz.value.toDouble() - 0.001
                    }
                    enhanceAndHelp(helpImage, "lead_in_perpendicular.png", "Mill")
                    prefWidth = 100.0
                }
                label(cutData.toolModel!!.item.mmOrInchTooth)
            }
            field("Stepover") {
                spinner(0.0, 200.0, property = cutData.stepover, amountToStepBy = 0.1, enableScroll = true) {
                    isEditable = true
                    defaultSize()
                    enhanceAndHelp(helpImage, "stepover.png", "Mill")
                }
                label(cutData.toolModel!!.item.mmOrInch)
            }
            field("Plunge feedrate") {
                spinner(0.0, 48000.0, property = cutData.plungeFeedrate, amountToStepBy = 50, enableScroll = true) {
                    isEditable = true
                    defaultSize()
                    enhanceAndHelp(helpImage, "Plunge_feedrate.png", "Mill")
                }
                label(cutData.toolModel!!.item.mmOrInchMin)
            }
        }
    }

    private fun Spinner<*>.defaultSize() {
        prefWidth = 100.0
        minWidth = prefWidth
        maxWidth = prefWidth
    }

}