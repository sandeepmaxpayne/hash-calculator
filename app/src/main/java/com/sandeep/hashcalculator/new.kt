package com.sandeep.hashcalculator



import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
import org.bouncycastle.jce.interfaces.ECPublicKey
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.InvalidKeySpecException
import java.util.*


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



fun main(args: Array<String>){

    println(fromHex(123.toString(16)))
  ecCurve()
   //key()


}



//@Suppress("DEPRECATION")
private fun ecCurve() {
    fun encode(publicKey: ECPublicKey): ByteArray? {
        return publicKey.q.getEncoded(true)
    }
    // val key : PublicKey? = null
    val ec_param_name = "secp160k1"
    Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
//    val keyGenerator: KeyPairGenerator = KeyPairGenerator.getInstance("ECDH", "BC")
//    //keyGenerator.initialize(1024)
//    keyGenerator.initialize(ECGenParameterSpec("prime192v1"), SecureRandom())
//    val kpair: KeyPair? = keyGenerator.genKeyPair()
    val kpg: KeyPairGenerator = KeyPairGenerator.getInstance("EC", "BC")
    kpg.initialize(ECGenParameterSpec(ec_param_name))
    val keyPair =
        kpg.generateKeyPair().public as ECPublicKey
    println(keyPair)

    val CURVE_PARAMS: X9ECParameters? = CustomNamedCurves.getByName(ec_param_name)
    val aval = CURVE_PARAMS?.curve?.a?.toBigInteger()
    val bval = CURVE_PARAMS?.curve?.b?.toBigInteger()
    val n = CURVE_PARAMS?.n
    val gx = CURVE_PARAMS?.g?.xCoord?.toBigInteger()
    val gy = CURVE_PARAMS?.g?.yCoord?.toBigInteger()


    val curves =
        ECDomainParameters(CURVE_PARAMS?.curve, CURVE_PARAMS?.g, CURVE_PARAMS?.n, CURVE_PARAMS?.h)
    val half_curve_order = CURVE_PARAMS?.n?.shiftRight(1)
    val secureRandom = SecureRandom()




    val eckey = CURVE_PARAMS?.h
    val qval = keyPair.q


    println("qval: $qval")
    println("qval: ${qval.affineXCoord}")
    println("qvalchar: ${qval.curve.field.characteristic}")
    println(fromHex(qval.affineYCoord.toString()).toString().length)
    //  println("1404991880376980322707896681342960449049418597035".length)


//    function get_G(curve) {
//        return new ECPointFp(curve,
//        curve.fromBigInteger(gx),
//        curve.fromBigInteger(gy));
//    }
    var n1 = n?.subtract(BigInteger.ONE)
    var rng = SecureRandom()
//    val cur = get_curve()
    //   println(cur)


    @Throws(
        NoSuchProviderException::class,
        NoSuchAlgorithmException::class,
        InvalidKeySpecException::class
    )


    fun savePublicKey(key: PublicKey): ByteArray? {
        //return key.getEncoded();


        val eckey =
            key as ECPublicKey
        return eckey.q.getEncoded(true)
    }

    fun getG(curve: ECCurve.Fp): ECPoint.Fp {
        return ECPoint.Fp(
            curve,
            curve.fromBigInteger(gx),
            curve.fromBigInteger(gy)
        )
    }

    //val qq = (fromHex(qval.affineXCoord.toString()), fromHex)
    fun bigQ(x: String, y: String): Pair<BigInteger, BigInteger> {
        return Pair(fromHex(x), fromHex(y))
    }
    println(qval.getEncoded(true))
    fun getcurve(): ECCurve.Fp {
        return ECCurve.Fp(
            qval.curve.field.characteristic,
            //bigQ(qval.affineXCoord.toString(), qval.affineYCoord.toString()),

            aval,
            bval
        )
    }


//        if (document.getElementById('alice_priv').innerHTML.length === 0) {
//            alert("Please generate Alice's private value first")
//            return
//        }
    val before = Date()
    val curve = getcurve()
    val G = getG(curve)

    println("G: $G")
    println("Gxy: ${fromHex(G.affineXCoord.toString())}, ${fromHex(G.affineYCoord.toString())}")


    //val a = BigInteger(document.getElementById('alice_priv').innerHTML)
    println("508823125271755536873046716475775511128178341582".length)
    val a = BigInteger("213386303091419595375114803924781164503310845069")
    val P = G.multiply(a)


    println("Pz: ${P.getZCoord(1)}")

    //  val after = Date()
//        val x = P.affineXCoord.toBigInteger().toString()
    //      val  y = P.affineYCoord.toBigInteger().toString()
    //println("aval: $aval")


    println("P: $P")
    println("P: ${fromHex(P.xCoord.toString())}, ${fromHex(P.getZCoord(1).toString())}")
    println(fromHex("d3f0860a3f94e13c628579887dc1406a").toString().length)
    println("118150763105817221558337383938208097087657523298".length)
    // println("$x $y")


    println("alength: ${"883011946207649850265918082470453708352236023296".length} blentgh: ${"1117349004357633500276498510191050585206312780785".length}")


    ///Alice_key

    fun getP(curve: ECCurve.Fp): ECPoint.Fp {
        return ECPoint.Fp(
            curve,
            curve.fromBigInteger(BigInteger("338530205676502674729549372677647997389429898930")),
            curve.fromBigInteger(BigInteger("842365456698940303598009444920994870805149798382"))

        //"391364184041841077369899923801481224577173594674"
       //1149713774003874565035727584377657891074561055739
        )
    }
    val before1 = Date()
    val curve1 = getcurve()
//    @Suppress("DEPRECATION") val P1 = ECPoint.Fp(
////        curve1,
////        curve1.fromBigInteger(BigInteger("901431496915388754554004297175065533160544023155")),
////        curve1.fromBigInteger(BigInteger("171123727126429582249210994039202110284291869599"))
////    )
    val P1 = getP(curve1)
    println("cueve1: $curve1")
    val a1 = BigInteger("541249053587824575193224016293050928346160890099") // private key
    println("gx: ${gx.toString().length} , gy: ${gy.toString().length}, $gx, $gy")
    println("p1:$P1")
    println("${P1.affineXCoord.bitLength()}, ${P1.affineYCoord.bitLength()}, ${fromHex(P1.affineXCoord.toString())}")
    println("${G.affineXCoord.bitLength()}, ${G.affineYCoord.bitLength()}")

    val S = P1.multiply(a1)
    val after1 = Date()

    val Sx = S.xCoord.toBigInteger().toString()
    val Sy = S.yCoord.toBigInteger().toString()

    println("S: $S")
    println("sx: ${Sx.length}")
    println("sy: $Sy")


}




//fun setECParams(name: String){
//    var c = CustomNamedCurves.getByName(name)
//    val d = c.curve.get
//}
//
//fun ECKey(secureRandom: SecureRandom?) {
//    val generator = ECKeyPairGenerator()
//    val keygenParams =
//        ECKeyGenerationParameters(CURVE, secureRandom)
//    generator.init(keygenParams)
//    val keypair: AsymmetricCipherKeyPair = generator.generateKeyPair()
//    val privParams =
//        keypair.private as ECPrivateKeyParameters
//    val pubParams =
//        keypair.public as ECPublicKeyParameters
//    val priv = privParams.d
//    val pub = LazyECPoint(CURVE.getCurve(), pubParams.q.getEncoded(true))
//    creationTimeSeconds = Utils.currentTimeSeconds()
//}




