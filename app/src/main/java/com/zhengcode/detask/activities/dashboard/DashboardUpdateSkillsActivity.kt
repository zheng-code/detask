package com.zhengcode.detask.activities.dashboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Skill
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.update_dialog.*

class DashboardUpdateSkillsActivity : AppCompatActivity() {

    private lateinit var editTextUpdatedName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_dialog)
        editTextUpdatedName = findViewById(R.id.editTextUpdatedName)

        val skillId = intent.getStringExtra("skillId")
        val skillName = intent.getStringExtra("skillName")

        updateTitle.text = "Update ${skillName}"

        buttonUpdate.setOnClickListener {
            val updatedSkillName= editTextUpdatedName.text.toString().trim()

            if (TextUtils.isEmpty(updatedSkillName)) {
                editTextUpdatedName.error = "Input required"
                return@setOnClickListener
            }

            updateSkill(updatedSkillName, skillId)
        }

        buttonDelete.setOnClickListener {
            deleteSkill(skillId)
        }

    }

    private fun deleteSkill(skillId: String) {
        val databaseReference: DatabaseReference? = Helpers.getCurrentUserUid()?.let {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("skills")
                .child(skillId)
        }

        databaseReference?.removeValue()

        showToast("Skill deleted successfully")
    }

    private fun updateSkill(updatedSkillName: String, skillId: String) {
        val databaseReference: DatabaseReference? = Helpers.getCurrentUserUid()?.let {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("skills")
                .child(skillId)
        }

        val newSkill = Skill(updatedSkillName, skillId)

        databaseReference?.setValue(newSkill)
        showToast("Skill updated successfully")
    }
}
