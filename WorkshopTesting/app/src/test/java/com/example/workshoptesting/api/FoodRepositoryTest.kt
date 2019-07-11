package com.example.workshoptesting.api

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class FoodRepositoryTest {
    private lateinit var spyRepository: FoodRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
        spyRepository = spy(FoodRepositoryReal(sharedPreferences))
    }

    @Test
    fun addFavorite_withEmptyFood_savesJson() {
        // Arrange
        doReturn(emptyList<Food>()).whenever(spyRepository).getFavoriteFoods()
        val food = Food("id", "title", "imageUrl", "sourceUrl", false)

        // Action
        spyRepository.addFavorite(food)

        // Assert
        val jsonString: String = Gson().toJson(listOf(food))
        verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
        verify(sharedPreferencesEditor).apply()
    }
}