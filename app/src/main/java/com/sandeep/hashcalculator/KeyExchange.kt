package com.sandeep.hashcalculator


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.Time
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.sandeep.hashcalculator.fancyDialog.FancyAlertDialog
import com.sandeep.hashcalculator.fancyDialog.FancyAlertDialogListener
import kotlinx.android.synthetic.main.activity_key_exchange.*
import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.interfaces.ECPublicKey
import org.bouncycastle.math.ec.ECCurve
import org.bouncycastle.math.ec.ECPoint
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.ECGenParameterSpec
import java.time.Instant
import java.util.*
import android.util.Pair as UtilPair


@Suppress("DEPRECATION")
class KeyExchange : AppCompatActivity() {

    var alicePrivateValue: String? = null
    var bobPrivateValue: String? = null
    var bobPubX: String? = null
    var bobPubY: String? = null
    var alicePubX: String? = null
    var alicePubY: String? = null
    var time: Int? = 0
    var numBit: Int = 0

    private var visibility = false
    var qval: ECPoint? = null
    private val Tag = "secret"
//    private var keyPair: ECPublicKey? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_exchange)
        supportActionBar?.setTitle(R.string.keyExch)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.reddish))


//        GetQ.ecQ()
        //Security.insertProviderAt(org.bouncycastle.jce.provider.BouncyCastleProvider(), 1)
    //   Security.addProvider(org.bouncycastle.jcaj)
        Security.removeProvider("BC")
        Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
        val kpg: KeyPairGenerator = KeyPairGenerator.getInstance("EC", "BC")
        kpg.initialize(ECGenParameterSpec(ecParamName))
        val keyPair1 = kpg.generateKeyPair().public as ECPublicKey
        val keyPair = keyPair1
