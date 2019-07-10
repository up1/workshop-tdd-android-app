package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickMe(view: View) {
        var intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("XX", "คู่วะ")
        startActivity(intent)
    }
}
