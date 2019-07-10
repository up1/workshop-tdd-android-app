package com.example.workshoptesting.searchresult

import com.example.workshoptesting.BasePresenter
import com.example.workshoptesting.api.Food
import com.example.workshoptesting.api.FoodRepository
import com.example.workshoptesting.api.RepositoryCallback

class SearchResultsPresenter(private val repository: FoodRepository) :
    BasePresenter<SearchResultsPresenter.View>() {

    interface View {
        fun showLoading()
        fun showRecipes(recipes: List<Food>)
        fun showEmptyRecipes()
        fun showError()
        fun refreshFavoriteStatus(recipeIndex: Int)
    }

    private var recipes: List<Food>? = null

    fun search(query: String) {
        view?.showLoading()

        repository.getFoods(query, object : RepositoryCallback<List<Food>> {
            override fun onSuccess(recipes: List<Food>?) {
                this@SearchResultsPresenter.recipes = recipes
                if (recipes != null && recipes.isNotEmpty()) {
                    view?.showRecipes(recipes)
                } else {
                    view?.showEmptyRecipes()
                }
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    fun addFavorite(recipe: Food) {
        recipe.isFavorited = true

        repository.addFavorite(recipe)

        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }


    fun removeFavorite(recipe: Food) {
        repository.removeFavorite(recipe)
        recipe.isFavorited = false
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }


}