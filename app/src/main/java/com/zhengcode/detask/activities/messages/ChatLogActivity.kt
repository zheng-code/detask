package com.zhengcode.detask.activities.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.zhengcode.detask.R
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import com.zhengcode.detask.models.ChatMessage
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.Helpers
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerView_chat_log.adapter = adapter

        // Supposed to jump to the end but hmmmmm
        recyclerView_chat_log.scrollToPosition(adapter.itemCount - 1)
        val currentTask = intent.getParcelableExtra<OfferedTask>(TasksAdapter.TASK_KEY)
        // Currently no Action Bar, so this doesn't show anything
        supportActionBar?.title = "Chat Log"

        //setupDummyData()
        listenForMessages()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message..")
            performSendMessage()
        }

        otherUser_chat_log.text = currentTask.username
    }

    private fun listenForMessages() {
        val currentTask = intent.getParcelableExtra<OfferedTask>(TasksAdapter.TASK_KEY)
        val fromId = Helpers.getCurrentUserUid()
        val toId = currentTask.requestorId
        val ref = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                val currentUserUid = Helpers.getCurrentUserUid()
                if (chatMessage !== null) {
                    // Probably need to do this the kotlin way lol
                    Log.d(TAG, "fromId: ${chatMessage.fromId}, current Id: ${currentUserUid}")

                    // TODO: ANALYSE THE LOGIC OF THIS CODE
                    if (chatMessage.fromId == currentUserUid && chatMessage.toId == currentTask.requestorId) {
                        // I sent this message. So it's from me to you
                        Log.d(TAG, "Called???")
                        adapter.add(ChatFromItem(chatMessage.text))
                    } else if (chatMessage.fromId == currentTask.requestorId && chatMessage.toId == currentUserUid){
                        adapter.add(ChatToItem(chatMessage.text))
                    } else {
                        // The chat doesn't belong to this display
                    }
                }
                recyclerView_chat_log.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }

    private fun performSendMessage() {
        // How do we actually send message to firebase..
        val currentTask = intent.getParcelableExtra<OfferedTask>(TasksAdapter.TASK_KEY)

        val text = editText_chat_log.text.toString()
        val fromId = Helpers.getCurrentUserUid()
        val toId = currentTask.requestorId
        val timestamp = System.currentTimeMillis() / 1000

        val reference = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/$fromId/$toId")
            .push()

        val toReference = FirebaseDatabase
            .getInstance()
            .getReference("/user-messages/$toId/$fromId")
            .push()

        val id = reference.key

        val chatMessage = ChatMessage(id!!, text, fromId!!, toId!!, timestamp)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message : ${reference.key}")
                editText_chat_log.text.clear()
                recyclerView_chat_log.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)

        val latestMessageRef = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase
            .getInstance()
            .getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(ChatFromItem("Hi!"))
        adapter.add(ChatToItem("How art thou"))
        adapter.add(ChatFromItem("Hmmmm"))
        adapter.add(ChatFromItem("Would you like to talk abt this?"))
        adapter.add(ChatFromItem("No thanks"))
        adapter.add(ChatToItem("I'm almost there"))
        adapter.add(ChatToItem("Come on now"))
        adapter.add(ChatToItem("LAST ONE!"))

        recyclerView_chat_log.adapter = adapter
    }
}

class ChatFromItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
