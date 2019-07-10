package com.example.workshoptesting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.workshoptesting.api.Food
import com.example.workshoptesting.api.FoodRepository
import com.example.workshoptesting.api.FoodRepositoryReal
import com.example.workshoptesting.searchresult.RecipeAdapter
import com.example.workshoptesting.searchresult.recipeDetailIntent
import kotlinx.android.synthetic.main.activity_search_results.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

class FavoritesActivity : AppCompatActivity() {

    private val repository: FoodRepository by lazy { FoodRepositoryReal.getRepository(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
        noresultsTitle.text = getString(R.string.nofavorites)

        val favoriteRecipes = repository.getFavoriteFoods()
        if (favoriteRecipes.isEmpty()) {
            showEmptyRecipes()
        } else {
            showRecipes(favoriteRecipes)
        }
    }

    private fun showEmptyRecipes() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.VISIBLE
    }

    private fun showRecipes(recipes: List<Food>) {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.GONE

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onClickItem(item: Food) {
                startActivity(recipeDetailIntent(item.sourceUrl.replace("http", "https")))
            }

            override fun onAddFavorite(item: Food) {
                item.isFavorited = true
                repository.addFavorite(item)
                list.adapter?.notifyItemChanged(recipes.indexOf(item))
            }

            override fun onRemoveFavorite(item: Food) {
                repository.removeFavorite(item)
                with((list.adapter as RecipeAdapter)) {
                    removeItem(item)
                    notifyItemRemoved(recipes.indexOf(item))
                }
                if ((list.adapter as RecipeAdapter).itemCount == 0) {
                    showEmptyRecipes()
                }
            }
        })
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
