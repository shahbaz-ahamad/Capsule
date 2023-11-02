package com.example.capsule.datamodel

data class QuestionModel(
    val question : String?= "",
    val opt1 : String?= "",
    val opt2 : String?= "",
    val opt3 : String?= "",
    val opt4 : String?= "",
    val ans : String?= "",
) {

    constructor(): this("","","","","","")
}