package no.syse.ectool.view

import no.syse.ectool.app.Styles
import no.syse.ectool.domain.Tool
import tornadofx.*

class MainView : View("EdgeCam Tool Database Manager") {
    override val root = borderpane {
        setPrefSize(1024.0, 768.0)
        center {
            tabpane {
                addClass(Styles.maintabs)
                tab(find<ToolList>(ToolList::category to Tool.Category.Milling))
                tab(find<ToolList>(ToolList::category to Tool.Category.Hole))
                tab(find<ToolList>(ToolList::category to Tool.Category.Probe))
            }
        }
    }
}