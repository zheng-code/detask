package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Supplier
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.adapters.dashboard.DashboardTaskHistoryAdapter
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.TaskStatus
import kotlinx.android.synthetic.main.activity_dashboard_task_history.*

class DashboardCurrentTasksActivity: AppCompatActivity() {

    lateinit var taskList: MutableList<OfferedTask>
    lateinit var ref : DatabaseReference

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
        layoutManager.orientation = RecyclerView.VERTICAL
        dashboard_task_history_recycler_view.layoutManager = layoutManager

        val adapter = DashboardTaskHistoryAdapter(this, taskList)
        dashboard_task_history_recycler_view.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_current_tasks)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(0)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        taskList = mutableListOf()
        val ref = Helpers.getCurrentUserUid()?.let {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("taskHistory")
        }


        ref?.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    taskList.clear()
                    for (h in p0.children) {
                        val tasknow = h.getValue(OfferedTask::class.java)
                        // ACCEPTED is when freelancer takes it
                        // WAITING is for the requestor to see
                        // Will probably need visual differentiation right there
                        if (tasknow?.taskStatus === TaskStatus.ACCEPTED ||
                            tasknow?.taskStatus === TaskStatus.WAITING) {
                            taskList.add(tasknow)
                        }
                    }

                    setupRecycleView()
                }
            }
        })
    }
}
