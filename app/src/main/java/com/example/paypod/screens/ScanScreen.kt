package com.example.paypod.screens

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.paypod.R
import com.example.paypod.model.NFCViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScanScreen(
    navController: NavController,
    amount: String,
    nfcViewModel: NFCViewModel = viewModel()
) {
    val montserrat = FontFamily(
        Font(R.font.montserratmedium),
    )
    val montserratbold = FontFamily(
        Font(R.font.montserratextrabold),
    )

    val activity = LocalContext.current as? Activity
    LaunchedEffect(activity) {
        activity?.let {
            nfcViewModel.enableNfcReader(it)
        }
    }

    val cardNumber by nfcViewModel.cardNumber.collectAsState()
    val expirationDate by nfcViewModel.expirationDate.collectAsState()
    val scanSuccess by nfcViewModel.scanSuccess.collectAsState()

    if (scanSuccess) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = { Text(text = "Scan succeeded") },
            text = {
                Column {
                    Text(text = "Card number: $cardNumber")
                    Text(text = "Expiration date: $expirationDate")
                }
            },
            confirmButton = {
                Button(onClick = {
                    navController.navigate("SuccessScreen/$amount")
                }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderScan(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = "NFC",
                    modifier = Modifier.size(180.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hold your card or device to the back of this phone",
                    fontSize = 15.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = montserrat),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = amount,
                    fontSize = 30.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = montserratbold)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MAD",
                    fontSize = 20.sp,
                    color = Color.Black,
                    style = TextStyle(fontFamily = montserrat)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    color = Color.White,
                    style = TextStyle(fontFamily = montserrat)
                )
            }
        }
    }
}


@Composable
fun HeaderScan(navController: NavController) {
    val orbitron = FontFamily(
        Font(R.font.orbitronbold),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        Text(
            text = "Scan",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}
