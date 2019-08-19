package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    var input: String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcHash.setOnClickListener {
            calculateHash()
        }
        inputData.setOnClickListener {
            if (inputData.text.isNullOrEmpty()) {
                showHash.text = "Calculated Hash"
            }
        }

    }

    private fun stringtoByteArray(input: String): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
    }

    private fun hexString(hash: ByteArray): String {
        val num = BigInteger(1, hash)

        val hexString = StringBuilder(num.toString(16))

        return hexString.toString()

    }

    @SuppressLint("SetTextI18n")
    private fun calculateHash() {
        input = inputData.text.toString()
        // Log.d("msg","Data: $input")
        if (input?.length!! > 0 ) {
            val hashvalue = hexString(stringtoByteArray(input!!))
            showHash.text = hashvalue
        } else {
            showHash.text = "Calculated Hash"
        }
    }
}
