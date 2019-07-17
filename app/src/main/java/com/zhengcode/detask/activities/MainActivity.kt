package com.zhengcode.detask.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import com.zhengcode.detask.utils.login
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    // Not needed anymore because it is now a login page
    /*
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_tasks -> {
                val intent = Intent(this, TasksActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_task_manager -> {
                val intent = Intent(this, TaskManagerActivity::class.java)
                startActivity(intent)
            }
        }
        false
    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)

        mAuth = FirebaseAuth.getInstance()
        /*
        // when this Activity is created, uncheck the menuItem
        // CURRENTLY DOESN'T WORK
        val menuItem: MenuItem = navView.menu.getItem(0)
        menuItem.isChecked = false
        */

        button_sign_in.setOnClickListener {
            val email = edit_text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()

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

            loginUser(email, password)
        }
        text_view_register.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

        //navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun loginUser(email: String, password: String) {
        progressbar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressbar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in Success
                    // util
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
        // If already logged in, just start the dashboard activity
        mAuth.currentUser?.let {
            login()
        }
    }
}
