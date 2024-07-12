package com.example.paypod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.paypod.api.ApiClient
import com.example.paypod.api.ApiService
import com.example.paypod.model.DataResponse
import com.example.paypod.model.User
import com.example.paypod.ui.theme.PayPodTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : ComponentActivity() {

    private val apiService: ApiService by lazy {
        ApiClient.getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayPodTheme {
                HistoryScreen(apiService)
            }
        }
    }
}

@Composable
fun HistoryScreen(apiService: ApiService) {
    var users by remember { mutableStateOf<List<User>?>(null) }

    LaunchedEffect(Unit) {
        apiService.getUsers().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.isSuccessful) {
                    users = response.body()?.users
                } else {
                    // Handle error response (show error message, etc.)
                }
            }

            override fun onFailure(call: Call<DataResponse>, throwable: Throwable) {
                // Handle network error or other failures (show error message, etc.)
            }
        })
    }

    if (users == null) {
        // Show loading message while fetching data
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...")
        }
    } else {
        // Display user data directly
        UserList(users = users!!)
    }
}

@Composable
fun UserList(users: List<User>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "List of Users:")
        users.forEach { user ->
            Text(text = "${user.firstName} ${user.lastName} -------- Gender --> ${user.gender}")
        }
    }
}
