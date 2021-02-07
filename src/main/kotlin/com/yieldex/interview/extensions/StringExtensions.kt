package com.yieldex.interview.extensions

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec










class StringExtensions {

    companion object {
        fun String.encrypt(): String {
            val keySpec = DESKeySpec("Your secret Key phrase".toByteArray(charset("UTF8")))
            val keyFactory = SecretKeyFactory.getInstance("DES")
            val key = keyFactory.generateSecret(keySpec)
            val base64encoder = BASE64Encoder()

            val cleartext: ByteArray = this.toByteArray()

            val cipher = Cipher.getInstance("DES") // cipher is not thread safe

            cipher.init(Cipher.ENCRYPT_MODE, key)
            return base64encoder.encode(cipher.doFinal(cleartext))
        }

        fun String.decrypt(): String {
            val keySpec = DESKeySpec("Your secret Key phrase".toByteArray(charset("UTF8")))
            val keyFactory = SecretKeyFactory.getInstance("DES")
            val key = keyFactory.generateSecret(keySpec)
            val base64decoder = BASE64Decoder()

            val encrypedPwdBytes: ByteArray = base64decoder.decodeBuffer(this)

            val cipher = Cipher.getInstance("DES") // cipher is not thread safe

            cipher.init(Cipher.DECRYPT_MODE, key)
            return cipher.doFinal(encrypedPwdBytes).toString()
        }
    }

}