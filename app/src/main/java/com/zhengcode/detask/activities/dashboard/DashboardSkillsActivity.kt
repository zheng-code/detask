package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.zhengcode.detask.adapters.dashboard.DashboardSkillsAdapter
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Supplier
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import kotlinx.android.synthetic.main.activity_dashboard_skills.*

class DashboardSkillsActivity: AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
            R.id.navigation_tasks -> {
            }
            R.id.navigation_task_manager -> {
                val intent = Intent(this, TaskManagerActivity::class.java)
                startActivity(intent)
            }
        }
        false
    }
    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dashboard_skills_recycler_view.layoutManager = layoutManager

        val adapter = DashboardSkillsAdapter(this, Supplier.skillStubs)
        dashboard_skills_recycler_view.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_skills)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(0)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setupRecycleView()
    }
}
