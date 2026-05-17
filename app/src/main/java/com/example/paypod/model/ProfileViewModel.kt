package com.example.paypod.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypod.api.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileViewModel : ViewModel() {
    private val apiService: ApiService

    val profile = MutableLiveData<ProfileDetails?>()
    val isLoading = MutableLiveData(false)
    private val errorMessage = MutableLiveData<String?>() // For tracking error messages

    init {
        // Initialize Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.8.100:9968/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getProfileDetails() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getProfileDetails() // Make API call
                if (response.isSuccessful) {
                    profile.value = response.body() // Set the profile data if successful
                } else {
                    errorMessage.value = "Failed to load profile data" // Handle unsuccessful response
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}" // Handle network or other errors
            } finally {
                isLoading.value = false
            }
        }
    }
}

data class ProfileDetails(
    val id: Int?,
    val login: String?,
    val mainFirstName: String?,
    val mainLastName: String?,
    val mainEmail: String?,
    val mainPhoneNumber: String?,
    val country: String?,
    val businessAddress: String?,
    val merchantCode: String?,
    // Add other fields as needed based on your API response
)
