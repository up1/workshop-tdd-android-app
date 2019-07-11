package com.example.workshoptesting

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import com.example.workshoptesting.api.OkHttp
import com.jakewharton.espresso.OkHttp3IdlingResource
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class OkHttpIdlingResourceRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val idlingResource = OkHttp3IdlingResource.create(
                    "okhttp", OkHttp.instance
                )
                IdlingRegistry.getInstance().register(idlingResource)

                base.evaluate()

                IdlingRegistry.getInstance().unregister(idlingResource)
            }
        }
    }
}
