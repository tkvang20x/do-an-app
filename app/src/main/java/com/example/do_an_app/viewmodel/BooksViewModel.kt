package com.example.do_an_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.books.DetailBooks
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class BooksViewModel: ViewModel() {
    private val api: Api
    var dataBooks : MutableLiveData<BooksDataList?>
    var detailBooks: MutableLiveData<DetailBooks?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataBooks = MutableLiveData()
        detailBooks = MutableLiveData()
    }

    fun getBooks(page: Int, size:Int, name: String, author: String, group_code: String){
        api.getBooks("Bearer $token",page, size, name, author, group_code).enqueue(object : Callback<BooksDataList> {
            override fun onResponse(call: Call<BooksDataList>, response: Response<BooksDataList>) {
                dataBooks.postValue(response.body())
            }
            override fun onFailure(call: Call<BooksDataList>, t: Throwable) {
                dataBooks.postValue(null)
            }
        })
    }

    fun getDetailBooks(code: String){
        api.getDetailBooks("Bearer $token",code).enqueue(object : Callback<DetailBooks> {
            override fun onResponse(call: Call<DetailBooks>, response: Response<DetailBooks>) {
                detailBooks.postValue(response.body())
            }
            override fun onFailure(call: Call<DetailBooks>, t: Throwable) {
                detailBooks.postValue(null)
            }
        })
    }

}