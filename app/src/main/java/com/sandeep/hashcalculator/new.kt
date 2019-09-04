package com.sandeep.hashcalculator

import org.bouncycastle.asn1.x500.style.RFC4519Style.cn
import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger


/*fun main(args: Array<String>){

    val b = BigInteger(256, Random())
   // println("$b, ${b.toString().length}")
   // println("40307746164886925000131121516174591540288286368887692170340874190989050390152".length)

    generateECKeys()
}

fun generateECKeys(){
    try {
        Security.addProvider(BouncyCastleProvider())
        val parameterSpec: ECNamedCurveParameterSpec =
            ECNamedCurveTable.getParameterSpec("secp256r1")
        var keyPairGenerator: KeyPairGenerator =
            KeyPairGenerator.getInstance("ECDH", "BC")
        keyPairGenerator.initialize(parameterSpec)
        val keyPair: KeyPair = keyPairGenerator.generateKeyPair()
        val publickeyJava: PublicKey = keyPair.public
        //(TAG_LOG, "X & Y values are ....$publickeyJava")
        println(
            "LOG_TAG"+
            "BigInteger X value is = " + (publickeyJava as ECPublicKey).w.affineX.toString()
        )
        println(
            "LOG_TAG"+
            "BigInteger Y value is = " + publickeyJava.w.affineY.toString()
        )
        *//* .....code to generate shared secret   ..... *//*
        val y = "12345645646456"
       val pubKey = keyPair.public as ECPublicKey
        val ecp = pubKey.w
        println("X: ${ecp.affineX} \n ${ecp.affineY}")
        println(ecp.affineX.toString().length)
        println(ecp.affineY.toString().length)

        println("203520114162904107873991457957346892027982641970".length)

    } catch (e: Exception) {
        e.printStackTrace()}
}*/

//ECPublicKey publickeyJava = (ECPublicKey)keyPair.getPublic();
//ECPoint ecp = chiavePubblica.getW();
//// to access X and Y you can use
//ecp.getAffineX()
//ecp.getAffineY()




fun fromHex(s: String) = BigInteger(s, 16)
internal fun secp128r1(): X9ECParameters {
    // p = 2^128 - 2^97 - 1

    val p = fromHex("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF")
    val a= fromHex("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC")
    val b= fromHex("E87579C11079F43DD824993C2CEE5ED3")


    val n= fromHex("FFFFFFFE0000000075A30D1B9038A115")
    val h= BigInteger.ONE
    val curve = ECCurve.Fp(p, a, b) as ECCurve

    val G = curve.decodePoint(Hex.decode(
        "04"
                + "161FF7528B899B2D0C28607CA52C5B86"
                + "CF5AC8395BAFEB13C02DA292DDED7A83"
    ))
    println("$p \n $a \n $b \n $n \n \n ${G.affineXCoord} \n ${curve.order}")
    return X9ECParameters(curve, G, n, h)
}

internal fun secp192k1(): X9ECParameters{
    // p = 2^192 - 2^32 - 2^12 - 2^8 - 2^7 - 2^6 - 2^3 - 1

    val p= fromHex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37")
    val a= BigInteger.ZERO
    val b= fromHex("3")
    //byte[] S = null;


    val n= fromHex("FFFFFFFFFFFFFFFFFFFFFFFE26F2FC170F69466A74DEFD8D")
    val h= BigInteger.ONE
    val curve = ECCurve.Fp(p, a, b)
    val G = curve.decodePoint(Hex.decode(
        "04"
                + "DB4FF10EC057E9AE26B07D0280B7F4341DA5D1B1EAE06C7D"
                + "9B2F2F6D9C5628A7844163D015BE86344082AA88D95E2F9D"
    ))
    val x = fromHex("DB4FF10EC057E9AE26B07D0280B7F4341DA5D1B1EAE06C7D")

    val y = x.toString() == ("5377521262291226325198505011805525673063229037935769709693")
    println(y)
    println("Q: $p \n A: $a \n B: $b \n N: $n \n h: $h \n ${G} \n ${curve.order}")
    return X9ECParameters(curve, G, n, h)
}



fun main(args: Array<String>){
    //secp128r1()
    secp192k1()
}

