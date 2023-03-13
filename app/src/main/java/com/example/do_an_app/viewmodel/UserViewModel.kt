package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.users.UserData
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {
    private val api: Api
    var dataUser: MutableLiveData<UserData?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataUser = MutableLiveData()
    }

    fun getUser(){
        api.getUser("Bearer $token").enqueue(object :
            Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                dataUser.postValue(response.body())
            }
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                dataUser.postValue(null)
            }
        })
    }
}