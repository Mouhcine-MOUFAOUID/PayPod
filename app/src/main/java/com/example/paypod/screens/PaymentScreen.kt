package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paypod.R

@Composable
fun PaymentScreen(navController: NavController) {
    var amount by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderPayment(navController)
        Spacer(modifier = Modifier.height(1.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            InputField(
                value = clientName,
                placeholder = "Client Name",
                onValueChange = { clientName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            InputField(
                value = description,
                placeholder = "Description",
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            AmountRow(amount)
            Spacer(modifier = Modifier.height(1.dp))
            NumberPad { value ->
                when (value) {
                    "<" -> if (amount.isNotEmpty()) amount = amount.dropLast(1)
                    "," -> if (!amount.contains(",")) amount += ","
                    else -> amount += value
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            ActionButtons(navController, amount, clientName, description, showDialog, { showDialog = it })
        }

        // Show dialog if showDialog is true
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Input Error") },
                text = { Text("Please fill all fields and ensure the amount is greater than 0.") },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun HeaderPayment(navController: NavController) {
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
            text = "Payment",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun InputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 12.sp,
                color = Color.Gray
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black, // Text color
            backgroundColor = Color.White, // Background color
            cursorColor = Color.Black
        ),
        modifier = modifier
            .background(Color.White)
            .padding(vertical = 4.dp)
    )
}


@Composable
fun AmountRow(amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .background(Color.White)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = amount, fontSize = 16.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.width(8.dp))
        CurrencyDropdown()
    }
}

@Composable
fun CurrencyDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("MAD") }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(Color.White)
    ) {
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedCurrency, fontSize = 16.sp)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                selectedCurrency = "MAD"
                expanded = false
            }) {
                Text(text = "MAD")
            }
            DropdownMenuItem(onClick = {
                selectedCurrency = "EUR"
                expanded = false
            }) {
                Text(text = "EUR")
            }
        }
    }
}

@Composable
fun NumberPad(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "<", "0", ","
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
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F))
                    ) {
                        Text(text = label, color = Color.Black, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButtons(
    navController: NavController,
    amount: String,
    clientName: String,
    description: String,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                if (clientName.isBlank() || description.isBlank() || amount.isBlank() || amount == "0") {
                    setShowDialog(true)
                } else {
                    navController.navigate("scan_screen/$amount")
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Charge", color = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F)),
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Cancel", color = Color.White)
        }
    }
}
