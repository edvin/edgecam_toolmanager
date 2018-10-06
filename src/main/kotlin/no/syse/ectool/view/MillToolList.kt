package no.syse.ectool.view

import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import no.syse.ectool.app.Styles
import no.syse.ectool.app.ToolApp
import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.Tool
import no.syse.ectool.domain.ToolModel
import no.syse.ectool.domain.ToolQuery
import no.syse.ectool.events.ToolModifiedEvent
import org.controlsfx.control.textfield.TextFields
import tornadofx.*

class MillToolList : View() {
    val query = ToolQuery().apply { category = Tool.Category.Milling }
    val db: DBController by inject()
    val tools = SortedFilteredList<Tool>()
    private var ignoreUpdate = false

    override val root = borderpane {
        top {
            TextFields.createClearableTextField().apply {
                this@top.add(this)

                promptText = "Filter (ESC)"
                tooltip("ESC enters the filter field. ESC while filter field is active clears filter.")
                isFocusTraversable = false
                trimWhitespace()
                // Cache keywords and trimmed content instead of calculating once for each tool when filtering
                val keywords = ArrayList<String>()
                textProperty().onChange {
                    keywords.clear()
                    keywords.addAll(text.split(" ").filter { t -> t.isNotEmpty() })
                }

                tools.filterWhen(textProperty()) { q, t ->
                    val content = t.toString().toLowerCase().trim()
                    keywords.map { content.contains(it) }.all { it }
                }
                shortcut("ESC") {
                    if (isFocused) {
                        clear()
                        center.requestFocus()
                    } else {
                        requestFocus()
                    }
                }
                shortcut("Down") {
                    center.requestFocus()
                }
            }
        }

        center {
            tableview(tools) {
                column("Description", Tool::descriptionProperty) {
                    cellFormat {
                        text = it
                        rowItem.millType?.let {
                            graphic = ToolApp.icon(it).apply {
                                fitHeight = 16.0
                                fitWidth = 16.0
                            }
                        }
                    }
                }
                column("Diameter", Tool::diameterProperty).cellFormat {
                    text = "$it${rowItem.mmOrInch.value}"
                }
                column("Reach", Tool::reachProperty).cellFormat {
                    text = "$it${rowItem.mmOrInch.value}"
                }
                column("Flutes", Tool::teethProperty)
                column("Flute Length", Tool::fluteLengthProperty).cellFormat {
                    text = "$it${rowItem.mmOrInch.value}"
                }
                column("Centre Cutting", Tool::centreCuttingProperty) {
                    cellFormat {
                        alignment = Pos.CENTER
                        text = if (it) "Y" else "N"
                    }
                }
                column("Manufacturer", Tool::manufacturerProperty)
                column("Item ID", Tool::itemIdProperty)
                column("Comment", Tool::commentProperty)
                onUserSelect {
                    editTool(it)
                }
                setOnKeyPressed {
                    if (it.code == KeyCode.ENTER && selectedItem != null)
                        editTool(selectedItem!!)
                }
                smartResize()
            }
        }
        bottom {
            hbox(3) {
                addClass(Styles.toolbar)
                Tool.MillType.values().forEach { millType ->
                    val toggleGroup = ToggleGroup()
                    togglebutton(millType.description, toggleGroup) {
                        graphic = ToolApp.icon(millType)
                        isSelected = millType == Tool.MillType.Endmill

                        selectedProperty().onChange {
                            if (it)
                                query.millTypes.add(millType)
                            else
                                query.millTypes.remove(millType)

                            onRefresh()
                        }
                    }
                }
            }
        }
    }

    private fun editTool(tool: Tool) {
        val model = ToolModel(tool)
        val editScope = Scope(model)
        find<MillToolMainEditor>(editScope).openWindow()
    }

    override fun onDock() {
        query.millTypes.add(Tool.MillType.Endmill)
        onRefresh()
        subscribe<ToolModifiedEvent> { onRefresh() }
    }

    override fun onRefresh() {
        if (ignoreUpdate) return

        tools.asyncItems {
            db.listTools(query)
        }
    }
}