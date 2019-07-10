package com.example.workshoptesting.api

data class FoodsContainer(val foods: List<Food>)

data class Food(
    val recipeId: String,
    val title: String,
    val imageUrl: String,
    val sourceUrl: String,
    var isFavorited: Boolean
)