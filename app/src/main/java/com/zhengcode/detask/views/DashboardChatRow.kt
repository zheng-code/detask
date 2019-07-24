package com.zhengcode.detask.views

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.zhengcode.detask.R
import com.zhengcode.detask.models.ChatMessage
import com.zhengcode.detask.models.UserWithName
import com.zhengcode.detask.utils.Helpers
import kotlinx.android.synthetic.main.dashboard_chat_row.view.*

class DashboardChatRow(val chatMessage: ChatMessage): Item<ViewHolder>() {
    var chatPartnerUser: UserWithName? = null
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.dashboard_chat_latest_message.text = chatMessage.text

        val chatPartnerId: String
        // TODO : Try to understand this logic?
        if (chatMessage.fromId == Helpers.getCurrentUserUid()) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                // TODO: THIS IS LIFEHACKS ONLY BECAUSE I NEED TO ADD A USER MODEL WITH USERNAME
                chatPartnerUser = p0.getValue(UserWithName::class.java)
                viewHolder.itemView.dashboard_chat_username.text = chatPartnerUser?.username
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    override fun getLayout(): Int {
        return R.layout.dashboard_chat_row
    }
}