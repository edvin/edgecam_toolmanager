package no.syse.ectool.view

import javafx.scene.control.ButtonBar
import no.syse.ectool.app.Styles
import no.syse.ectool.app.ToolApp
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.ToolModel
import no.syse.ectool.events.ToolModifiedEvent
import tornadofx.*
import kotlin.reflect.KClass

class MillToolMainEditor : Fragment() {
    val db: DBController by inject()
    val tool: ToolModel by inject()

    companion object {
        var lastOpenTab: KClass<out UIComponent>? = ToolEditorTechnology::class
    }

    init {
        titleProperty.bind(tool.description)
    }

    override fun onDock() {
        currentStage!!.icons.add(ToolApp.icon(tool.millType.value).image)
    }

    override val root = borderpane {
        setPrefSize(800.0, 600.0)
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
                            runAsync {
                                db.saveTool(tool.item)
                            } ui {
                                close()
                                fire(ToolModifiedEvent)
                            }
                        }
                    }
                }
                button("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE) {
                    addClass(Styles.Cancel)
                    action {
                        close()
                    }
                }
            }
        }
    }
}