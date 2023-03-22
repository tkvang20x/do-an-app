package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.avatar.DataAvatar
import com.example.do_an_app.model.users.UserData
import com.example.do_an_app.model.voucher.DataDetailVoucher
import com.example.do_an_app.model.voucher.DataVoucher
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoucherViewModel: ViewModel() {

    private val api: Api
    var dataVoucher: MutableLiveData<DataVoucher?>
    var dataDetailVoucher: MutableLiveData<DataDetailVoucher?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataVoucher = MutableLiveData<DataVoucher?>()
        dataDetailVoucher = MutableLiveData<DataDetailVoucher?>()
    }

    fun getVoucherUserId(page: Int, user_id: String, status_voucher: String) {
        api.getVoucherByUserId("Bearer $token", page, user_id, status_voucher).enqueue(object :
            Callback<DataVoucher> {
            override fun onResponse(call: Call<DataVoucher>, response: Response<DataVoucher>) {
                dataVoucher.postValue(response.body())
            }
            override fun onFailure(call: Call<DataVoucher>, t: Throwable) {
                dataVoucher.postValue(null)
            }
        })
    }

    fun getDetailVoucher(voucher_id : String) {
        api.getDetailVoucher("Bearer $token", voucher_id).enqueue(object :
            Callback<DataDetailVoucher> {
            override fun onResponse(call: Call<DataDetailVoucher>, response: Response<DataDetailVoucher>) {
                dataDetailVoucher.postValue(response.body())
            }
            override fun onFailure(call: Call<DataDetailVoucher>, t: Throwable) {
                dataDetailVoucher.postValue(null)
            }
        })
    }
}