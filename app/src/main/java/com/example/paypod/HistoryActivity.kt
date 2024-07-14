package com.example.paypod

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paypod.api.ApiService
import com.example.paypod.model.TransactionDTO
import com.example.paypod.model.TransactionResponse
import com.example.paypod.ui.theme.BlueGreyL
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
                // A surface container using the 'background' color from the theme
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
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = BlueGreyL,
                title = {
                    Text(
                        text = "Simple API Request",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val id = remember { mutableStateOf(TextFieldValue()) }
                val transaction = remember { mutableStateOf(TransactionDTO()) }

                Text(
                    text = "API Sample",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "User ID") },
                    value = id.value,
                    onValueChange = { id.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            sendRequest(id.value.text, transaction)
                        }
                    ) {
                        Text(text = "Get Data")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = transaction.value.toString(), fontSize = 12.sp)
            }
        }
    )
}


fun sendRequest(
    id: String,
    transaction: MutableState<TransactionDTO>
) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.88:9902/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(ApiService::class.java)

    // Use getTransactions method from UserApi
    val call: Call<TransactionResponse> = api.getTransactions();

    call!!.enqueue(object : Callback<TransactionResponse?> {

        override fun onResponse(call: Call<TransactionResponse?>, response: Response<TransactionResponse?>) {
            if (response.isSuccessful) {
                Log.d("Main", "success!" + response.body().toString())
                transaction.value = response.body()!!.transactionDTOS.content.get(0);
            }
        }

        override fun onFailure(call: Call<TransactionResponse?>, t: Throwable) {
            Log.e("Main", "Failed mate " + t.message.toString())
        }
    })
}