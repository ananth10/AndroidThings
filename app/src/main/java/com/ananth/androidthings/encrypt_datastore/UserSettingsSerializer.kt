package com.ananth.androidthings.encrypt_datastore

import androidx.datastore.core.Serializer
import com.ananth.androidthings.crypto.CryptoManager
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserSettingsSerializer(
    private val cryptoManager: CryptoManager,
) : Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        val decryptBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = decryptBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(userSettings: UserSettings, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = UserSettings.serializer(),
                value = userSettings
            ).encodeToByteArray(),
            outputStream = output

        )
    }

}