package no.syse.ectool.view

import tornadofx.*

class MainView : View("EdgeCam Tool Database Manager") {
    override val root = borderpane {
        setPrefSize(1024.0, 768.0)
        center {
            add<MillToolList>()
        }
    }
}