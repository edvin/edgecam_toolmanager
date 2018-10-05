package no.syse.ectool.app

import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.CutData
import no.syse.ectool.view.MainView
import tornadofx.*


class MyApp : App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    val db = DBController()
    val tool = db.getTool(207)

    val cutData = db.listCutDataForTool(tool)
    val finishingAlu = cutData.find { it.application == CutData.Application.Finishing && it.material.description.contains("Alu") }!!
//    finishingAlu.feedrate = 1100.0
//    db.saveCutData(finishingAlu)

    println(finishingAlu)

}