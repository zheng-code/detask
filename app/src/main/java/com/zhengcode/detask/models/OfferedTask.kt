package com.zhengcode.detask.models

import android.os.Parcelable
import com.zhengcode.detask.utils.TaskStatus
import kotlinx.android.parcel.Parcelize


@Parcelize
class OfferedTask(val offer: Double, val title: String, val description: String,
                  val locationx: Double, val locationy: Double, val date: String,
                  val username: String?, val taskStatus: TaskStatus, val taskid: String,
                  val requestorId: String?): Parcelable {

    constructor() : this(0.0, "", "", 0.0,
                        0.0, "", "", TaskStatus.WAITING, "",
                        ""){}



}

// include an array of tags, use an enum of predefined tags
// create a state with "waiting to be accepted", "currently being done", "deleted", "Finished"
