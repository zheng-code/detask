package com.zhengcode.detask.activities.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.TaskStatus
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.task_view_page.*

class ViewTaskActivity : AppCompatActivity() {

    lateinit var accept_button: Button
    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_view_page)

        //Take all the data in the selected task and put in the task_view_page
        val offer: Double = intent.getDoubleExtra("offer", 0.0)
        task_view_offer.text = "$${offer}"

        val title: String = intent.getStringExtra("title")
        task_view_title.text = title

        val description: String = intent.getStringExtra("description")
        task_view_description.text = description

        val locationx: Double = intent.getDoubleExtra("locationx", 0.0)
        val locationy: Double = intent.getDoubleExtra("locationy", 0.0)

        val date: String = intent.getStringExtra("date")
        task_view_date.text = date

        val username: String = intent.getStringExtra("username")
        task_view_username.text = username

        val taskid: String = intent.getStringExtra("taskID")

        val requestorId: String = intent.getStringExtra("requestorId")

        accept_button = findViewById(R.id.accept_task_button)

        accept_button.setOnClickListener {

            if (requestorId == Helpers.getCurrentUserUid()) {
                showToast("Error: You cannot accept your own task")
                return@setOnClickListener
            }

            /* Idea: Create a new OfferedTask object based on the current node,
            and then you submit it to the taskHistory of both requestor and freelancer
            After that you set the freelancerId in both nodes

            Then remove currentTask from tasks
             */

            val currentUserUid = Helpers.getCurrentUserUid()
            // Remove the currentTask from pool of tasks
            database.getReference("task")
                .child(taskid)
                .removeValue()

            // Updating status in the requestor's task history and adding freelancerId
            val requestorDbRef = database.getReference("users")
                .child(requestorId)
                .child("taskHistory")
                .child(taskid)

            requestorDbRef.child("taskStatus").setValue(TaskStatus.ACCEPTED)
            requestorDbRef.child("freelancerId").setValue(currentUserUid)

            // Add a new node to the freelancer (accepter)
            val acceptedOfferedTask = OfferedTask(offer, title, description, locationx, locationy,
                                                    date, username, TaskStatus.ACCEPTED, taskid, requestorId)
            val freelancerDbRef = Helpers.getCurrentUserUid()?.let { it ->
                database.getReference("users")
                    .child(it)
                    .child("taskHistory")
                    .child(taskid)
            }

            freelancerDbRef?.setValue(acceptedOfferedTask)
            freelancerDbRef?.child("freelancerId")?.setValue(currentUserUid)

            showToast("Task accepted: $title")
        }
    }
}
