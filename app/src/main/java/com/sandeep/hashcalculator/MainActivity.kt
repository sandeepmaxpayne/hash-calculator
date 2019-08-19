package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.Signature

class MainActivity : AppCompatActivity() {

    var input: String? = null
    var ecdInput: String? = null
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
        calcEC.setOnClickListener {
            generateECDSAKey()
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

    @SuppressLint("SetTextI18n")
    private fun generateECDSAKey(){
        val keyGen = KeyPairGenerator.getInstance("EC")
        val random = SecureRandom.getInstance("SHA1PRNG")

        keyGen.initialize(256, random)
        val pair = keyGen.generateKeyPair()
        val priv = pair.private
        val pub = pair.public
      //  Log.d("keys", "pub: ${pub.hashCode()} \n priv: ${priv.hashCode()}")


        val ecd = Signature.getInstance("SHA256withECDSA")
        ecd.initSign(priv)

        ecdInput = enterDataEC.text.toString()
        Log.d("input", "input:$ecdInput")
        val ecdSha = hexString(stringtoByteArray(ecdInput!!))
     //   Log.d("input", "ecdsha:$ecdSha")


        val strByte = ecdInput?.toByteArray(StandardCharsets.UTF_8)
        ecd.update(strByte)
        val realSig = ecd.sign()
        val sig = "Signature: ${BigInteger(1, realSig).toString(16)}"
      //  Log.d("sig", "Sign:$sig \n len:${sig.length}")


        if (ecdInput?.length!! > 0) {
            dispECD.text = "Public Key: $pub \n Private Key: $ecdSha"
            disSig.text = sig
        }else{
            dispECD.text = "Public Key to show"
            disSig.text = "Signature to show"
        }

    }
}
