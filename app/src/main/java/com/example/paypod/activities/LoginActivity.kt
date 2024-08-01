package com.example.paypod.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.paypod.R
import com.example.paypod.api.RetrofitInstance
import com.example.paypod.model.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.editTextUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val messageTextView = findViewById<TextView>(R.id.textViewWelcomeMessage1)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            login(username, password) { success, message ->
                if (success) {
                    navigateToHistoryActivity()
                } else {
                    messageTextView.text = message
                }
            }
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
