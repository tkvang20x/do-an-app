package com.example.do_an_app.callback

import com.example.do_an_app.model.voucher.BooksBorrowed


interface CallBackItemBook {
    fun onClick(books: BooksBorrowed)

    fun onLongClick(job: BooksBorrowed)
}