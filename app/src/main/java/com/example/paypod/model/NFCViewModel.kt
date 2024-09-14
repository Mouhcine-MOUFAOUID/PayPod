package com.example.paypod.model

import android.app.Activity
import android.app.Application
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypod.api.PcscProvider
import com.github.devnied.emvnfccard.parser.EmvTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NFCViewModel(application: Application) : AndroidViewModel(application), NfcAdapter.ReaderCallback {

    private val _cardNumber = MutableStateFlow<String?>(null)
    val cardNumber: StateFlow<String?> = _cardNumber

    private val _expirationDate = MutableStateFlow<String?>(null)
    val expirationDate: StateFlow<String?> = _expirationDate

    private val _scanSuccess = MutableStateFlow(false)
    val scanSuccess: StateFlow<Boolean> = _scanSuccess

    private val _transactionAmount = mutableDoubleStateOf(0.00)  // Variable to store transaction amount
    private val transactionAmount: Double
        get() = _transactionAmount.doubleValue

    private var mNfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(application)

    @RequiresApi(Build.VERSION_CODES.O)
    fun enableNfcReader(activity: Activity) {
        mNfcAdapter?.let {
            val options = Bundle().apply {
                putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
            }
            it.enableReaderMode(
                activity,
                this,
                NfcAdapter.FLAG_READER_NFC_A or
                        NfcAdapter.FLAG_READER_NFC_B or
                        NfcAdapter.FLAG_READER_NFC_F or
                        NfcAdapter.FLAG_READER_NFC_V or
                        NfcAdapter.FLAG_READER_NFC_BARCODE or
                        NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                options
            )
        }
    }

    fun resetScanState() {
        _cardNumber.value = ""
        _expirationDate.value = ""
        _scanSuccess.value = false
        _transactionAmount.doubleValue = 0.00
    }

    fun setTransactionAmount(amount: Double) {
        _transactionAmount.doubleValue = amount
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDateTime(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss")
        return now.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTagDiscovered(tag: Tag?) {
        viewModelScope.launch(Dispatchers.IO) {
            var isoDep: IsoDep? = null
            var attempts = 0
            val maxAttempts = 3
            val delayBetweenAttempts = 500L // 500ms delay between attempts

            while (attempts < maxAttempts) {
                try {
                    isoDep = IsoDep.get(tag)
                    isoDep?.connect()

                    val provider = PcscProvider()
                    provider.setmTagCom(isoDep)

                    val config = EmvTemplate.Config()
                        .setContactLess(true)
                        .setReadAllAids(true)
                        .setReadTransactions(true)
                        .setRemoveDefaultParsers(false)
                        .setReadAt(true)

                    val parser = EmvTemplate.Builder()
                        .setProvider(provider)
                        .setConfig(config)
                        .build()

                    val card = parser.readEmvCard()
                    val cardNumber = card.cardNumber ?: "Unknown"
                    Log.d("PaymentResultCardNumber", cardNumber)

                    val formattedExpireDate = card.expireDate?.let {
                        SimpleDateFormat("MM/yy", Locale.getDefault()).format(it)
                    } ?: "Unknown"

                    Log.d("PaymentResultDate", formattedExpireDate)

                    _cardNumber.value = cardNumber
                    _expirationDate.value = formattedExpireDate
                    _scanSuccess.value = true

                    // Send the transaction request using the dynamic amount directly
                    val transactionRequest = TransactionRequest(
                        primaryAcountNumber = cardNumber,
                        processingCode = "001000",
                        transactionAmount = transactionAmount,  // Use the dynamic amount directly
                        transmissionDateTime = getFormattedDateTime(),
                        dateTimeLocalTransaction = getFormattedDateTime(),
                        expirationDate = "280615",
                        institutionId = "00002",
                        merchantId = "20000",
                        currencyCode = "MAD",
                        cvc = "341",
                        deviceId = "200001"
                    )

                    val repository = TransactionRepository()
                    repository.initiateTransaction(transactionRequest)

                    break // Exit loop if successful

                } catch (e: IOException) {
                    Log.e("NFC", "IOException during NFC tag communication, attempt ${attempts + 1} of $maxAttempts", e)
                    attempts++
                    if (attempts >= maxAttempts) {
                        Log.e("NFC", "Max attempts reached. Could not communicate with NFC tag.")
                    } else {
                        try {
                            kotlinx.coroutines.delay(delayBetweenAttempts)
                        } catch (interruptedException: InterruptedException) {
                            Log.e("NFC", "Coroutine delay interrupted", interruptedException)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("NFC", "Exception during NFC tag communication", e)
                    break
                } finally {
                    try {
                        isoDep?.close()
                    } catch (e: IOException) {
                        Log.e("NFC", "IOException during IsoDep close", e)
                    }
                }
            }
        }
    }
}