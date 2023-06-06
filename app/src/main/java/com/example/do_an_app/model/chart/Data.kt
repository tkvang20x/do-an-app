package com.example.do_an_app.model.chart

data class Data(
    val list_books_count: List<BooksCount>,
    val total_cancel: Int,
    val total_confirm: Int,
    val total_expired: Int,
    val total_payed: Int,
    val total_voucher: Int,
    val total_waiting: Int
)