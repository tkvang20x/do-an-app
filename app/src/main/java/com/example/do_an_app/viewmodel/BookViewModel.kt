package com.example.do_an_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.do_an_app.Const
import com.example.do_an_app.model.book.DataBook
import com.example.do_an_app.model.book.UpdateBook
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.books.DetailBooks
import com.example.do_an_app.model.voucher.DataListIdBook
import com.example.do_an_app.response.Api
import com.example.do_an_app.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel: ViewModel() {
    private val api: Api
    var detailBook: MutableLiveData<DataBook?>
    var token: String? = Const.TOKEN
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        detailBook = MutableLiveData()
    }

    fun getDetailBook(code: String){
        api.getDetailBook("Bearer $token",code).enqueue(object : Callback<DataBook> {
            override fun onResponse(call: Call<DataBook>, response: Response<DataBook>) {
                detailBook.postValue(response.body())
            }
            override fun onFailure(call: Call<DataBook>, t: Throwable) {
                detailBook.postValue(null)
            }
        })
    }

    fun updateBook(code: String, data_update: UpdateBook){
        api.updateBook("Bearer $token",code, data_update).enqueue(object : Callback<DataBook> {
            override fun onResponse(call: Call<DataBook>, response: Response<DataBook>) {
                detailBook.postValue(response.body())
            }
            override fun onFailure(call: Call<DataBook>, t: Throwable) {
                detailBook.postValue(null)
            }
        })
    }
}