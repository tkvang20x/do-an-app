package com.example.do_an_app.model.book

data class DataBook(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)