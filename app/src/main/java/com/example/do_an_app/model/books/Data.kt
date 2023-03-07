package com.example.do_an_app.model.books

data class Data(
    val pagination: Pagination,
    val result: List<Result>,
    val sort: Sort
)