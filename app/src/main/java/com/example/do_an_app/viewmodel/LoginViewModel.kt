package com.example.do_an_app.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.fragment.FragmentLogin
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val api: Api
    var dataLogin : MutableLiveData<Login?>
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataLogin = MutableLiveData()
    }
    fun postLogin(login : DataLogin){
        api.postLogin(login).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                Log.d("kkk", "onFailure: ${response.toString()}")

                dataLogin.postValue(response.body())
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                dataLogin.postValue(null)
            }


        })
    }
}