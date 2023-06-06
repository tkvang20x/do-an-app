package com.example.do_an_app.model.chart

data class ChartData(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)