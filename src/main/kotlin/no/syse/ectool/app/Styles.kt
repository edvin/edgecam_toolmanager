package no.syse.ectool.app

import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val OK by cssclass()
        val Cancel by cssclass()
        val toolbar by cssclass()
        val maintabs by cssclass()
    }

    init {
        maintabs {
            tabMinHeight = 40.px
            tabMaxHeight = 40.px
            tabMinWidth = 85.px
            tabMaxWidth = 85.px
        }
        toolbar {
            backgroundColor += Color.LIGHTGREY
        }
        root {
            focusColor = Color.TRANSPARENT
            faintFocusColor = Color.TRANSPARENT
            backgroundColor += Color.WHITE
            unsafe("-fx-control-inner-background-alt", "white")
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
            accentColor = Color.BLUE
        }
        comboBox {
            borderColor += box(Color.BLACK)
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
        }
        checkBox {
            borderColor += box(Color.BLACK)
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
            padding = box(3.px, 0.px, 3.px, 4.px)
            box {
                backgroundColor += Color.WHITE
                alignment = Pos.CENTER
            }
        }
        tab {
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
        }
        toggleButton {
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
        }
        textField {
            borderColor += box(Color.BLACK)
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
        }
        scrollBar {
            prefWidth = 20.px
            prefHeight = 20.px
            backgroundColor += Color.WHITE

            thumb {
                backgroundColor += c("#ededed")
                backgroundRadius += box(0.px)

                and(hover) {
                    backgroundColor += c("#cecece")
                }
            }
        }
        buttonBar {
            backgroundColor += Color.BLACK
            button {
                and(hover) {
                    backgroundColor += Color.ORANGE
                }
                borderColor += box(Color.WHITE)
            }
            padding = box(3.px)
        }
        button {
            and(OK) {
                backgroundColor += Color.BLUE
                textFill = Color.WHITE
            }
            and(Cancel) {
                backgroundColor += Color.BLACK
                textFill = Color.WHITE
            }
        }
        tableView {
            unsafe("-fx-selection-bar", "blue")
            unsafe("-fx-selection-bar-non-focused", "blue")
        }

        s(incrementArrowButton, decrementArrowButton) {
            backgroundRadius += box(0.px)
            borderRadius += box(0.px)
            borderColor += box(Color.BLACK)
            backgroundColor += Color.TRANSPARENT
        }
        textField and disabled {
            backgroundColor += Color.LIGHTGREY
        }
    }
}