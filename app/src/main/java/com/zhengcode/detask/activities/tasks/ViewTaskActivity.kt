package com.zhengcode.detask.activities.tasks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.messages.ChatLogActivity
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.TaskStatus
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.task_view_page.*

class ViewTaskActivity : AppCompatActivity() {

    lateinit var accept_button: Button
    lateinit var message_button: Button

    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_view_page)

        // Take all the data in the selected task and put in the task_view_page
        val currentTask = intent.getParcelableExtra<OfferedTask>(TasksAdapter.TASK_KEY)
        val offer: Double = currentTask.offer
        task_view_offer.text = "$" + offer.toString()

        val title: String = currentTask.title
        task_view_title.text = title

        val description: String = currentTask.description
        task_view_description.text = description

        val locationx: Double = currentTask.locationx
        val locationy: Double = currentTask.locationy

        val date: String = currentTask.date
        task_view_date.text = date


        val username: String = currentTask.username!!
        task_view_username.text = username

        val taskid: String = currentTask.taskid

        val requestorId: String = currentTask.requestorId!!
        // --- End of trying to get Data from the previous intent ---

        setUpAcceptButton(offer, title, description, locationx,
            locationy, date, username, taskid, requestorId)

        setUpMessageButton(currentTask)


    }

    // Difference with OfferedTask is that this one doesn't have TaskStatus
    private fun setUpAcceptButton(offer: Double, title: String, description: String,
                      locationx: Double, locationy: Double, date: String,
                      username: String?, taskid: String,
                      requestorId: String) {
        // --- Matters pertaining the accept button ---
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
        // --- End of matter pertaining to accept button
    }

    private fun setUpMessageButton(currentTask: OfferedTask) {
        message_button = findViewById(R.id.message_user_button)

        message_button.setOnClickListener {
            if (currentTask.requestorId == Helpers.getCurrentUserUid()) {
                showToast("Error: You cannot message yourself")
                return@setOnClickListener
            }

            val intent = Intent(this, ChatLogActivity::class.java)
            // TODO: Change this putExtra to something simpler, so I can use DashboardChatsActivity
            intent.putExtra(TasksAdapter.TASK_KEY, currentTask)
            startActivity(intent)
        }

    }
}
