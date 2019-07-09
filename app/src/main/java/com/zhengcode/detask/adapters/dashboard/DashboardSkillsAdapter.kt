package com.zhengcode.detask.adapters.dashboard

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.dashboard.DashboardUpdateSkillsActivity
import com.zhengcode.detask.models.Skill
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.layout_skill_list.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class DashboardSkillsAdapter(val context: Context, private val skillStubs: List<Skill>) :
    RecyclerView.Adapter<DashboardSkillsAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.layout_skill_list, parent, false)
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
        var currentSkill: Skill? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnLongClickListener {
                // observer that you use currentSkill, and this is a property
                // of the class
                val intent = Intent(context, DashboardUpdateSkillsActivity::class.java)
                intent.putExtra("skillId", currentSkill?.skillId)
                intent.putExtra("skillName", currentSkill?.skillName)
                itemView.context.startActivity(intent)
                return@setOnLongClickListener false
            }

//            // essentially this is the same code as before for sharing!
//            itemView.imgShare.setOnClickListener {
//                currentSkill?.let {
//                    val message: String = "My skillStub is: " + currentSkill!!.skillName
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

        fun setData(skillStub: Skill, pos: Int) {
            itemView.txvSkill.text = skillStub.skillName

            this.currentSkill = skillStub
            this.currentPosition = pos
        }
    }
}
