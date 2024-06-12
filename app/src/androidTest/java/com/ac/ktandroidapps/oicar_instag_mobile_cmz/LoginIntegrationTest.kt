package com.ac.ktandroidapps.oicar_instag_mobile_cmz


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
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
class LoginIntegrationTest {

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
    fun testLoginSuccess() {

        val email = "progmejl@gmail.com"
        val password = "123456"

        val scenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.tvEmail)).perform(replaceText(email))
        onView(withId(R.id.tvPassword)).perform(replaceText(password))
        onView(withId(R.id.btnLogin)).perform(click())

        Thread.sleep(5000)

        intended(hasComponent(HomeActivity::class.java.name))

        scenario.close()
    }

    @Test
    fun testRegisterRedirect() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.btnRegisterRedirect)).perform(click())
        intended(hasComponent(SignUpActivity::class.java.name))

        scenario.close()
    }
}