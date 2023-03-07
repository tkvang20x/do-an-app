package com.example.do_an_app.model.register

data class DataRegister(
    val code: String,
    val course: String,
    val date_of_birth: String,
    val email: String,
    val gender: String,
    val name: String,
    val password: String,
    val phone: String,
    val role: String = "USER",
    val university: String,
    val user_name: String
)