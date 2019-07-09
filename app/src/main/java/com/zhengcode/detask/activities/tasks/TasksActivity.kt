package com.zhengcode.detask.activities.tasks

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import com.google.firebase.database.*
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity() {

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
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        dashboard_task_history_recycler_view.layoutManager = layoutManager

        val adapter = TasksAdapter(this, taskList)
        dashboard_task_history_recycler_view.adapter = adapter
        Log.i("TasksActivity", "Number of items: ${taskList.size}")

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(1)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)





        // Section that creates list of tasks taskList and displays them ///////////
        taskList = mutableListOf()
        val ref = FirebaseDatabase.getInstance().getReference("task")


        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    taskList.clear()
                    for (h in p0.children) {
                        val tasknow = h.getValue(OfferedTask::class.java)
                        taskList.add(tasknow!!)
                    }

                    setupRecycleView()
                }
            }
        })

    }
}
