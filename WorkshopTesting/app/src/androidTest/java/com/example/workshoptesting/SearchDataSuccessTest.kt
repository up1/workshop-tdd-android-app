package com.example.workshoptesting

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.example.workshoptesting.searchresult.RecipeAdapter
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class SearchDataSuccessTest {

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var okHttpIdlingResourceRule = OkHttpIdlingResourceRule()

    @Test
    fun success_flow_of_search_data_from_api_and_show_detail() {
        // Action
        onView(withId(R.id.searchButton)).perform(click())

        onView(withId(R.id.ingredients)).perform(replaceText("chicken"))
        onView(withId(R.id.searchButton)).perform(click())

        // Assert
        onView(withId(R.id.list)).check(matches(isDisplayed()))
//        onView(withId(R.id.list)).check(matches(hasChildCount(2)))

        onView(withId(R.id.list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecipeAdapter.ViewHolder>(0, click()))

    }

    @Test fun success_flow_of_search_data_from_api_and_save_first_item_to_favorite() {
        // Action
        onView(withId(R.id.searchButton)).perform(click())

        onView(withId(R.id.ingredients)).perform(replaceText("chicken"))
        onView(withId(R.id.searchButton)).perform(click())

        // Assert
        onView(withId(R.id.list)).check(matches(isDisplayed()))

        onView(withId(R.id.list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecipeAdapter.ViewHolder>
                    (0, MyRecyclerViewAction.clickChildViewWithId(R.id.favButton)))

    }

}