package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paypod.R
import com.example.paypod.activities.sendRequest
import com.example.paypod.model.TransactionDTO

@Composable
fun HistoryScreen(navController: NavController) {
    var selectedTransaction by remember { mutableStateOf<TransactionDTO?>(null) }
    var selectedStatus by remember { mutableStateOf("All") }
    val transactionList = remember { mutableStateOf(listOf<TransactionDTO>()) }

    // Trigger the API request to fetch the transactions
    LaunchedEffect(Unit) {
        sendRequest("someId", transactionList)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderHistory(navController)
        StatusFilter(selectedStatus) { status ->
            selectedStatus = status
        }
        HistoryContent(selectedStatus, transactionList.value) { transaction ->
            selectedTransaction = transaction
        }
    }

    selectedTransaction?.let { transaction ->
        TransactionDetailDialog(
            transaction = transaction,
            onDismiss = { selectedTransaction = null }
        )
    }
}


@Composable
fun HeaderHistory(navController: NavController) {
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
            text = "History",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun StatusFilter(selectedStatus: String, onStatusSelected: (String) -> Unit) {
    val statuses = listOf("All", "Completed", "Settled", "Approved", "Pending", "Rejected", "Canceled", "Voided")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { expanded = true }
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = selectedStatus, fontSize = 16.sp, color = Color.Black)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            statuses.forEach { status ->
                DropdownMenuItem(onClick = {
                    onStatusSelected(status)
                    expanded = false
                }) {
                    Text(text = status)
                }
            }
        }
    }
}

@Composable
fun HistoryContent(
    selectedStatus: String,
    transactions: List<TransactionDTO>, // Use the dynamic transaction data
    onTransactionClick: (TransactionDTO) -> Unit
) {
    // Make the content scrollable
    val scrollState = rememberScrollState()

    // Filter transactions based on the selected status
    val filteredTransactions = if (selectedStatus == "All") {
        transactions
    } else {
        transactions.filter { it.status == selectedStatus }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        filteredTransactions.forEach { transaction ->
            HistoryTransactionItem(transaction = transaction, onClick = {
                onTransactionClick(transaction)
            })
        }
    }
}

@Composable
fun HistoryTransactionItem(transaction: TransactionDTO, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = transaction.dateTimeLocalTransaction, fontSize = 14.sp, color = Color.Black)
        Text(
            text = "%.2f".format(transaction.transactionAmount),
            fontSize = 14.sp,
            color = Color.Black
        )
        //Text(text = transaction.primaryAcountNumber, fontSize = 14.sp, color = Color.Black) // Update this based on your actual DTO
        Text(text = transaction.status, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun TransactionDetailDialog(transaction: TransactionDTO, onDismiss: () -> Unit) {
    fun formatPAN(pan: String): String {
        return if (pan.length > 4) {
            "*".repeat(pan.length - 4) + pan.takeLast(4)
        } else {
            pan
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Transaction Details") },
        text = {
            Column {
                Text(text = "Date: ${transaction.dateTimeLocalTransaction}")
                Text(text = "Amount: ${transaction.transactionAmount}")
                Text(text = "PAN: ${formatPAN(transaction.primaryAcountNumber)}")
                Text(text = "Status: ${transaction.status}")
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                when (transaction.status) {
                    "Approved" -> {
                        Button(onClick = { /* Handle cancel action */ }) {
                            Text("Void")
                        }
                    }
                    "Settled" -> {
                        Button(onClick = { /* Handle refund action */ }) {
                            Text("Refund")
                        }
                    }
                }
            }
        }
    )
}
