package com.example.do_an_app.model.books

data class Pagination(
    val limit: Int,
    val page: Int,
    val total_page: Int,
    val total_records: Int
)