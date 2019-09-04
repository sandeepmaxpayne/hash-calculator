package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.activity_elliptic__curve__parameters.*
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger


class EllipticCurveParameters : AppCompatActivity() {

    var selChoice: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elliptic__curve__parameters)
        supportActionBar?.setTitle(R.string.ecpara)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.reddish))
        selectAlgoOption()

        textView.setOnClickListener { makeTransition() }
    }

    fun fromHex(s: String) = BigInteger(s, 16)

    @SuppressLint("SetTextI18n")
    internal fun secp128r1(){
        //p = 2^128 - 2^97 - 1
        val p =  fromHex("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF")
        val a= fromHex("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC")
        val b= fromHex("E87579C11079F43DD824993C2CEE5ED3")


        val n= fromHex("FFFFFFFE0000000075A30D1B9038A115")
        val h= BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b) as ECCurve

        val G = curve.decodePoint(
            Hex.decode(
                "04"
                        + "161FF7528B899B2D0C28607CA52C5B86"
                        + "CF5AC8395BAFEB13C02DA292DDED7A83"
            ))
        getValue(p, a, b, n, G)
       // Log.d("curve", "curve: $curve")
        parameter_status.setText(R.string.secp128r1)

    }

    @SuppressLint("SetTextI18n")
    internal fun secp160k1(){
        // p = 2^160 - 2^32 - 2^14 - 2^12 - 2^9 - 2^8 - 2^7 - 2^3 - 2^2 - 1
        val p = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73")
        val a = BigInteger.ZERO
        val b= fromHex("7")
        val n= fromHex("0100000000000000000001B8FA16DFAB9ACA16B6B3")
        val h= BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "3B4C382CE37AA192A4019E763036F4F5DD4D7EBB"
                    + "938CF935318FDCED6BC28286531733C3F03C4FEE"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp160k1)
    }

    @SuppressLint("SetTextI18n")
    internal fun secp160r1(){
        // p = 2^160 - 2^31 - 1

        val p = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF")
        val a = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFC")
        val b = fromHex("1C97BEFC54BD7A8B65ACF89F81D4D4ADC565FA45")

        val n= fromHex("0100000000000000000001F4C8F927AED3CA752257")
        val h = BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "4A96B5688EF573284664698968C38BB913CBFC82"
                    + "23A628553168947D59DCC912042351377AC5FB32"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp160r1)
    }

    @SuppressLint("SetTextI18n")
    internal fun sec192k1(){
        // p = 2^192 - 2^32 - 2^12 - 2^8 - 2^7 - 2^6 - 2^3 - 1
        val p= fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37")
        val a = BigInteger.ZERO
        val b= fromHex("3")
        val n = fromHex("FFFFFFFFFFFFFFFFFFFFFFFE26F2FC170F69466A74DEFD8D")
        val h= BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "DB4FF10EC057E9AE26B07D0280B7F4341DA5D1B1EAE06C7D"
                    + "9B2F2F6D9C5628A7844163D015BE86344082AA88D95E2F9D"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp192k1)
    }
    @SuppressLint("SetTextI18n")
    internal fun sec192r1(){
        // p = 2^192 - 2^64 - 1
        val p = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF")
        val a = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")
        val b = fromHex("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")
        val n = fromHex("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831")
        val h= BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF1012"
                    + "07192B95FFC8DA78631011ED6B24CDD573F977A11E794811"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp192r1)
    }
    internal fun sec224r1(){
        // p = 2^224 - 2^96 + 1
        val p = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001")
        val a= fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFE")
        val b= fromHex("B4050A850C04B3ABF54132565044B0B7D7BFD8BA270B39432355FFB4")
        val n = fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFF16A2E0B8F03E13DD29455C5C2A3D")
        val h = BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "B70E0CBD6BB4BF7F321390B94A03C1D356C21122343280D6115C1D21"
                    + "BD376388B5F723FB4C22DFE6CD4375A05A07476444D5819985007E34"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp224r1)

    }
    internal fun secp256r1(){
        // p = 2^224 (2^32 - 1) + 2^192 + 2^96 - 1
        val p = fromHex("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF")
        val a = fromHex("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC")
        val b = fromHex("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B")
        val n = fromHex("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551")
        val h = BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296"
                    + "4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp256r1)
    }
    internal fun secp256k1(){
        val p = fromHex("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f")
        val a = fromHex("0")
        val b = fromHex("7")
        val n = fromHex("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141")
        val h = BigInteger.ONE
        val curve = ECCurve.Fp(p, a, b)
        val G = curve.decodePoint(Hex.decode(
            "04"
                    + "79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798"
                    + "483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8"
        ))
        getValue(p, a, b, n, G)
        parameter_status.setText(R.string.secp256k1)
    }
    internal fun selectAlgoOption(){
        sec128.setOnClickListener {
            selChoice = "secp128r1"
            selectAlgo(selChoice!!)
        }
        secp160.setOnClickListener {
            selChoice = "secp160k1"
            selectAlgo(selChoice!!)

        }
        secp160r.setOnClickListener {
            selChoice = "secp160r1"
            selectAlgo(selChoice!!)
        }
        secp192k.setOnClickListener {
            selChoice = "sec192k1"
            selectAlgo(selChoice!!)
        }
        secp192r.setOnClickListener {
            selChoice = "sec192r1"
            selectAlgo(selChoice!!)
        }
        secp224.setOnClickListener {
            selChoice = "sec224r1"
            selectAlgo(selChoice!!)
        }
        secp256r1.setOnClickListener {
            selChoice = "secp256r1"
            selectAlgo(selChoice!!)
        }
        secp256k1.setOnClickListener {
            selChoice = "secp256k1"
            selectAlgo(selChoice!!)
        }
    }
    private fun selectAlgo(choice: String){
        when(choice){
            "secp128r1" -> secp128r1()
            "secp160k1" -> secp160k1()
            "secp160r1" -> secp160r1()
            "sec192k1" -> sec192k1()
            "sec192r1" -> sec192r1()
            "sec224r1" -> sec224r1()
            "secp256r1" -> secp256r1()
            "secp256k1" -> secp256k1()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getValue(p: BigInteger, a: BigInteger, b: BigInteger, n: BigInteger, G: ECPoint){
        curve_Q.setText(p.toString())
        curve_A.setText(a.toString())
        curve_B.setText(b.toString())
        integer_n.setText(n.toString())
        g_x.setText(fromHex(G.affineXCoord.toString()).toString())
        g_y.setText(fromHex(G.affineYCoord.toString()).toString())
    }
    private fun makeTransition(){
        val transText = findViewById<View>(R.id.textView)
        val textPair = Pair.create(transText, "sharedTitle")
        val options = ActivityOptions.makeSceneTransitionAnimation(this, textPair)
        val i = Intent(this@EllipticCurveParameters, KeyExchange::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i, options.toBundle())

    }
}
