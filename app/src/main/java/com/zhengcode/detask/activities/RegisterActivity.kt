package com.zhengcode.detask.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import com.zhengcode.detask.models.User
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.login
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        button_register.setOnClickListener {
            val email = edit_text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()
            val confirm_password = edit_text_confirm_password.text.toString().trim()
            val username = edit_text_username.text.toString().trim()

            if (username.isEmpty()) {
                edit_text_username.error = "Username required"
                edit_text_email.requestFocus()
                // Exit this setOnClickListener
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                edit_text_email.error = "Email Required"
                edit_text_email.requestFocus()
                // Exit this setOnClickListener
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edit_text_email.error = "Valid Email Required"
                edit_text_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                edit_text_password.error = "6 char password required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }

            if (password != confirm_password) {
                edit_text_confirm_password.error = "Passwords don't match"
                edit_text_confirm_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password, username)
        }
        text_view_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String, username: String) {
        progressbar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressbar.visibility = View.GONE
                if (task.isSuccessful) {
                    val uid = Helpers.getCurrentUserUid()
                    // Registration success
                    // util
                    val user = User(email, username, uid!!)
                    val updates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    // should have an onCompleteListener here somewhere
                    // Currently doesn't really show the displayName upon signing in though...
                    // Idk why also .-.
                    FirebaseAuth.getInstance().currentUser?.updateProfile(updates)
                        ?.addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Log.d("RegisterActivity", "Updated profile")
                            }

                        }

                    /* TODO: NEED SLIGHT REFACTORING HERE TO INCLUDE UID,
                        OTHERWISE, GOING FROM DashboardChatActivity to ChatLogActivity
                        WILL FAIL!!
                     */
                    FirebaseAuth.getInstance().currentUser?.uid?.let {
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(it)
                            .setValue(user)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    showToast("Registration Success")
                                } else {
                                    showToast("Registration no success")
                                }
                            }
                    }



                    login()
                } else {
                    task.exception?.message?.let {
                        showToast(it)
                    }
                }

            }
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}