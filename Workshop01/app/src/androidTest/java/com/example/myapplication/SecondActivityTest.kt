package com.example.myapplication

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SecondActivityTest {
    @get:Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SecondActivity::class.java, false, false)

    @Test
    fun check_result() {
        val intent = Intent()
        intent.putExtra("XX", "คู่วะ")
        mActivityTestRule.launchActivity(intent)
        // Assert
        onView(withText("คู่วะ"))
            .check(matches(isDisplayed()))
    }
}