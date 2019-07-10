package com.example.workshoptesting.api

interface FoodRepository {
    fun addFavorite(food: Food)
    fun removeFavorite(food: Food)
    fun getFavoriteFoods(): List<Food>
    fun getFoods(query: String, callback: RepositoryCallback<List<Food>>)
}

interface RepositoryCallback<in T> {
    fun onSuccess(t: T?)
    fun onError()
}