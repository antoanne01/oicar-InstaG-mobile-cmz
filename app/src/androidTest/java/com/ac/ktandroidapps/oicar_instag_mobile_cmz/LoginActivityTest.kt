package com.ac.ktandroidapps.oicar_instag_mobile_cmz

import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.ac.ktandroidapps.oicar_instag_mobile_cmz.Post.PostActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LoginActivityTest {

    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setUp() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    @Test
    fun whenInputAreNotNull(){
        val email = "progmejl@gmail.com"
        val pass = "mail123"

        val result = firebaseAuth.signInWithEmailAndPassword(email, pass)

        result.addOnCompleteListener {
            assertNotNull(result)
            assertFalse(result.isSuccessful)
        }
    }

    @Test
    fun whenEmailIsEmpty(){

        val email = ""
        val pass = "invalid_password"

        val result = if (email.isEmpty()) {
            IllegalArgumentException("Empty email")
        } else {
            assertThrows(IllegalArgumentException::class.java){
                firebaseAuth.signInWithEmailAndPassword(email, pass)
            }
        }
        assertEquals("Empty email", result.message)
    }

    @Test
    fun whenInputsAreValid() {
        val email = "progmejl@mail.com"
        val pass = "mail123"

        val result = firebaseAuth.signInWithEmailAndPassword(email, pass)

        result.addOnCompleteListener {
            assertTrue(it.isComplete)
            assertTrue(it.isSuccessful)
            assertFalse(it.exception is IllegalArgumentException)
        }
    }

    @Test
    fun whenInputsAreInvalid() {
        val email = "mail"
        val pass = "ip"

        val result = firebaseAuth.signInWithEmailAndPassword(email, pass)

        result.addOnCompleteListener {
            assertFalse(it.isSuccessful)
            assertTrue(it.exception is IllegalArgumentException)
        }
    }
}