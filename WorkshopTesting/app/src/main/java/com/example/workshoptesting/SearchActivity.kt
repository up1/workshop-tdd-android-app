package com.example.workshoptesting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.workshoptesting.searchresult.searchResultsIntent
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchButton.setOnClickListener {
            val query = ingredients.text.toString()
            startActivity(searchResultsIntent(query))
        }
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
