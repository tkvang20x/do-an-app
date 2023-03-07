package com.example.do_an_app.model.register

data class RegisterUser(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)