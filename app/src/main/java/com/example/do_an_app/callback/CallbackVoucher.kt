package com.example.do_an_app.callback

import com.example.do_an_app.model.voucher.Result

interface CallbackVoucher {

    fun onClick(voucher: Result)

    fun onLongClick(groups: Result)
}