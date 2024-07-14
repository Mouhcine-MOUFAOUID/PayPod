package com.example.paypod.model
data class TransactionResponse(
    val transactionDTOS: TransactionDTOContent,
    val totalElements: Long
)

data class TransactionDTOContent(
    val content: List<TransactionDTO>,
    val pageable: Pageable,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)

data class Sort(
    val unsorted: Boolean,
    val sorted: Boolean,
    val empty: Boolean
)

data class Pageable(
    val sort: Sort,
    val offset: Long,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)

data class TransactionDTO(
    val id: Int = 0,
    val transactionId: String = "",
    val authorizationNumber: Int = 0,
    val primaryAcountNumber: String = "",
    val processingCode: String = "",
    val transactionAmount: Double = 0.0,
    val transmissionDateTime: String = "",
    val dateTimeLocalTransaction: String = "",
    val expirationDate: String = "",
    val institutionId: String = "",
    val merchantId: String = "",
    val currencyCode: String? = null,
    val cvc: String = "",
    val paymentMethod: String? = null,
    val aid: String? = null,
    val status: String = "",
    val pin: String? = null,
    val deviceId: String = "",
    val reasonForRefused: String? = null,
    val requestTimestamp: Long = 0,
    val sdkTimestampRequestI: Long = 0,
    val sdkTimestampRequestF: Long = 0,
    val frontTimestampRequestI: Long = 0,
    val frontTimestampRequestF: Long = 0,
    val issuerName: String? = null,
    val acquirerName: String? = null,
    val voidTimestamp: Long = 0,
    val creditOrDebit: String = "",
    val settled: Boolean = false
)