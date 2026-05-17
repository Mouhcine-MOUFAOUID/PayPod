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
            .baseUrl("http://192.168.8.100:9902/")
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
                    println("Transaction successful: ${transactionResponse?.responseCode}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Transaction failed: $errorBody")
                }
            }

            override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                // Handle network failure
                println("Network error: ${t.message}")
            }
        })
    }
}
