package com.zhengcode.detask.activities.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.TaskStatus
import kotlinx.android.synthetic.main.task_view_page.*

class ViewTaskActivity : AppCompatActivity() {

    lateinit var accept_button: Button
    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_view_page)

        //Take all the data in the selected task and put in the task_view_page
        val offer:String = intent.getStringExtra("offer")
        task_view_offer.text = "$" + offer
        val title:String = intent.getStringExtra("title")
        task_view_title.text = title
        val description:String = intent.getStringExtra("description")
        task_view_description.text = description
        val date:String = intent.getStringExtra("date")
        task_view_date.text = date
        val username:String = intent.getStringExtra("username")
        task_view_username.text = username
        val taskid:String = intent.getStringExtra("taskID")

        accept_button = findViewById(R.id.accept_task_button)

        accept_button.setOnClickListener {
            val databaseRef = database.getReference("task")
            databaseRef.child(taskid).child("taskStatus").setValue(TaskStatus.ACCEPTED)

            Toast.makeText(applicationContext, "Task accepted: $title", Toast.LENGTH_LONG).show()

        }



    }

}