package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class CheckEvenNumberUseCaseTest {

    @Test fun `2 ต้องเป็นเลขคู่`() {
        val c = CheckEvenNumberUseCase()
        assertTrue(c.execute(2))
    }

    @Test fun `3 ต้องไม่เป็นเลขคู่`() {
        val c = CheckEvenNumberUseCase()
        assertFalse(c.execute(3))
    }

}