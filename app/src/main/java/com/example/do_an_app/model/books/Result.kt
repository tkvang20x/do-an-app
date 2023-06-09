package com.example.do_an_app.model.books

data class Result(
    val author: String,
    val avatar: String,
    val code: String,
    val created_by: String,
    val created_time: String,
    val group_code: String,
    val groups: Groups,
    val is_active: Boolean,
    val is_delete: Boolean,
    val modified_by: String,
    val modified_time: String,
    val name: String,
    val title: String,
    val total_books: Int,
    val total_ready: Int,
    val cabinet: Int,
)