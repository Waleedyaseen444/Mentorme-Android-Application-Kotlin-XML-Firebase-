package com.muhammadwaleed.i210438

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivity3Test {

    private lateinit var activityScenario: ActivityScenario<MainActivity3>

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity3::class.java)

    @Before
    fun setUp() {
        activityScenario = activityRule.scenario
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun testNotificationButton() {
        // Click on the notification button
        onView(withId(R.id.btnNotifications)).perform(click())

        // Check if the correct activity (Notifications) is launched
        val expectedIntent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, Notifications::class.java)
        launch<Notifications>(expectedIntent)
    }
}
