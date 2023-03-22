package com.example.do_an_app.model.voucher

import com.example.do_an_app.model.books.DataX

data class BooksBorrowed(
    val books: DataX,
    val code_books: String,
    val code_id: String,
    val created_by: String,
    val created_time: String,
    val id: String,
    val is_active: Boolean,
    val is_delete: Boolean,
    val modified_by: String,
    val modified_time: String,
    val qr_code_data: String,
    val status_book: String,
    val status_borrow: String,
    val user_borrow: String
)