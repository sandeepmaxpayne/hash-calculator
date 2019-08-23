package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.reddish))

        calcHash.setOnClickListener {
            calculateHash()
        }
        inputData.setOnClickListener {
            if (inputData.text.isNullOrEmpty()) {
                showHash.text = "Calculated Hash"
            }
        }
        nextSig.setOnClickListener {
            val intent = Intent(this@MainActivity, EcKeyGen::class.java)
            startActivity(intent)
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
            showHash.text = "Hash Value: $hashvalue"
        } else {
            showHash.text = "Calculated Hash"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ec_key, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.key_exchange-> {
                val back = Intent(this@MainActivity,KeyExchange::class.java)
                startActivity(back)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
