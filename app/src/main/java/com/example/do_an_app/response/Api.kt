package com.example.do_an_app.response

import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.books.DetailBooks
import com.example.do_an_app.model.group.GroupsData
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.model.register.DataRegister
import com.example.do_an_app.model.register.RegisterUser
import com.example.do_an_app.model.users.UserData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("/do-an/v1/login/user")
    fun postLogin(@Body login: DataLogin) : Call<Login>

    @POST("/do-an/v1/users")
    fun postRegister(@Body register: DataRegister) : Call<RegisterUser>

    @GET("/do-an/v1/books")
    fun getBooks(@Header("Authorization") token: String?, @Query("page") page: Int, @Query("size") size: Int, @Query("name") name: String, @Query("author") author: String, @Query("group_code") group_code:String) : Call<BooksDataList>

    @GET("/do-an/v1/user/detail")
    fun getUser(@Header("Authorization") token: String?) : Call<UserData>

    @GET("/do-an/v1/books/{code}")
    fun getDetailBooks(@Header("Authorization") token: String?, @Path("code") code: String) : Call<DetailBooks>

    @GET("/do-an/v1/groups")
    fun getGroups() : Call<GroupsData>

}