package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.messages.ChatLogActivity
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import com.zhengcode.detask.models.ChatMessage
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.models.UserWithName
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.TaskStatus
import com.zhengcode.detask.views.DashboardChatRow
import kotlinx.android.synthetic.main.activity_dashboard_chats.*
import kotlinx.android.synthetic.main.dashboard_chat_row.view.*

class DashboardChatsActivity : AppCompatActivity() {
    companion object {
        val TAG = "DashboardChatsActivity"
    }

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_chats)

        recyclerView_dashboard_chats.adapter = adapter
        // set itemClickListener to your adapter
        adapter.setOnItemClickListener { item, view ->
            Log.d(TAG, "123")
            val intent = Intent(this, ChatLogActivity::class.java)
            val row = item as DashboardChatRow
            val chatPartnerUser = row.chatPartnerUser

            // This is a technical debt. Because of the way we implemented the original messaging...
            val stubTask = OfferedTask(1.0, "", "", 1.0, 1.0, "", "",
                TaskStatus.WAITING, "", chatPartnerUser?.uid)

            intent.putExtra(TasksAdapter.TASK_KEY, stubTask)

            startActivity(intent)
        }

        listenForLatestMessages()

    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(DashboardChatRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val fromId = Helpers.getCurrentUserUid()
        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
