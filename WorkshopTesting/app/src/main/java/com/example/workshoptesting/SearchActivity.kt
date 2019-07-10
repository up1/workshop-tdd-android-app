package com.example.workshoptesting

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.inputmethod.InputMethodManager
import com.example.workshoptesting.searchresult.searchResultsIntent
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchButton.setOnClickListener {
            val query = ingredients.text.toString()
            if (query.trim().isBlank()) {
                showQueryRequiredMessage()
            } else {
                startActivity(searchResultsIntent(query))
            }
        }
    }

    private fun showQueryRequiredMessage() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Snackbar.make(
            searchButton, getString(R.string.search_query_required), Snackbar
                .LENGTH_LONG
        ).show()
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
