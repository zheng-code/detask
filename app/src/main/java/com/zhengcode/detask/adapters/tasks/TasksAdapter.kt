package com.zhengcode.detask.adapters.tasks

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.tasks.ViewTaskActivity
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.item_recycler.view.*



class TasksAdapter(val context: Context, private val offered_tasks: List<OfferedTask>) : RecyclerView.Adapter<TasksAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return offered_tasks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val task = offered_tasks[position]
        holder.setData(task, position)
    }

    companion object {
        val TASK_KEY = "TASK_KEY"
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
                intent.putExtra(TASK_KEY, currentTask)
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