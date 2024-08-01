package com.example.paypod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.paypod.activities.MainScreen
import com.example.paypod.ui.theme.PayPodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PayPodTheme {
                MainScreen()
            }
        }
    }
}
