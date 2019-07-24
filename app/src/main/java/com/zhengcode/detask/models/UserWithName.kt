package com.zhengcode.detask.models

// TODO: REFACTOR THIS LATER!!!! COMBINE WITH THE NORMAL USER!!
// TODO: HARDCODED THE UID INTO THE DATABASE. MUST CHANGE QUICKLY
class UserWithName(val username: String, val uid: String) {
   constructor() : this("", "")
}