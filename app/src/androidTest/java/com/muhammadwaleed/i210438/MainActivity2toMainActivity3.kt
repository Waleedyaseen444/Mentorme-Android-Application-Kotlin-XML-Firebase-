package com.muhammadwaleed.i210438

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivity2ToMainActivity3Test {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity2::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun navigateToMainActivity3() {
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(MainActivity3::class.java.name))
    }
}
