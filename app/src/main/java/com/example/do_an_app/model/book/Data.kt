package com.example.do_an_app.model.book

import com.example.do_an_app.model.books.DataX
import com.example.do_an_app.model.group.Result

data class Data(
    val books: DataX,
    val code_books: String,
    val code_id: String,
    val created_by: String,
    val created_time: String,
    val groups: Result,
    val id: String,
    val is_active: Boolean,
    val is_delete: Boolean,
    val modified_by: String,
    val modified_time: String,
    val qr_code_data: String,
    val status_book: String,
    val status_borrow: String,
    val user_borrow: String,
    val compartment: Int
)