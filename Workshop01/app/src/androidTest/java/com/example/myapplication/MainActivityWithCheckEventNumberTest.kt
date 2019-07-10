package com.example.myapplication


import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityWithCheckEventNumberTest {

    @get:Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun open_case() {
        mActivityTestRule.launchActivity(Intent())
        // Action
        onView(withId(R.id.inputEditText)).perform(replaceText("2"), closeSoftKeyboard())
        onView(withId(R.id.clickMeButton)).perform(click())

        // Assert
        onView(withText("คู่วะ")).check(matches(isDisplayed()))
    }
}
