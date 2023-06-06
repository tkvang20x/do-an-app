package com.example.do_an_app.model.password

data class ForgotPassword(
    val `data`: Boolean,
    val error: String,
    val path: String,
    val status: Int
)