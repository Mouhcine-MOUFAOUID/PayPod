package com.example.paypod.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.paypod.api.RetrofitInstance
import com.example.paypod.model.TokenResponse
import com.example.paypod.screens.LoginScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var errorMessage by remember { mutableStateOf<String?>(null) } // For storing error message

            LoginScreen(
                onLoginClick = { username, password ->
                    login(username, password) { success, message ->
                        if (success) {
                            errorMessage = null
                            navigateToHistoryActivity()
                        } else {
                            errorMessage = message // Set error message on failure
                        }
                    }
                },
                onWelcomeMessageClick = {
                    // Handle welcome message click if needed
                },
                errorMessage = errorMessage // Pass the error message to the screen
            )
        }
    }

    private fun navigateToHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        val call = RetrofitInstance.api.login(
            "soft-pos", username, password,
            clientId = "softpos", clientSecret = "FV9nynZ6wFOrRvZ5wCvV5UrLAQ4QFdSu",
            scope = "openid"
        )

        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val tokenResponse = response.body()
                    tokenResponse?.let {
                        onResult(true, "Login successful!")
                    } ?: onResult(false, "Login failed")
                } else {
                    onResult(false, "Login failed")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                onResult(false, "Error: ${t.message}")
            }
        })
    }
}

