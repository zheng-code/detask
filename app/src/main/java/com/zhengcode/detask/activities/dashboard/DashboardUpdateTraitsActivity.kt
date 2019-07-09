package com.zhengcode.detask.activities.dashboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Trait
import com.zhengcode.detask.utils.Constants
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.update_dialog.*

class DashboardUpdateTraitsActivity : AppCompatActivity() {

    private lateinit var editTextUpdatedName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_dialog)
        editTextUpdatedName = findViewById(R.id.editTextUpdatedName)

        val traitId = intent.getStringExtra("traitId")
        val traitName = intent.getStringExtra("traitName")

        updateTitle.text = "Update ${traitName}"

        buttonUpdate.setOnClickListener {
            val updatedTraitName= editTextUpdatedName.text.toString().trim()

            if (TextUtils.isEmpty(updatedTraitName)) {
                editTextUpdatedName.error = "Input required"
                return@setOnClickListener
            }

            updateTrait(updatedTraitName, traitId)
        }

        buttonDelete.setOnClickListener {
            deleteTrait(traitId)
        }

    }

    private fun deleteTrait(traitId: String) {
        val databaseReference: DatabaseReference? = Constants.getCurrentUserUid()?.let {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("traits")
                .child(traitId)
        }

        databaseReference?.removeValue()

        showToast("Trait deleted successfully")
    }

    private fun updateTrait(updatedTraitName: String, traitId: String) {
        val databaseReference: DatabaseReference? = Constants.getCurrentUserUid()?.let {
            FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("traits")
                .child(traitId)
        }

        val newTrait = Trait(updatedTraitName, traitId)

        databaseReference?.setValue(newTrait)
        showToast("Trait updated successfully")
    }
}