package no.syse.ectool.view

import javafx.scene.control.ButtonBar
import javafx.scene.image.ImageView
import no.syse.ectool.app.Styles
import no.syse.ectool.app.ToolApp
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import no.syse.ectool.events.ToolAddedEvent
import tornadofx.*
import kotlin.reflect.KClass

class MillToolMainEditor : Fragment() {
    val db: DBController by inject()
    val tool: ToolModel by inject()

    companion object {
        var lastOpenTab: KClass<out UIComponent>? = ToolEditorTechnology::class
    }

    init {
        titleProperty.bind(tool.description.stringBinding { "${tool.category.value}: $it" })
    }

    override fun onDock() {
        val icon = when (tool.category.value) {
            Tool.Category.Milling -> ToolApp.icon(tool.millType.value)
            Tool.Category.Hole -> ToolApp.icon(tool.holeType.value)
            else -> ImageView()
        }
        currentStage!!.icons.add(icon.image)
    }

    override val root = borderpane {
        setPrefSize(700.0, 480.0)
        center {
            tabpane {
                tab<ToolEditorGeneral>()
                tab<ToolEditorTechnology>()
                tab<ToolEditorFeedsAndSpeeds>()

                selectionModel.selectedItemProperty().onChange {
                    lastOpenTab = it?.content?.uiComponent<UIComponent>()?.javaClass?.kotlin
                }

                lastOpenTab?.let {
                    selectionModel.select(tabs.find { tab ->
                        tab.content?.uiComponent<UIComponent>()?.javaClass?.kotlin == it
                    })
                }
            }
        }
        bottom {
            buttonbar {
                button("OK", ButtonBar.ButtonData.OK_DONE) {
                    addClass(Styles.OK)
                    action {
                        tool.commit {
                            val isNew = tool.id.value == null || tool.id.value == 0
                            runAsync {
                                db.saveTool(tool.item)

                                // Commit cut data being edited
                                find<ToolEditorFeedsAndSpeeds>().commitCutData()

                                tool.modifiedCutData.forEach {
                                    db.saveCutData(it)
                                }
                            } ui {
                                close()
                                if (isNew)
                                    fire(ToolAddedEvent(tool.item))
                            }
                        }
                    }
                }
                button("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE) {
                    addClass(Styles.Cancel)
                    action {
                        tool.rollback()
                        close()
                    }
                }
            }
        }
    }

}