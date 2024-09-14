package com.example.paypod.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paypod.R

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onWelcomeMessageClick: () -> Unit
) {
    val orbitronFont = FontFamily(Font(R.font.orbitron))
    val blueGreyColor = colorResource(id = R.color.bluegrey)
    val blueColor = colorResource(id = R.color.blue)
    val blackColor = colorResource(id = R.color.black)
    val whiteColor = colorResource(id = R.color.white)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello Again!",
            fontFamily = orbitronFont,
            fontSize = 40.sp,
            color = blueGreyColor,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Welcome back, you have",
            fontFamily = orbitronFont,
            fontSize = 14.sp,
            color = blackColor,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )

        Text(
            text = "been missed!",
            fontFamily = orbitronFont,
            fontSize = 14.sp,
            color = blackColor,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle username input */ },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle password input */ },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = { /* Handle login click */ },
            colors = ButtonDefaults.buttonColors(containerColor = blueColor, contentColor = whiteColor),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
        ) {
            Text("SIGN IN")
        }
    }
}
