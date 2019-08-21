package com.sandeep.hashcalculator

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_ec_key_gen.*
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.StringBuilder
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*

class EcKeyGen : AppCompatActivity() {


    var enterData: String? = null
    var pKey: String? = null
    var pubKey: String? = null
    var sig: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ec_key_gen)

        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.dark))

        genPrivateKey.setOnClickListener {
            createPrivateKey()
        }
        gen_public_key.setOnClickListener {
            showPublicKey()
        }
        gen_sign.setOnClickListener {
            genSign()
        }
        verifySig.setOnClickListener {
            showVerifySignature()
        }


    }

    //create random keyPair on cur

    private fun stringtoByteArray(input: String): ByteArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        return messageDigest.digest(input.toByteArray(StandardCharsets.UTF_8))
    }
    private fun hexString(hash: ByteArray): String {
        val num = BigInteger(1, hash)

        val hexString = StringBuilder(num.toString(16))

        return hexString.toString()

    }

//    private fun genKey(){
//        enterData = enterDataEC.text.toString()
//        val stringtohash = hexString(stringtoByteArray(enterData!!)).toBigInteger(16)
//      //  val key = KeyGen.random32BytePrivateKey
//       // val priv = KeyGen.newKeyPair(stringtohash, Secp256k1)
//        val randomkey = KeyGen.newInstance(Secp256k1)
//        val priv = stringtohash
//        val fromPrivateKey = KeyGen.newKeyPair(priv, Secp256k1)
//        //  Log.d("hash", "data: $enterData , hash: $stringtohash, ${stringtohash.toString().length}")
//        Log.d("hash", "$fromPrivateKey")
//    }

    @SuppressLint("NewApi")
    @Throws(
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        UnsupportedEncodingException::class,
        SignatureException::class
    )

    private fun sender(): JSONObject{
        val ecSpec = ECGenParameterSpec(SPEC)
        val g = KeyPairGenerator.getInstance("EC")
        g.initialize(ecSpec, SecureRandom())
        val keypair = g.generateKeyPair()
        val publicKey = keypair.public
        val privateKey = keypair.private

        val plaintext = enterDataEC.text.toString()

        //...... sign
        val ecdsaSign = Signature.getInstance(ALGO)
        ecdsaSign.initSign(privateKey)
        ecdsaSign.update(plaintext.toByteArray(charset("UTF-8")))
        val signature = ecdsaSign.sign()
        val pub = Base64.getEncoder().encodeToString(publicKey.encoded)
        val sig = Base64.getEncoder().encodeToString(signature)
        println(sig)
        println(pub)
      //  val priv= Base64.getEncoder().encodeToString(privateKey.encoded)
      //  Log.d("res", "privateKey: $priv")

        val obj = JSONObject()
        obj.put("publicKey", pub)
        obj.put("signature", sig)
        obj.put("message", plaintext)
        obj.put("algorithm", ALGO)

        return obj
    }
    @SuppressLint("NewApi")
    @Throws(
        NoSuchAlgorithmException::class,
        InvalidKeySpecException::class,
        InvalidKeyException::class,
        UnsupportedEncodingException::class,
        SignatureException::class
    )
    private fun receiver(obj: JSONObject): Boolean {

        val ecdsaVerify = Signature.getInstance(obj.getString("algorithm"))
       // val kf = KeyFactory.getInstance("EC")

        val publicKeySpec = X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")))

        val keyFactory = KeyFactory.getInstance("EC")
        val publicKey = keyFactory.generatePublic(publicKeySpec)



        ecdsaVerify.initVerify(publicKey)
        ecdsaVerify.update(obj.getString("message").toByteArray(charset("UTF-8")))

        return ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")))
    }

    @SuppressLint("SetTextI18n")
    private fun createPrivateKey(){
        enterData = enterDataEC.text.toString()
        val privateKey = hexString(stringtoByteArray(enterData!!))
        pKey = privateKey
        if (enterData?.length!! > 0){
            show_Private_key.setText(privateKey)
        }else{
            show_Private_key.setText("Your Private Key")
        }

    }

    companion object {

        private val SPEC = "secp256r1"
        private val ALGO = "SHA256withECDSA"

    }
    private fun getDataFromJson(): JSONObject{
        return sender()
    }

    private fun showPublicKey(){
        val publicKey = getDataFromJson().getString("publicKey")
        pubKey = publicKey
        if (enterDataEC.length() > 0 && pKey?.length!! > 0){
            dis_public_key.setText(publicKey)
        }else{
            dis_public_key.setText("Your Public Key")
        }


    }
    @SuppressLint("SetTextI18n")
    private fun  genSign(){
        // gen sig using private key
        val privKey = enter_private_key.text.toString()
        if (pKey.equals(privKey)){
            val signature = getDataFromJson().getString("signature")
            show_signature.text = signature
            sig = signature
        }else{
            show_signature.text = "Invalid Private Key"
        }

    }
    @SuppressLint("SetTextI18n")
    private fun showVerifySignature(){
        Log.d("res", "pub:$pubKey enter:${enterPubKey.text}")
        if (enterPubKey.text.toString() == pubKey && sig?.length!! > 0){
            showVerify.text = "Signature Verified"
            dispmsg.text = "Message used: ${getDataFromJson().getString("message")}" +
                    "\n Alogrithm Used: ${getDataFromJson().getString("algorithm")}"


        }else{
            showVerify.text = "Invalid Public Key"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.back -> {
                val back = Intent(this@EcKeyGen, MainActivity::class.java)
                startActivity(back)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


