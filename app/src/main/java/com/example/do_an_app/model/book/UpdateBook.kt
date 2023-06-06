package com.example.do_an_app.model.book

data class UpdateBook (
        val status_book: String,
        val status_borrow: String,
        val user_borrow: String
        ) {
}