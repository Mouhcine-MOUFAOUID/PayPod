package com.example.paypod.model

import com.example.paypod.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionRepository {

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.97:9902/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun initiateTransaction(transactionRequest: TransactionRequest) {
        val call = apiService.initiateTransaction(transactionRequest)

        call.enqueue(object : Callback<TransactionResponse> {
            override fun onResponse(call: Call<TransactionResponse>, response: Response<TransactionResponse>) {
                if (response.isSuccessful) {
                    val transactionResponse = response.body()
                    // Handle successful response
                    println("Transaction successful: ${transactionResponse?.responseCode}")
                } else {
                    // Handle unsuccessful response
                    println("Transaction failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                // Handle network failure
                println("Network error: ${t.message}")
            }
        })
    }
}
