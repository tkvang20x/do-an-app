package com.example.do_an_app.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.fragment.FragmentLogin
import com.example.do_an_app.model.login.ChangePassWord
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.DataPassword
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.model.password.ForgotPassword
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val api: Api
    var dataLogin : MutableLiveData<Login?>
    var dataPass : MutableLiveData<ChangePassWord?>
    var token: String? = Const.TOKEN
    var dataForgotPassword: MutableLiveData<ForgotPassword?>
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataLogin = MutableLiveData()
        dataPass = MutableLiveData()
        dataForgotPassword = MutableLiveData()
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

    fun putPassword(data : DataPassword){
        api.updatePass("Bearer $token", data).enqueue(object : Callback<ChangePassWord> {
            override fun onResponse(call: Call<ChangePassWord>, response: Response<ChangePassWord>) {
                Log.d("kkk", "onFailure: ${response.toString()}")

                dataPass.postValue(response.body())
            }

            override fun onFailure(call: Call<ChangePassWord>, t: Throwable) {
                dataPass.postValue(null)
            }
        })
    }

    fun forgotPassword(role : String, email: String){
        api.forgotPassword("Bearer $token", role, email).enqueue(object : Callback<ForgotPassword> {
            override fun onResponse(call: Call<ForgotPassword>, response: Response<ForgotPassword>) {
                Log.d("kkk", "onFailure: ${response.toString()}")

                dataForgotPassword.postValue(response.body())
            }

            override fun onFailure(call: Call<ForgotPassword>, t: Throwable) {
                dataForgotPassword.postValue(null)
            }
        })
    }

}