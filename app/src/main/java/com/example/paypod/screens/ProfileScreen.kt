package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paypod.R
import com.example.paypod.model.ProfileViewModel

private val orbitron = FontFamily(
    Font(R.font.orbitronbold)
)

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel()) {
    val profile = viewModel.profile.observeAsState()
    val loading = viewModel.isLoading.observeAsState(false) // Track loading state

    LaunchedEffect(Unit) {
        viewModel.getProfileDetails()  // Ensure the API call is triggered
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderProfile(navController)

        when {
            loading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            profile.value != null -> {
                ProfileDetailsSection(
                    username = profile.value?.login ?: "N/A",
                    firstName = profile.value?.mainFirstName ?: "N/A",
                    lastName = profile.value?.mainLastName ?: "N/A",
                    email = profile.value?.mainEmail ?: "N/A",
                    phone = profile.value?.mainPhoneNumber ?: "N/A"
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error loading profile.", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun HeaderProfile(navController: NavController) {
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
            text = "Profile",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun ProfileDetailsSection(
    username: String,
    firstName: String,
    lastName: String,
    email: String,
    phone: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Username
        Text(
            text = username,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Profile details
        ProfileItem(label = "First Name:", value = firstName)
        ProfileItem(label = "Last Name:", value = lastName)
        ProfileItem(label = "Email:", value = email)
        ProfileItem(label = "Phone:", value = phone)
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(2f)
        )
    }
}
