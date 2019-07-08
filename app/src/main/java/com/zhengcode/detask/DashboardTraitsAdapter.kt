package com.zhengcode.detask

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class DashboardTraitsAdapter(val context: Context, private val traitStubs: List<TraitStub>) :
    RecyclerView.Adapter<DashboardTraitsAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // It is responsible for creating the ViewHolder, which is the each item in the list
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return traitStubs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // responsible for binding all data to all the
        // views that are created
        val traitStub = traitStubs[position]
        holder.setData(traitStub, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // I think this is adding properties to the class
        var currentTraitStub: TraitStub? = null
        var currentPosition: Int = 0

        init {
            // itemView obtained from the parameter
            itemView.setOnClickListener {
                // observer that you use currentTraitStub, and this is a property
                // of the class
                currentTraitStub?.let {
                    context.showToast(currentTraitStub!!.name + " Clicked !")
                }
            }

            // essentially this is the same code as before for sharing!
            itemView.imgShare.setOnClickListener {
                currentTraitStub?.let {
                    val message: String = "My traitStub is: " + currentTraitStub!!.name

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.type = "text/plain"

                    // context is required because startActivity method is in there
                    context.startActivity(Intent.createChooser(intent, "Share to: "))
                }
            }
        }

        fun setData(traitStub: TraitStub, pos: Int) {
            itemView.txvTitle.text = traitStub.name

            this.currentTraitStub = traitStub
            this.currentPosition = pos
        }
    }
}
