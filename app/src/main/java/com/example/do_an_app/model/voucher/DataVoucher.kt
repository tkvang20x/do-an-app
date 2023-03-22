package com.example.do_an_app.model.voucher

data class DataVoucher(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)