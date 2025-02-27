package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity2> =
        ActivityScenarioRule(MainActivity2::class.java)

    private lateinit var validEmail: String
    private lateinit var validPassword: String

    @Before
    fun setUp() {
        // Initialize valid email and password for testing
        validEmail = "ali12@gmail.com"
        validPassword = "123456" // Valid password containing only numbers
    }

    @Test
    fun testLoginSuccess() {
        // Type valid email and password
        onView(withId(R.id.etEmail)).perform(typeText(validEmail), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText(validPassword), closeSoftKeyboard())

        // Click on the login button
        onView(withId(R.id.btnLogin)).perform(click())

        // Check if successful login toast message appears
        onView(withText("Sign in successful"))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        // Check if "My Profile" text view is displayed

    }
}
