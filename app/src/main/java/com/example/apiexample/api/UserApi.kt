package com.example.apiexample.api

import com.example.apiexample.ProfileModel
import com.example.apiexample.TransactionDTO
import com.example.apiexample.TransactionResponse
import com.example.apiexample.UserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

public interface UserApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("users/{id}")
    abstract fun getUserById(@Path("id") id: String): Call<UserModel?>?
//    @GET("api/front-office/transactionId-search?status=Null&perPage=5&page=1&merchantId=20003&acquirer&issuer&date&transactionId&transactionType")
//    abstract fun getUsers(): Call<TransactionResponse?>?

    @GET("transactions")
    abstract fun getUsers(): Call<TransactionResponse?>?
}