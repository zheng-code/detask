package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_dashboard_edit_profile.*

class DashboardEditProfileActivity : AppCompatActivity() {
    private val currentUser = FirebaseAuth.getInstance().currentUser

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_edit_profile_2)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        currentUser?.let { user ->
            edit_text_name.setText(user.displayName)

            text_email.text = user.email

//            text_phone.text = if (user.phoneNumber.isNullOrEmpty()) "Add Number" else user.phoneNumber

            if (user.isEmailVerified) {
                text_not_verified.visibility = View.INVISIBLE
            } else {
                text_not_verified.visibility = View.VISIBLE
            }
        }

        button_save.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            val currentUserUid = Helpers.getCurrentUserUid()

            if (name.isEmpty()) {
                edit_text_name.error = "Name required"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }

            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            progressbar.visibility = View.VISIBLE

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener {task ->
                    progressbar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        val ref = FirebaseDatabase
                            .getInstance()
                            .getReference("/users/$currentUserUid/username")
                        ref.setValue(name)
                        showToast("Profile Updated")
                    } else {
                        showToast(task.exception?.message!!)
                    }

                }
        }
    }

}
