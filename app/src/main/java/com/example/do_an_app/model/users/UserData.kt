package com.example.do_an_app.model.users

data class UserData(
    val `data`: Data,
    val error: String,
    val path: String,
    val status: Int
)