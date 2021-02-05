package com.yieldex.interview.extensions

import org.springframework.util.Base64Utils
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class StringExtensions {

    companion object {
        fun String.encrypt(): String {
            val secretKeySpec = SecretKeySpec(this.toByteArray(), "AES")
            val iv = ByteArray(16)
            val charArray = this.toCharArray()
            for (i in charArray.indices){
                iv[i] = charArray[i].toByte()
            }
            val ivParameterSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)

            val encryptedValue = cipher.doFinal(this.toByteArray())
            return Base64Utils.encodeToString(encryptedValue)
        }

        fun String.decrypt(): String {
            val secretKeySpec = SecretKeySpec(this.toByteArray(), "AES")
            val iv = ByteArray(16)
            val charArray = this.toCharArray()
            for (i in charArray.indices){
                iv[i] = charArray[i].toByte()
            }
            val ivParameterSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

            val decryptedByteValue = cipher.doFinal(Base64Utils.decode(this.toByteArray()))
            return String(decryptedByteValue)
        }
    }

}