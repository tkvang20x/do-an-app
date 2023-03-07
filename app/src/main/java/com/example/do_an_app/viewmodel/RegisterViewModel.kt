package com.example.do_an_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.register.DataRegister
import com.example.do_an_app.model.register.RegisterUser
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val api: Api
    var dataRegister : MutableLiveData<RegisterUser>
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataRegister = MutableLiveData()
    }
    fun postRegister(mDataRegister: DataRegister){
        api.postRegister(mDataRegister).enqueue(object : Callback<RegisterUser> {

            override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                dataRegister.postValue(response.body())
                Log.d("aaa","mot ${response.body()?.error}")
            }

            override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                Log.d("aaa","fail")
            }


        })
    }
}