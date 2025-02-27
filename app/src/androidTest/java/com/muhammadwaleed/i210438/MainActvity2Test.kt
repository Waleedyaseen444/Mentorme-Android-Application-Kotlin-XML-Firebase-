package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivity2Test {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity2> =
        ActivityScenarioRule(MainActivity2::class.java)

    @Test
    fun testNavigateToSignup() {
        // Click the Sign Up button
        onView(withId(R.id.tvSignUpPrompt)).perform(click())

        // Check if we have navigated to the Signup activity by checking a view in that activity
        onView(withId(R.id.tvAppName)).check(matches(isDisplayed()))
    }
}
