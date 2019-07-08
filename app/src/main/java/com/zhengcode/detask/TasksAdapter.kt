package com.zhengcode.detask

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.view.*

class TasksAdapter(val context: Context, private val offered_tasks: List<OfferedTask>) : RecyclerView.Adapter<TasksAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val viewButton = view.findViewById<TextView>(R.id.taskViewButton)

        viewButton.setOnClickListener {
            showViewDialog()
        }

        return MyViewHolder(view)
    }

    fun showViewDialog() {
        val builder = AlertDialog.Builder(context)


        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.task_view_page, null)

        builder.setView(view)

        builder.setPositiveButton("View") { p0, p1 ->

        }

        builder.setNegativeButton("No") { p0, p1 ->

        }

        val alert = builder.create()
        alert.show()

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
                    context.showToast(currentTask!!.title + " Clicked !")
                }
            }

//            // essentially this is the same code as before for sharing!
//            itemView.imgShare.setOnClickListener {
//                currentTask?.let {
//                    val message: String = "My task is: " + currentTask!!.title
//
//                    val intent = Intent()
//                    intent.action = Intent.ACTION_SEND
//                    intent.putExtra(Intent.EXTRA_TEXT, message)
//                    intent.type = "text/plain"
//
//                    // context is required because startActivity method is in there
//                    context.startActivity(Intent.createChooser(intent, "Share to: "))
//                }
//            }
        }

        fun setData(task: OfferedTask, pos: Int) {
            itemView.txvTitle.text = task.title

            this.currentTask = task
            this.currentPosition = pos
        }
    }
}