package com.example.paypod.api


import com.example.paypod.model.TokenResponse
import com.example.paypod.model.TransactionResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers(
        "Accept: application/json"
    )

    @GET("api/front-office/transactionId-search?status=Null&perPage=5&page=1&merchantId=20003&acquirer&issuer&date&transactionId&transactionType")
    fun getTransactions(): Call<TransactionResponse>

    @FormUrlEncoded
    @POST("realms/{realm}/protocol/openid-connect/token")
    fun login(
        @Path("realm") realm: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password", // Default value
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("scope") scope:String

    ): Call<TokenResponse>
}
