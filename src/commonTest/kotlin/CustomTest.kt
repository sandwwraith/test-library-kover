package org.jetbrains.kotlinx.test_library

import kotlinx.serialization.Serializable
import kotlin.test.*

class TestCustomFormat {

    @Serializable
    data class X(val foo: String?)

    @Test
    fun testOk() {
        val result = CustomFormat().encodeToList(X.serializer(), X("foo"))
        assertEquals(CustomResult.OK(listOf("foo")), result)
    }

    @Test
    fun testFail() {
        val result = CustomFormat().encodeToList(X.serializer(), X(null))
        assertTrue(result is CustomResult.BruhMoment)
    }
}
