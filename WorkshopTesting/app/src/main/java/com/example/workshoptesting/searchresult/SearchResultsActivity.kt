package com.example.workshoptesting.searchresult

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.workshoptesting.R
import com.example.workshoptesting.api.Food
import com.example.workshoptesting.api.FoodRepositoryReal
import kotlinx.android.synthetic.main.activity_search_results.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

fun Context.searchResultsIntent(query: String): Intent {
    return Intent(this, SearchResultsActivity::class.java).apply {
        putExtra(EXTRA_QUERY, query)
    }
}

private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : AppCompatActivity(), SearchResultsPresenter.View {

    private val presenter: SearchResultsPresenter by lazy { SearchResultsPresenter(FoodRepositoryReal.getRepository(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val query = intent.getStringExtra(EXTRA_QUERY)
        supportActionBar?.subtitle = query

        presenter.attachView(this)

        presenter.search(query)
        retry.setOnClickListener { presenter.search(query) }

    }

    override fun showEmptyRecipes() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.VISIBLE
    }

    override fun showRecipes(recipes: List<Food>) {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        list.visibility = View.VISIBLE
        noresultsContainer.visibility = View.GONE

        setupRecipeList(recipes)
    }

    override fun showLoading() {
        loadingContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        list.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
    }

    override fun showError() {
        loadingContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        list.visibility = View.GONE
        noresultsContainer.visibility = View.GONE
    }

    override fun refreshFavoriteStatus(position: Int) {
        list.adapter?.notifyItemChanged(position)
    }

    private fun setupRecipeList(recipes: List<Food>) {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onClickItem(item: Food) {
//                startActivity(recipeIntent(recipe.sourceUrl))
            }

            override fun onAddFavorite(item: Food) {
                presenter.addFavorite(item)
            }

            override fun onRemoveFavorite(item: Food) {
                presenter.removeFavorite(item)
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
