package com.sandeep.hashcalculator

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.util.Pair as UtilPair
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_key_exchange.*
import org.bouncycastle.jce.provider.BrokenPBE
import java.lang.Exception
import java.math.BigInteger
import java.util.*


class KeyExchange : AppCompatActivity() {

    private var visibility = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_exchange)
        supportActionBar?.setTitle(R.string.keyExch)

        getRandomValues()
        key_exchange_id.setOnClickListener { slideEffect() }

        text_ecparams.setOnClickListener {
            makeTransition()
        }

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
    private fun makeTransition(){
        val transText = findViewById<View>(R.id.text_ecparams)
        val textPair = UtilPair.create(transText, "sharedTitle")
        val options = ActivityOptions.makeSceneTransitionAnimation(this, textPair)
        val i = Intent(this@KeyExchange, EllipticCurveParameters::class.java)
        startActivity(i, options.toBundle())
    }

    private fun slideEffect(){
        val transition = Slide(Gravity.BOTTOM)
        //val x= android.transition.
        TransitionManager.beginDelayedTransition(key_exchange_id, transition)
        text_ecparams.visibility = if (visibility) View.INVISIBLE else View.VISIBLE
        visibility = !visibility
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.back-> {
                val back = Intent(this@KeyExchange, MainActivity::class.java)
                startActivity(back)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

