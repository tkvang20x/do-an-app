package com.example.do_an_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.group.GroupsData
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupsViewModel : ViewModel() {
    private val api: Api
    var dataGroups: MutableLiveData<GroupsData?>

    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api::class.java)
        dataGroups = MutableLiveData()
    }

    fun getGroups() {
        api.getGroups().enqueue(object :
            Callback<GroupsData> {
            override fun onResponse(call: Call<GroupsData>, response: Response<GroupsData>) {
                dataGroups.postValue(response.body())
            }

            override fun onFailure(call: Call<GroupsData>, t: Throwable) {
                dataGroups.postValue(null)
            }
        })
    }
}