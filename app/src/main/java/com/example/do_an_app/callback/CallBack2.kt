package com.example.do_an_app.callback

import com.example.do_an_app.model.chart.BooksCount


interface CallBack2 {
    fun onClick(books: BooksCount)

    fun onLongClick(job: BooksCount)
}