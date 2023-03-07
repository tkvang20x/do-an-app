package com.example.do_an_app.callback

import com.example.do_an_app.model.books.Result


interface CallBack {
    fun onClick(books: Result)

    fun onLongClick(job: Result)
}