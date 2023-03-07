package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksViewModel: ViewModel() {
    private val api: Api
    var dataBooks : MutableLiveData<BooksDataList?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataBooks = MutableLiveData()
    }

    fun getBooks(page: Int, size:Int, name: String, author: String){
        api.getBooks("Bearer $token",page, size, name, author).enqueue(object : Callback<BooksDataList> {
            override fun onResponse(call: Call<BooksDataList>, response: Response<BooksDataList>) {
                dataBooks.postValue(response.body())
            }
            override fun onFailure(call: Call<BooksDataList>, t: Throwable) {
                dataBooks.postValue(null)
            }
        })
    }

}