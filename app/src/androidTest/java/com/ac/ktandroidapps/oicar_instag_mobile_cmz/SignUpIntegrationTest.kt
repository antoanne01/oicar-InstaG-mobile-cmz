package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpIntegrationTest {

    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setUp() {
        Intents.init()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }

    @After
    fun tearDown() {
        Intents.release()
        firebaseAuth.signOut()
    }

    @Test
    fun testLoginRedirect() {
        val scenario = ActivityScenario.launch(SignUpActivity::class.java)

        onView(withId(R.id.tvLoginRedirect)).perform(click())
        intended(hasComponent(LoginActivity::class.java.name))

        scenario.close()
    }
}