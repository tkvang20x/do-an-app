package com.example.do_an_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.avatar.DataAvatar
import com.example.do_an_app.model.users.DataUpdate
import com.example.do_an_app.model.users.UserData
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.do_an_app.fragment.FragmentHome.Companion.data_user

class UserViewModel: ViewModel() {
    private val api: Api
    var dataUser: MutableLiveData<UserData?>
    var token: String? = Const.TOKEN
    var dataAvatar: MutableLiveData<DataAvatar?>
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataUser = MutableLiveData<UserData?>()
        dataAvatar = MutableLiveData<DataAvatar?>()
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

    fun uploadAvatar(code: String?, avatar: MultipartBody.Part?) {
        api.uploadAvatar("Bearer $token", code, avatar)?.enqueue(object : Callback<DataAvatar?> {
            override fun onResponse(call: Call<DataAvatar?>, response: Response<DataAvatar?>) {
                if (response.body() == null) {
                    dataAvatar.setValue(null)
                } else {
                    dataAvatar.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<DataAvatar?>, t: Throwable) {
                Log.d("Error", "Fail: ${t.message}")
            }
        })
    }

    fun updateUser(code: String?, data_update: DataUpdate?) {
        api.updateUser("Bearer $token", code, data_update).enqueue(object : Callback<UserData?>{
            override fun onResponse(call: Call<UserData?>, response: Response<UserData?>) {
                if (response.body() == null ) {
                    dataUser.postValue(null)
                } else {
                    dataUser.postValue(response.body())
                    data_user = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<UserData?>, t: Throwable) {
                Log.d("Error", "Fail: ${t.message}")
            }

        })
    }
}