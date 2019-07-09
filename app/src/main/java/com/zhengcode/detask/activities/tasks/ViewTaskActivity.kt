package com.zhengcode.detask.activities.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhengcode.detask.R
import com.zhengcode.detask.models.OfferedTask
import kotlinx.android.synthetic.main.task_view_page.*

class ViewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_view_page)
        val offer:String = intent.getStringExtra("offer")
        task_view_offer.text = "$" + offer
        val title:String = intent.getStringExtra("title")
        task_view_title.text = title
        val description:String = intent.getStringExtra("description")
        task_view_description.text = description
        val date:String = intent.getStringExtra("date")
        task_view_date.text = date


    }

}