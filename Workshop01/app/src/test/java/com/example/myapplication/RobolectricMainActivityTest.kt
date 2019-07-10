package com.example.myapplication

import android.widget.TextView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RobolectricMainActivityTest {

    @Test
    fun `check data in first page`() {
        // Arrange
        val activity = buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()

        // Act

        // Assert
        val show = activity.findViewById(R.id.messageTextView) as TextView
        assertEquals("Hello World!]", show.text);
    }

}