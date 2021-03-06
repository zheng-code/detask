package com.zhengcode.detask.activities.taskmanager

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.TaskStatus


class TaskManagerActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var offer_input: EditText
    lateinit var title_input: EditText
    lateinit var description_input: EditText
    lateinit var location_x_coordinate: EditText
    lateinit var location_y_coordinate: EditText
    lateinit var date_input: EditText
    lateinit var submit_button: Button


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
        setContentView(R.layout.activity_task_manager_2)

        offer_input = findViewById(R.id.offer_input)
        title_input = findViewById(R.id.title_input)
        description_input = findViewById(R.id.description_input)
        location_x_coordinate = findViewById(R.id.location_x_coordinate)
        location_y_coordinate = findViewById(R.id.location_y_coordinate)
        date_input = findViewById(R.id.date_input)
        submit_button = findViewById(R.id.submit_button)



        submit_button.setOnClickListener {


            val offer = (offer_input.text.toString().trim()).toDouble()
            val title = title_input.text.toString().trim()
            val description = description_input.text.toString().trim()
            val locationx = location_x_coordinate.text.toString().trim().toDouble()
            val locationy = location_y_coordinate.text.toString().trim().toDouble()
            val date = date_input.text.toString().trim()
            val username = currentUser?.displayName

            val databaseRef = database.getReference("task")
            val taskId = databaseRef.push().key.toString()
            val requestorId = Helpers.getCurrentUserUid()

            val requestorDbRef = requestorId?.let { it ->
                database
                    .getReference("users")
                    .child(it)
                    .child("taskHistory")
            }

            val task = OfferedTask(offer, title, description, locationx, locationy,
                date, username, TaskStatus.WAITING, taskId, requestorId)
            databaseRef.child(taskId).setValue(task)
            requestorDbRef?.child(taskId)?.setValue(task)



            databaseRef.child(taskId).setValue(task).addOnCompleteListener {
                Toast.makeText(applicationContext, "Task successfully submitted: $title", Toast.LENGTH_LONG).show()
                offer_input.text.clear()
                title_input.text.clear()
                description_input.text.clear()
                location_x_coordinate.text.clear()
                location_y_coordinate.text.clear()
                date_input.text.clear()

            }



        }


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(2)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}
