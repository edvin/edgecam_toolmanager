package no.syse.ectool.app

import javafx.scene.image.ImageView
import javafx.stage.Stage
import no.syse.ectool.domain.Tool
import no.syse.ectool.view.MainView
import tornadofx.*


class MyApp : App(MainView::class, Styles::class) {
    init {
        Workspace.defaultCloseable = false
    }

    override fun start(stage: Stage) {
        val icon = ImageView(Tool.millIconBase.resolve("Tool_type_mill.png").toUri().toURL().toExternalForm()).apply {
            fitHeight = 16.0
            fitWidth = 16.0
        }
        stage.icons.add(icon.image)
        super.start(stage)
    }
}