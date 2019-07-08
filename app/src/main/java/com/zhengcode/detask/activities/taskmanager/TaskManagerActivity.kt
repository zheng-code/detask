package com.zhengcode.detask.activities.taskmanager

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import com.zhengcode.detask.activities.tasks.TasksActivity


class TaskManagerActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
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
        setContentView(R.layout.activity_task_manager)

        offer_input = findViewById(R.id.offer_input)
        title_input = findViewById(R.id.title_input)
        description_input = findViewById(R.id.description_input)
        location_x_coordinate = findViewById(R.id.location_x_coordinate)
        location_y_coordinate = findViewById(R.id.location_y_coordinate)
        date_input = findViewById(R.id.date_input)
        submit_button = findViewById(R.id.submit_button)



        submit_button.setOnClickListener {


            val offer = (offer_input.getText().toString().trim()).toDouble()
            val title = title_input.text.toString().trim()
            val description = description_input.text.toString().trim()
            val locationx = location_x_coordinate.text.toString().trim().toDouble()
            val locationy = location_y_coordinate.text.toString().trim().toDouble()
            val date = date_input.text.toString().trim()

            val task = OfferedTask(offer, title, description, locationx, locationy, date)
            val databaseRef = database.getReference("task")
            databaseRef.push().setValue(task)
            val taskId = databaseRef.push().key.toString()


            databaseRef.child(taskId).setValue(task).addOnCompleteListener {
                Toast.makeText(applicationContext, "Task successfully submitted: $title", Toast.LENGTH_LONG).show()

            }


        }


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        // when this Activity is created, check its corresponding menuItem
        val menuItem: MenuItem = navView.menu.getItem(2)
        menuItem.isChecked = true

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}
