package com.zhengcode.detask.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.zhengcode.detask.activities.MainActivity
import com.zhengcode.detask.activities.dashboard.DashboardActivity

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.login() {
    // Registration success
    val intent = Intent(this, DashboardActivity::class.java).apply {
        // we want to start a fresh activity. Will nullify the old activity (can't go back)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Context.logout() {
    // Registration success
    val intent = Intent(this, MainActivity::class.java).apply {
        // we want to start a fresh activity. Will nullify the old activity (can't go back)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}