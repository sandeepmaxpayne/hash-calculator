package com.sandeep.hashcalculator

import java.nio.charset.StandardCharsets
import java.security.MessageDigest


interface EcHasher {
    fun hash(data: ByteArray): ByteArray
}

object EcSha256: EcHasher{
    override fun hash(data: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(data)
    }
}

