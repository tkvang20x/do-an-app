package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.books.DetailBooks
import com.example.do_an_app.model.voucher.DataListIdBook
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksViewModel: ViewModel() {
    private val api: Api
    var dataBooks : MutableLiveData<BooksDataList?>
    var detailBooks: MutableLiveData<DetailBooks?>
    var datalistIdBook: MutableLiveData<DataListIdBook?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataBooks = MutableLiveData()
        detailBooks = MutableLiveData()
        datalistIdBook= MutableLiveData()
    }

    fun getBooks(page: Int, size:Int, name: String, author: String, group_code: String){
        dataBooks = MutableLiveData<BooksDataList?>()
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
    fun getListIdBook(code: String, size: Int, status_borrow:String){
        api.getListIdBook("Bearer $token",code, size, status_borrow).enqueue(object : Callback<DataListIdBook> {
            override fun onResponse(call: Call<DataListIdBook>, response: Response<DataListIdBook>) {
                datalistIdBook.postValue(response.body())
            }
            override fun onFailure(call: Call<DataListIdBook>, t: Throwable) {
                datalistIdBook.postValue(null)
            }
        })
    }
}