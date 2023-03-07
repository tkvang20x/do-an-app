package com.example.do_an_app.model.books

data class BooksDataList(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)