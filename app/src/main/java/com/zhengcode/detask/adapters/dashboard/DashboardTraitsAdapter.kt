package com.zhengcode.detask.adapters.dashboard

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.dashboard.DashboardTraitsActivity
import com.zhengcode.detask.activities.dashboard.DashboardUpdateTraitsActivity
import com.zhengcode.detask.models.Trait
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.layout_trait_list.view.*

class DashboardTraitsAdapter(val context: Context, private val traits: List<Trait>) :
    RecyclerView.Adapter<DashboardTraitsAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.layout_trait_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return traits.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val trait = traits[position]
        holder.setData(trait, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // I think this is adding properties to the class
        var currentTrait: Trait? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnLongClickListener {
                // observer that you use currentSkill, and this is a property
                // of the class
                val intent = Intent(context, DashboardUpdateTraitsActivity::class.java)
                intent.putExtra("traitId", currentTrait?.traitId)
                intent.putExtra("traitName", currentTrait?.traitName)
                itemView.context.startActivity(intent)
                return@setOnLongClickListener false
            }

            // essentially this is the same code as before for sharing!
//            itemView.imgShare.setOnClickListener {
//                currentTrait?.let {
//                    val message: String = "My trait is: " + currentTrait!!.traitName
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

        fun setData(trait: Trait, pos: Int) {
            itemView.txvTrait.text = trait.traitName

            this.currentTrait = trait
            this.currentPosition = pos
        }
    }
}
