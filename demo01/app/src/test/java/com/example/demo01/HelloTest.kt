package com.example.demo01

import org.junit.Assert.*
import org.junit.Test

class HelloTest {

    @Test
    fun `สวัสดี unit test`() {
        val h = Hello()
        val result = h.sayHi("somkiat")
        assertEquals("Hello, somkiat", result)
    }

}
