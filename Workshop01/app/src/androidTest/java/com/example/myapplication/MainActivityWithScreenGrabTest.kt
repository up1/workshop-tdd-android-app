package com.example.myapplication


import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
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
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityWithScreenGrabTest {

//    @get:ClassRule
//    @JvmField
//    val localeTestRule = LocaleTestRule()

    @get:Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun mainActivityTest() {
//        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())

        val appContext = InstrumentationRegistry.getTargetContext()
        //TODO

        mActivityTestRule.launchActivity(Intent());
//        Screengrab.screenshot("step_01")

        // Assert
        onView(withId(R.id.messageTextView))
            .check(matches(withText("Hello World!")))
            .check(matches(isDisplayed()))

//        Screengrab.screenshot("step_02")
//        Screengrab.screenshot("step_03")
    }
}
