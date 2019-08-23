package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_key_exchange.*
import java.math.BigInteger
import java.util.*
import kotlin.random.nextULong

class KeyExchange : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_exchange)

        getRandomValues()
    }
    private fun randomValue(): String{
        val random = BigInteger(256, Random())
        return random.toString()
    }
    private fun getRandomValues(){
        alice_rand.setOnClickListener {
            alice_private_value.setText(randomValue())
        }
        bob_rand.setOnClickListener {
            bob_private_value.setText(randomValue())
        }
    }
}

