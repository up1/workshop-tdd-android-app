package com.example.workshoptesting.searchresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import com.example.workshoptesting.R
import kotlinx.android.synthetic.main.activity_recipe_detail.*

fun Context.recipeDetailIntent(url: String): Intent {
    return Intent(this, RecipeDetailActivity::class.java).apply {
        putExtra(EXTRA_URL, url)
    }
}

private const val EXTRA_URL = "EXTRA_URL"

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val url = intent.getStringExtra(EXTRA_URL)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.isHorizontalScrollBarEnabled = false
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {}
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
