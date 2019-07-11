package com.example.workshoptesting.searchresult

import com.example.workshoptesting.api.Food
import com.example.workshoptesting.api.FoodRepository
import com.example.workshoptesting.api.RepositoryCallback
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchResultsTest {

    private lateinit var repository: FoodRepository
    private lateinit var presenter: SearchResultsPresenter
    private lateinit var view: SearchResultsPresenter.View

    @Before
    fun setup() {
        repository = mock()
        view = mock()
        presenter = SearchResultsPresenter(repository)
        presenter.attachView(view)
    }

    @Test
    fun search_callsShowLoading() {
        // Action
        presenter.search("eggs")

        // Assert
        verify(view).showLoading()
    }

    @Test
    fun search_calls_getFoods() {
        // Action
        presenter.search("eggs")

        // Assert
        verify(repository).getFoods(eq("eggs"), any())
    }

    @Test
    fun search_withRepositoryHavingRecipes_callsShowRecipes() {
        val food = Food("id", "title", "imageUrl", "sourceUrl", false)
        val foods = listOf(food)

        doAnswer {
            val callback: RepositoryCallback<List<Food>> = it.getArgument(1)
            callback.onSuccess(foods)
        }.whenever(repository).getFoods(eq("eggs"), any())

        // Action
        presenter.search("eggs")

        // Assert
        verify(view).showRecipes(eq(foods))
    }

    @Test
    fun addFavorite_shouldUpdateRecipeStatus() {
        val recipe = Food("id", "title", "imageUrl", "sourceUrl", false)

        // Action
        presenter.addFavorite(recipe)

        // Assert
        assertTrue(recipe.isFavorited)
    }
}