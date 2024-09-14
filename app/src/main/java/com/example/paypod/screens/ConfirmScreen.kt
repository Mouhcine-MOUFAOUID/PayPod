package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paypod.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ConfirmScreen(navController: NavController, amount: String) {
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(horizontal = 16.dp) // Apply padding here to avoid adding it multiple times
    ) {
        HeaderConfirm(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "$amount MAD",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Add alignment inside the column
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally) // Center the box horizontally
        ) {
            PasswordInputField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        NumberPadC { value ->
            if (value == "<") {
                if (password.isNotEmpty()) password = password.dropLast(1)
            } else {
                if (password.length < 4) password += value
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ActionButtons(navController, password, amount)
    }
}

@Composable
fun HeaderConfirm(navController: NavController) {
    val orbitron = FontFamily(
        Font(R.font.orbitronbold),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        Text(
            text = "Confirmation",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        placeholder = {
            Text(
                text = "Enter 4-digit code",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(textAlign = TextAlign.Center), // Center the text inside
        modifier = modifier
            .width(200.dp) // Adjust the width as needed
            .background(Color.White)
            .padding(vertical = 4.dp)
    )
}


@Composable
fun NumberPadC(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "<", "0"
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttons.chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { label ->
                    Button(
                        onClick = { onButtonClick(label) },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        shape = RoundedCornerShape(8.dp) // Slight curves with sharp corners
                    ) {
                        Text(text = label, color = Color.Black, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButtons(navController: NavController, password: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                if (password.length == 4) {
                    navController.navigate("success_screen/$amount") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Confirm", color = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Cancel", color = Color.White)
        }
    }
}
