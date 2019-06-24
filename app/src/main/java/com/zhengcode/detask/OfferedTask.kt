package com.zhengcode.detask


class OfferedTask(val offer: Double, val title: String, val description: String,
                val locationx: Double, val locationy: Double, val date: String) {

    constructor() : this(0.0, "", "", 0.0, 0.0, ""){}

}