//
        qval = keyPair.q



        getRandomValues()
        key_exchange_id.setOnClickListener { slideEffect() }

        text_ecparams.setOnClickListener {
            makeTransition()

        }
        compute_pub_alice.setOnClickListener {
            getAlicePubCoord()
        }
        compute_pub_bob.setOnClickListener {
            getBobPubCoord()
        }
        secret_key_alice.setOnClickListener {
            Log.d("secret", "aliceX$bobPubX, y:$bobPubY, $alicePrivateValue")
            aliceSecretKeyCoord()

        }
        secret_key_bob.setOnClickListener {
            bobSecretKeyCoord()
        }
        selectAlgoOption()

    }



    @SuppressLint("SetTextI18n")
    private fun showText(){
        dispParam.text = "EC Parameter used: $ecParamName"
        computeTime.text = "time to required compute $time seconds"
    }

    private fun fromHex(s: String) = BigInteger(s, 16)

    private fun genRandom(): String{
        val random = BigInteger(numBit, Random())
        return random.toString()
    }

    private fun getRandomValues(){
        alice_rand.setOnClickListener {
            alice_private_value.setText(genRandom())
            alicePrivateValue = genRandom()
            Log.d("secret", "alicePrivlength: ${alicePrivateValue?.length}")
        }
        bob_rand.setOnClickListener {
            bob_private_value.setText(genRandom())
            bobPrivateValue = genRandom()
            Log.d(Tag, "bobPrivlength: ${bobPrivateValue?.length}")
        }
    }




    private fun getG(curve: ECCurve.Fp): ECPoint.Fp{

        return ECPoint.Fp(
            curve,
            curve.fromBigInteger(gx),
            curve.fromBigInteger(gy))
    }

    private fun getCurve(): ECCurve.Fp{

        return ECCurve.Fp(
            qval?.curve?.field?.characteristic,
            aval,
            bval
        )
    }

    @SuppressLint("SetTextI18n")
    private fun getAlicePubCoord(){
        try {
         //   bobPrivateValue = genRandom() // This should not be there

            val before = Time.SECOND

            val curve = getCurve()
            val G = getG(curve)

            val a = BigInteger(alicePrivateValue)
            val P = G.multiply(a)  // A = a * G
            val after = Time.SECOND
            alicex.setText(fromHex(P.xCoord.toString()).toString())
            alicey.setText(fromHex(P.yCoord.toString()).toString())
            alicePubX = fromHex(P.xCoord.toString()).toString()
            alicePubY = fromHex(P.yCoord.toString()).toString()
            Log.d(Tag, "alicePubx: ${alicePubX?.length} alicePubY${alicePubY?.length}, epoch$before")
            time = (after - before)
        }catch (ex: NullPointerException){
            alertDialog("Alice Private Value", "Please Generate Alice's Private Value")
        }

    }
    @SuppressLint("SetTextI18n")
    private fun getBobPubCoord(){
        try {
       //     alicePrivateValue = genRandom() // This should not be there

            val before = Time.SECOND
            val curve = getCurve()
            val G = getG(curve)
            val b = BigInteger(bobPrivateValue)
            val P = G.multiply(b)  // B = b * G
            val after = Time.SECOND
            bobx.setText(fromHex(P.xCoord.toString()).toString())
            boby.setText(fromHex(P.yCoord.toString()).toString())
            bobPubX = fromHex(P.xCoord.toString()).toString()
            bobPubY = fromHex(P.yCoord.toString()).toString()
            time = (after - before)
           // Log.d(Tag, "alicePubx: ${bobPubY?.length} alicePubY${bobPubX?.length}")
        }catch (ex: NullPointerException){
            alertDialog("Bob Private Value", "Please Generate Bob's Private Value")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun aliceSecretKeyCoord(){
        try {
            val before = Time.SECOND
            val curve = getCurve()
//            bobPubX = gx.toString()
//            bobPubY = gy.toString()
            val P = ECPoint.Fp(
                curve,
                curve.fromBigInteger(BigInteger(bobPubX)),
                curve.fromBigInteger(BigInteger(bobPubY))
                // Bob x and y pub values [da * (db * G) ]
            )

//            val P1 = getG(curve) // This should not be there
//            val P2 = P1.multiply(BigInteger(alicePrivateValue)) // This should not be there
            val a = BigInteger(alicePrivateValue)
            val S = P.multiply(a)
            val after = Time.SECOND
            val Sx = S.xCoord.toBigInteger().toString() // point not getting valid
            val Sy = S.yCoord.toBigInteger().toString() // point not getting valid
            alice_secretX.setText(Sx)
            alice_secretY.setText(Sy)
            time = (after - before)
        }catch (ex: NullPointerException){
            alertDialog("Bob Public Coordinate", "Please generate Bob's public coordinate")
        }catch (ex: Exception){
            alertDialog("Unknown Exception", "${ex.message}")
        }

    }

    @SuppressLint("SetTextI18n")
    @TargetApi(Build.VERSION_CODES.O)
    private fun bobSecretKeyCoord(){
        try {
            genRandom()
            val before = Time.SECOND
            val curve = getCurve()
           //alicePubX =
           // alicePubY = gy.toString()
            val P = ECPoint.Fp(
                curve,
                curve.fromBigInteger(BigInteger(alicePubX)),
                curve.fromBigInteger(BigInteger(alicePubY))
            )

       //     val P1 = getG(curve) // This should not be there
      //      val P2 = P1.multiply(BigInteger(bobPrivateValue)) // This should not be there

            val b = BigInteger(bobPrivateValue)
            val S = P.multiply(b)
            // Alice x and y pub values [db * (da * G) ]
            val after = Time.SECOND
            val Sx = S.xCoord.toBigInteger().toString() // point not valid
            val Sy = S.yCoord.toBigInteger().toString() // point not valid
            bob_secretX.setText(Sx)
            bob_secretY.setText(Sy)
            time = (after - before)
        }catch (ex: NullPointerException){
            alertDialog("Alice Public Coordinate", "Please Generate Alice Public Coordinate")
        }catch (ex: Exception){
            alertDialog("Unknown Exception", "${ex.message}")
        }

    }


    private fun makeTransition(){
        val transText = findViewById<View>(R.id.text_ecparams)
        val textPair = UtilPair.create(transText, "sharedTitle")
        val options = ActivityOptions.makeSceneTransitionAnimation(this, textPair)
        val i = Intent(this@KeyExchange, EllipticCurveParameters::class.java)
        startActivity(i, options.toBundle())
        finish()
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

    companion object{
        var ecParamName = "secp160k1"


            val curveParameter: X9ECParameters? = CustomNamedCurves.getByName(ecParamName)
            val aval = curveParameter?.curve?.a?.toBigInteger()
            val bval = curveParameter?.curve?.b?.toBigInteger()
            val gx = curveParameter?.g?.xCoord?.toBigInteger()
            val gy = curveParameter?.g?.yCoord?.toBigInteger()

    }

    private fun alertDialog(name: String, message: String){
        FancyAlertDialog.Builder(this)
            .setTitle(name)
            .setMessage(message)
            .setNegativeBtnText("close")
            .setAnimation(FancyAlertDialog.Animation.POP)
            .isCancellable(true)
            .setIcon(R.drawable.ic_warning_black_24dp)
            .setBackgroundColor(R.color.dialogBackground)
            .setNegativeBtnBackground(R.color.dialogBtnBackground)
            .OnNegativeClicked(object: FancyAlertDialogListener{
                override fun OnClick() {
                    Toast.makeText(this@KeyExchange, "alert closed", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
    }


    private fun selectAlgoOption(){
        s128.setOnClickListener {
            selectAlgo("secp128r1")
            showText()
           // Log.d(Tag, "$ecParamName, $numBit")
        }
        s160.setOnClickListener {
            showText()
            selectAlgo("secp160k1")
         //   Log.d(Tag, "$ecParamName, $numBit")
        }
        s160r.setOnClickListener {
            showText()
            selectAlgo("secp160r1")
          //  Log.d(Tag, "$ecParamName, $numBit")
        }
        s192k.setOnClickListener {
            showText()
            selectAlgo("secp192k1")
        }
        s192r.setOnClickListener {
            showText()
            selectAlgo("secp192r1")
        }
        s224.setOnClickListener {
            showText()
            selectAlgo("secp2241")
        }
        s256r1.setOnClickListener {
            showText()
            selectAlgo("secp256r1")
        }
        s256k1.setOnClickListener {
            showText()
            selectAlgo("secp256k1")
        }
    }
    private fun selectAlgo(choice: String){
        when(choice){
            "secp128r1" -> {
                ecParamName = "secp128r1"
                numBit = 128

            }
            "secp160k1" -> {
                ecParamName = "secp160k1"
                numBit = 160
            }
            "secp160r1" ->{
                ecParamName = "secp160r1"
                numBit = 160
            }
            "secp192k1" -> {
                ecParamName = "secp192k1"
                numBit = 192
            }
            "secp192r1" -> {
                ecParamName = "secp192r1"
                numBit = 192
            }
            "secp224r1" -> {
                ecParamName = "secp224r1"
                numBit = 224
            }
            "secp256r1" -> {
                ecParamName = "secp256r1"
                numBit = 256
            }
            "secp256k1" -> {
                ecParamName = "secp256k1"
                numBit = 256
            }
        }
    }
}

