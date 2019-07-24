package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.zhengcode.detask.*
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import com.zhengcode.detask.utils.logout
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    private val currentUser = FirebaseAuth.getInstance().currentUser

    /*
    There are multiple buttons in this Activity, therefore to reduce
    unnecessary instantiation of anonymous inner classes of OnClickListener,
    we make this Activity itself implement OnClickListener
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            // TODO: abstract away these showToast messages to a single one
            R.id.btn_current_tasks -> {
                val intent = Intent(this, DashboardCurrentTasksActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.current_tasks)}")
            }
            R.id.btn_chats -> {
                val intent = Intent(this, DashboardChatsActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_edit_profile -> {
                val intent = Intent(this, DashboardEditProfileActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.edit_profile)}")
            }
            R.id.btn_skills -> {
                val intent = Intent(this, DashboardSkillsActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.skills)}")
            }
            R.id.btn_traits -> {
                val intent = Intent(this, DashboardTraitsActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.traits)}")
            }
            R.id.btn_task_history -> {
                val intent = Intent(this, DashboardTaskHistoryActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.task_history)}")
            }
            R.id.btn_settings -> {
                val intent = Intent(this, DashboardSettingsActivity::class.java)
                startActivity(intent)
                showToast("Clicked on ${getString(R.string.settings)}")
            }
            R.id.btn_sign_out -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Are you sure?")
                    /* _, _ means that you are not gonna use the parameters,
                       technically you can use the parameter names but you are gonna
                       get warnings that you are not using the parameter
                    */
                    setPositiveButton("Yes") { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        logout()
                    }
                    setNegativeButton("Cancel") { _, _ ->
                    }
                }.create().show()
            }
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
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

    private fun setClickListeners() {
        val btnCurrentTask: Button = findViewById(R.id.btn_current_tasks)
        val btnChats: Button = findViewById(R.id.btn_chats)
        val btnEditProfile: Button = findViewById(R.id.btn_edit_profile)
        val btnSkills: Button = findViewById(R.id.btn_skills)
        val btnTraits: Button = findViewById(R.id.btn_traits)
        val btnTaskHistory: Button = findViewById(R.id.btn_task_history)
        val btnSettings: Button = findViewById(R.id.btn_settings)
        val btnSignOut: Button = findViewById(R.id.btn_sign_out)

        btnCurrentTask.setOnClickListener(this)
        btnChats.setOnClickListener(this)
        btnEditProfile.setOnClickListener(this)
        btnSkills.setOnClickListener(this)
        btnTraits.setOnClickListener(this)
        btnTaskHistory.setOnClickListener(this)
        btnSettings.setOnClickListener(this)
        btnSignOut.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(0)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        currentUser?.let { user ->
            dashboard_username.text = user.displayName
        }

        setClickListeners()
    }
}
