package com.zhengcode.detask.utils

import com.google.firebase.auth.FirebaseAuth

object Constants {
    // Need to thread carefully. When is this retrieved? It could possibly be null
    val getCurrentUserUid = { FirebaseAuth.getInstance().currentUser?.uid }
}