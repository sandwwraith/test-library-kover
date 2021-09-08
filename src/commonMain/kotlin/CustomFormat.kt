package org.jetbrains.kotlinx.test_library

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
public class CustomFormat {
    class ListEncoder : AbstractEncoder() {
        val list = mutableListOf<String>()

        override val serializersModule: SerializersModule = EmptySerializersModule

        override fun encodeValue(value: Any) {
            list.add(value.toString())
        }
    }

    fun <T> encodeToList(serializer: SerializationStrategy<T>, value: T): CustomResult {
        val encoder = ListEncoder()
        return try {
            encoder.encodeSerializableValue(serializer, value)
            CustomResult.OK(encoder.list)
        } catch (e: Exception) {
            CustomResult.BruhMoment(e.message.orEmpty())
        }
    }
}

@Serializable
sealed class CustomResult {
    @Serializable
    data class OK(val result: List<String>): CustomResult()
    @Serializable
    data class BruhMoment(val error: String): CustomResult()
}

