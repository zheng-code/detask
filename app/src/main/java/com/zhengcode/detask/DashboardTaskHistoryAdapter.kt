package com.zhengcode.detask

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

// they are all still tasks because they are a task item in the task history
class DashboardTaskHistoryAdapter(val context: Context, private val hobbies: List<Task>) :
    RecyclerView.Adapter<DashboardTaskHistoryAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val task = hobbies[position]
        holder.setData(task, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // I think this is adding properties to the class
        var currentTask: Task? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnClickListener {
                // observer that you use currentTask, and this is a property
                // of the class
                currentTask?.let {
                    context.showToast(currentTask!!.name + " Clicked !")
                }
            }

            // essentially this is the same code as before for sharing!
            itemView.imgShare.setOnClickListener {
                currentTask?.let {
                    val message: String = "My task is: " + currentTask!!.name

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.type = "text/plain"

                    // context is required because startActivity method is in there
                    context.startActivity(Intent.createChooser(intent, "Share to: "))
                }
            }
        }

        fun setData(task: Task, pos: Int) {
            itemView.txvTitle.text = task.name

            this.currentTask = task
            this.currentPosition = pos
        }
    }
}
