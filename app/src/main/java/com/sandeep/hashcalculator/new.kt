package com.sandeep.hashcalculator

import org.bouncycastle.jce.ECNamedCurveTable
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import java.security.Security.addProvider
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import org.bouncycastle.asn1.x500.style.RFC4519Style.name
import java.math.BigInteger
import java.security.*
import java.util.*
import javax.crypto.KeyGenerator



fun main(args: Array<String>){

    val b = BigInteger(256, Random())
    println("$b, ${b.toString().length}")
    println("40307746164886925000131121516174591540288286368887692170340874190989050390152".length)

}