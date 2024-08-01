package com.example.paypod.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.paypod.api.ApiService
import com.example.paypod.model.TransactionDTO
import com.example.paypod.model.TransactionResponse
import com.example.paypod.navbar.BottomNavigationBar
import com.example.paypod.navbar.NavigationHost
import com.example.paypod.ui.theme.PayPodTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayPodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    MainScreen()
                }
            }
        }
    }
}

data class ProfileModel(
    var name: String,
    var password: String
)

data class UserModel(
    var profile: ProfileModel
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            NavigationHost(navController = navController)
        }
    }
}

fun sendRequest(
    id: String,
    transaction: MutableState<TransactionDTO>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.97:9902/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApiService::class.java)

    // Use getTransactions method from UserApi
    val call: Call<TransactionResponse> = api.getTransactions()

    call.enqueue(object : Callback<TransactionResponse?> {

        override fun onResponse(call: Call<TransactionResponse?>, response: Response<TransactionResponse?>) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                transaction.value = response.body()!!.transactionDTOS.content[0]
            }
        }

        override fun onFailure(call: Call<TransactionResponse?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}
