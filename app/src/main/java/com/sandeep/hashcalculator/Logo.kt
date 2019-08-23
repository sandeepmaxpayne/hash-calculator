package com.sandeep.hashcalculator

import android.animation.AnimatorInflater
import android.content.Intent
import com.sandeep.hashcalculator.MainActivity
import com.sandeep.hashcalculator.R


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_logo.*

class Logo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_logo)
        supportActionBar?.hide()
        val timeThread = object : Thread(){
                override fun run(){
                    try{
                        sleep(4000)


                        val i = Intent(this@Logo, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }catch (ex: Exception){
                        ex.printStackTrace()
                    }
                }
        }
        timeThread.start()
        logoanim()

    }

    private fun logoanim(){
        val rotateLogo = AnimatorInflater.loadAnimator(this@Logo , R.animator.rotate_logo)
        rotateLogo.apply {
            setTarget(logo_ecd)
            start()
        }
    }

}
