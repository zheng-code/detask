package com.zhengcode.detask

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

class TaskManagerActivity : AppCompatActivity() {

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
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_manager)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(2)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
