package no.syse.ectool.view

import tornadofx.*

class MainView : View("EdgeCam Tool Database Manager") {
    override val root = borderpane {
        center {
            add<MillToolList>()
        }
    }
}