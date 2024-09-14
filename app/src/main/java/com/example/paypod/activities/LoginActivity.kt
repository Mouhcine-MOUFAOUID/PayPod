package com.example.paypod.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
            LoginScreen(
                onLoginClick = { username, password ->
                    login(username, password) { success, message ->
                        if (success) {
                            navigateToHistoryActivity()
                        } else {
                            // Update UI with the error message
                        }
                    }
                },
                onWelcomeMessageClick = {
                    // Handle welcome message click if needed
                }
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
                    } ?: onResult(false, "Login failed: Empty response")
                } else {
                    onResult(false, "Login failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                onResult(false, "Error: ${t.message}")
            }
        })
    }
}
