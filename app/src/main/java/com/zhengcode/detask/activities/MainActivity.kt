package com.zhengcode.detask.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import com.zhengcode.detask.activities.dashboard.DashboardActivity

class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /*
        // when this Activity is created, uncheck the menuItem
        // CURRENTLY DOESN'T WORK
        val menuItem: MenuItem = navView.menu.getItem(0)
        menuItem.isChecked = false
        */

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
