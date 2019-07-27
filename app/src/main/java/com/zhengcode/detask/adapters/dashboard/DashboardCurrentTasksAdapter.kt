package com.zhengcode.detask.adapters.dashboard

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.tasks.ViewTaskActivity
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.item_recycler.view.*
import kotlinx.android.synthetic.main.list_item.view.*

// they are all still tasks because they are a task item in the task history
class DashboardCurrentTasksAdapter(val context: Context, private val offeredTasks : List<OfferedTask>) :
    RecyclerView.Adapter<DashboardCurrentTasksAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return offeredTasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val task = offeredTasks[position]
        holder.setData(task, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // I think this is adding properties to the class
        var currentTask: OfferedTask? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnClickListener {
                // observer that you use currentTask, and this is a property
                // of the class
                currentTask?.let {
//                    context.showToast(currentTask!!.title + " Clicked !")
                }

                val intent = Intent(context, ViewTaskActivity::class.java)
                intent.putExtra(TasksAdapter.TASK_KEY, currentTask)
                itemView.context.startActivity(intent)
            }

        }

        fun setData(task: OfferedTask, pos: Int) {
            itemView.txvTitle.text = task.title
            itemView.txvDescription.text = task.description
            val offer = "$" + task.offer.toString()
            itemView.txvOffer.text = offer
            itemView.txvUsername.text = task.username

            this.currentTask = task
            this.currentPosition = pos
        }
    }
}
