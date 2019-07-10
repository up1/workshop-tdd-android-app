package com.example.workshoptesting.api

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val FAVORITES_KEY = "FAVORITES_KEY"

class FoodRepositoryReal(private val sharedPreferences: SharedPreferences) : FoodRepository {

    private val gson = Gson()

    companion object {
        fun getRepository(context: Context): FoodRepositoryReal {
            return FoodRepositoryReal(context.getSharedPreferences("Favorites", Context.MODE_PRIVATE))
        }
    }

    override fun addFavorite(food: Food) {
        val favorites = getFavoriteFoods() + food
        saveFavorites(favorites)
    }

    override fun removeFavorite(food: Food) {
        val favorites = getFavoriteFoods() - food
        saveFavorites(favorites)
    }

    private fun saveFavorites(favorites: List<Food>) {
        val editor = sharedPreferences.edit()
        editor.putString(FAVORITES_KEY, gson.toJson(favorites))
        editor.apply()
    }

    private inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun getFavoriteFoods(): List<Food> {
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, null)
        if (favoritesString != null) {
            return gson.fromJson(favoritesString)
        }

        return emptyList()
    }

    override fun getFoods(query: String, callback: RepositoryCallback<List<Food>>) {
        val call = FoodAPI.create().search(query)
        call.enqueue(object : Callback<FoodsContainer> {
            override fun onResponse(call: Call<FoodsContainer>?, response: Response<FoodsContainer>?) {
                if (response != null && response.isSuccessful) {
                    val foodsContainer = response.body()
                    markFavorites(foodsContainer)
                    callback.onSuccess(foodsContainer?.recipes)
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<FoodsContainer>?, t: Throwable?) {
                callback.onError()
            }
        })
    }

    private fun markFavorites(recipesContainer: FoodsContainer?) {
        if (recipesContainer != null) {
            val favoriteRecipes = getFavoriteFoods()
            if (favoriteRecipes.isNotEmpty()) {
                for (item in recipesContainer.recipes) item.isFavorited = favoriteRecipes.map { it.recipeId }.contains(item.recipeId)
            }
        }
    }
}