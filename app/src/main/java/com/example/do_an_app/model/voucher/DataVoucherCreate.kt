package com.example.do_an_app.model.voucher

data class DataVoucherCreate(
    val books_borrowed: ArrayList<ListIdBook>,
    val due_date: String,
    val user_id: String,
    val description: String
) {
}