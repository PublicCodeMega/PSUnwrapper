package c.PSUnwrapper.cryptography

import java.io.File
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object Cryptography {
    fun decrypt(fileName: String, keyBase64: String, action: (String) -> Unit ) {
        val encryptedFile: File = File(fileName)
        val decryptedFile = File(fileName.replace(".enc", ".ps1"))
        try {
            val keyBytes = Base64.getDecoder().decode(keyBase64)
            val key: SecretKey = SecretKeySpec(keyBytes, "AES")
            val encryptedDataWithIv = encryptedFile.readBytes()
            val ivBytes = encryptedDataWithIv.copyOfRange(0, 12)
            val encryptedBytes = encryptedDataWithIv.copyOfRange(12, encryptedDataWithIv.size)
            val gcmParameterSpec = GCMParameterSpec(128, ivBytes)
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
//            decryptedFile.writeBytes(decryptedBytes)
            println("code PowerShell: ${decryptedBytes.toString(Charsets.UTF_8)}")
            println("\nSuccessfully decrypted '${encryptedFile.name}' to '${decryptedFile.name}'!")
            val scriptBase64 = Base64.getEncoder().encodeToString(decryptedBytes)
            action(scriptBase64)
        }
        catch (e: IllegalArgumentException){ println("Error 01 : ${e.message}") }
        catch (e: Exception){ println("Error decrypting file 02: ${e.message}") }
    }
}