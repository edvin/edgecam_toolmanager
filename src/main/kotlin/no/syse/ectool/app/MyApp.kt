package no.syse.ectool.app

import no.syse.ectool.controller.DBController
import no.syse.ectool.domain.CutData
import no.syse.ectool.view.MainView
import tornadofx.*


class MyApp : App(MainView::class, Styles::class)

fun main(args: Array<String>) {
    val db = DBController()
    val tool = db.getTool(3747)
    db.deleteTool(tool)
//    val finishingAlu = cutData.find { it.application == CutData.Application.Finishing && it.material.description.contains("Alu") }!!
//    finishingAlu.feedrate = 1100.0
//    db.saveCutData(finishingAlu)


//    val finishingAlu = CutData().apply {
//        this.tool = tool
//        application = CutData.Application.Finishing
//        material = db.listMaterials().find { it.description.contains("Alu") }
//        feedrate = 2200.0
//        plungeFeedrate = 800.0
//        stepover = 4.0
//        speed = 11500.0
//    }
//    db.saveCutData(finishingAlu)
//
//    val cutData = db.listCutDataForTool(tool)
//    println(cutData)

}