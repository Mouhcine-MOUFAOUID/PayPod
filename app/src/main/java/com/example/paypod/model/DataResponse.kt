package com.example.paypod.model

data class DataResponse(
    val users: List<User> // List of User objects
)

data class User(
    val id: String, // Assuming ID is a string
    val firstName: String,
    val lastName: String,
    val maidenName: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String, // Assuming birthDate is a string
    val image: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val eyeColor: String,
    val hair: Any, // More complex data structure needed for hair
    val ip: String,
    val address: Any, // More complex data structure needed for address
    val macAddress: String,
    val university: String,
    val bank: Any, // More complex data structure needed for bank
    val company: Any, // More complex data structure needed for company
    val ein: String,
    val ssn: String,
    val userAgent: String,
    val crypto: Any, // More complex data structure needed for crypto
    val role: String
)