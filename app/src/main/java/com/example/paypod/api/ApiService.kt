package com.example.paypod.api

import com.example.paypod.model.LoginRequest
import com.example.paypod.model.LoginResponse
import com.example.paypod.model.DataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    abstract fun getTransactions(nothing: Nothing?, i: Int, i1: Int, i2: Int): Any

    @GET("/users")
    fun getUsers(): Call<DataResponse>
}
