package com.example.unit6cse226testing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unit5cse226currentlocationtopic.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleEspressoTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity2::class.java)

    @Test
    fun testNameIsDisplayedCorrectly() {

        // 1. Type text into EditText
        onView(withId(R.id.editTextName))
            .perform(typeText("Shruti"), closeSoftKeyboard())

        // 2. Click the button
        onView(withId(R.id.btnShow))
            .perform(click())

        // 3. Check the result text
        onView(withId(R.id.textResult))
            .check(matches(withText("Hello, Shruti")))
    }
}
