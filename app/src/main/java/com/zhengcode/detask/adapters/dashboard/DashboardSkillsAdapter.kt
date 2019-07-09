package com.zhengcode.detask.adapters.dashboard

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengcode.detask.R
import com.zhengcode.detask.models.SkillStub
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.list_item.view.*

class DashboardSkillsAdapter(val context: Context, private val skillStubs: List<SkillStub>) :
    RecyclerView.Adapter<DashboardSkillsAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return skillStubs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val skillStub = skillStubs[position]
        holder.setData(skillStub, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // I think this is adding properties to the class
        var currentSkillStub: SkillStub? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnClickListener {
                // observer that you use currentSkillStub, and this is a property
                // of the class
                currentSkillStub?.let {
                    context.showToast(currentSkillStub!!.name + " Clicked !")
                }
            }

//            // essentially this is the same code as before for sharing!
//            itemView.imgShare.setOnClickListener {
//                currentSkillStub?.let {
//                    val message: String = "My skillStub is: " + currentSkillStub!!.name
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

        fun setData(skillStub: SkillStub, pos: Int) {
            itemView.txvTitle.text = skillStub.name

            this.currentSkillStub = skillStub
            this.currentPosition = pos
        }
    }
}
