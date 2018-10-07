package no.syse.ectool.app

import javafx.scene.image.ImageView
import javafx.stage.Stage
import no.syse.ectool.domain.Tool
import no.syse.ectool.view.MainView
import tornadofx.*
import java.nio.file.Files
import java.nio.file.Paths


class ToolApp : App(MainView::class, Styles::class) {
    init {
        Workspace.defaultCloseable = false
    }

    override fun start(stage: Stage) {
        stage.icons.add(icon("Tool_type_mill.png", 16).image)
        super.start(stage)
    }

    companion object {
        private val veroFolder = Paths.get("C:/Program Files/Vero Software")
        private val edgecamCandidates = Files.list(veroFolder).filter { it.fileName.toString().startsWith("Edgecam 2") }

        val iconBase = edgecamCandidates.sorted().findFirst().get().resolve("cam").resolve("illustrate")

        fun icon(millType: Tool.MillType, size: Int = 32) = icon(millType.iconName, size)
        fun icon(category: Tool.Category, size: Int = 20) = icon(category.iconName, size, category.library)

        fun icon(name: String, size: Number = 32, library: String = "Tool") = ImageView(iconBase.resolve(library).resolve(name).toUri().toURL().toExternalForm()).apply {
            fitHeight = size.toDouble()
            fitWidth = size.toDouble()
        }
    }


}