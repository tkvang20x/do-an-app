package com.example.do_an_app.callback

import com.example.do_an_app.model.voucher.ListIdBook


interface CallbackBookInCreateVC {

    fun onClick(voucher: ListIdBook, index: Int)

    fun onLongClick(groups: ListIdBook, index: Int)
}