package com.example.workshoptesting

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

fun Context.searchResultsIntent(query: String): Intent {
    return Intent(this, SearchResultsActivity::class.java).apply {
        putExtra(EXTRA_QUERY, query)
    }
}

private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val query = intent.getStringExtra(EXTRA_QUERY)
        supportActionBar?.subtitle = query
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
