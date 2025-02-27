package com.muhammadwaleed.i210438

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class SignupActivityEspressoTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<Signup> = ActivityScenarioRule(Signup::class.java)

    @Test
    fun testSignupSuccess() {
        // Fill in the EditText fields with valid data
        Espresso.onView(ViewMatchers.withId(R.id.id_name))
            .perform(ViewActions.typeText("John Doe"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.idEmailAddress))
            .perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editTextPhone))
            .perform(ViewActions.typeText("1234567890"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.Country))
            .perform(ViewActions.typeText("United States"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editTextCity))
            .perform(ViewActions.typeText("New York"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.editTextNumberPassword))
            .perform(ViewActions.typeText("password123"), ViewActions.closeSoftKeyboard())

        // Click the signup button
        Espresso.onView(ViewMatchers.withId(R.id.signup)).perform(ViewActions.click())

        // Wait for the login button to be displayed after signup
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
