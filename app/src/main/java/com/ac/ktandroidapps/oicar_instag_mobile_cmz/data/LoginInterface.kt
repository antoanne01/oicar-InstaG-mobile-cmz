package com.ac.ktandroidapps.oicar_instag_mobile_cmz.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface LoginInterface {
    fun signInWithEmailAndPassword(email: String, pass: String): Task<AuthResult>
}