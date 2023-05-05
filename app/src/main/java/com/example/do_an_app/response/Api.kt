package com.example.do_an_app.response

import com.example.do_an_app.model.avatar.DataAvatar
import com.example.do_an_app.model.book.DataBook
import com.example.do_an_app.model.books.BooksDataList
import com.example.do_an_app.model.books.DetailBooks
import com.example.do_an_app.model.group.GroupsData
import com.example.do_an_app.model.login.DataLogin
import com.example.do_an_app.model.login.Login
import com.example.do_an_app.model.register.DataRegister
import com.example.do_an_app.model.register.RegisterUser
import com.example.do_an_app.model.users.DataUpdate
import com.example.do_an_app.model.users.UserData
import com.example.do_an_app.model.voucher.*
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


    @Multipart
    @PUT("/do-an/v1/users/{code}/avatar")
    fun uploadAvatar(
        @Header("Authorization") token: String?,
        @Path("code") code: String?,
        @Part avatar: MultipartBody.Part?
    ): Call<DataAvatar?>?

    @PUT("/do-an/v1/users/{code}")
    fun updateUser(@Header("Authorization") token: String?,@Path("code") code: String?, @Body data_update: DataUpdate?) : Call<UserData>

    @GET("/do-an/v1/voucher")
    fun getVoucherByUserId(@Header("Authorization") token: String?, @Query("page") page:Int, @Query("user_id") user_id:String, @Query("status_voucher") status_voucher:String) : Call<DataVoucher>

    @GET("/do-an/v1/voucher/{voucher_id}")
    fun getDetailVoucher(@Header("Authorization") token: String?, @Path("voucher_id") voucher_id :String) : Call<DataDetailVoucher>

    @GET("/do-an/v1/book/listid/{code_books}")
    fun getListIdBook(@Header("Authorization") token: String?, @Path("code_books") code_books :String,@Query("size") size:Int, @Query("status_borrow") status_borrow:String ) : Call<DataListIdBook>


    @GET("/do-an/v1/book/{code_id}")
    fun getDetailBook(@Header("Authorization") token: String?, @Path("code_id") code: String) : Call<DataBook>

    @POST("/do-an/v1/voucher")
    fun postVoucher(@Header("Authorization") token: String?,@Body voucherCreate: DataVoucherCreate) : Call<DataDetailVoucher>

    @PUT("/do-an/v1/voucher/{voucher_id}/status")
    fun updateStatusVoucher(@Header("Authorization") token: String?, @Path("voucher_id") voucher_id :String, @Body status_update : StatusVoucher) : Call<DataDetailVoucher>
}