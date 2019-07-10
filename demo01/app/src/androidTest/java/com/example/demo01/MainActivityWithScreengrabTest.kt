package com.example.demo01

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import tools.fastlane.screengrab.locale.LocaleTestRule
import org.junit.ClassRule
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy




class MainActivityWithScreengrabTest {

    @get:ClassRule
    @JvmField
    val localeTestRule = LocaleTestRule()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun open_main_page() {
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        Screengrab.screenshot("step_01")
        onView(withText("Hello World!"))
            .check(matches(isDisplayed()))
    }

}