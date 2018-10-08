package no.syse.ectool.view

import javafx.beans.property.Property
import javafx.scene.Node
import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.image.Image
import no.syse.ectool.app.ToolApp
import tornadofx.*


fun Node.enhanceAndHelp(helpImage: Property<Image>, iconName: String, library: String = "Tool") {
    // Trigger help icon and select text on focus
    focusedProperty().onChange { focused ->
        if (focused) {
            helpImage.value = ToolApp.icon(iconName, 32, library).image
            (this as? TextField)?.selectAll()
            // Spinner needs a little extra push
            (this as? Spinner<*>)?.let {
                runLater {
                    it.editor?.selectAll()
                }
            }
        }
    }

    // Make the spinner commit it's value on manual edit
    (this as? Spinner<*>)?.editor?.textProperty()?.onChange { commitEditorText(this) }

    // Focus follows mouse
    setOnMouseEntered {
        requestFocus()
    }
}

fun <T> commitEditorText(spinner: Spinner<T>) {
    if (!spinner.isEditable) return
    val text = spinner.editor.text
    val valueFactory = spinner.valueFactory
    if (valueFactory != null) {
        val converter = valueFactory.converter
        if (converter != null) {
            val value = converter.fromString(text)
            if (value != null)
                valueFactory.value = value
        }
    }
}
