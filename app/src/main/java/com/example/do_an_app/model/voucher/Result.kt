package com.example.do_an_app.model.voucher

data class Result(
    val books_borrowed: List<BooksBorrowed>,
    val created_by: String,
    val created_time: String,
    val description: String,
    val due_date: String,
    val is_active: Boolean,
    val is_delete: Boolean,
    val manager_id: String,
    val manager_name: String,
    val modified_by: String,
    val modified_time: String,
    val start_date: String,
    val status_voucher: String,
    val user_id: String,
    val user_name: String,
    val voucher_id: String
)