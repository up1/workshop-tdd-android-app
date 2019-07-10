package com.example.myapplication

class CheckEvenNumberUseCase {
    fun execute(i: Int): Boolean {
        return i%2 == 0
    }
}