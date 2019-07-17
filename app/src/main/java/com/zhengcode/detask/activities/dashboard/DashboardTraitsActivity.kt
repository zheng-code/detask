package com.zhengcode.detask.activities.dashboard

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.zhengcode.detask.adapters.dashboard.DashboardTraitsAdapter
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Supplier
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.models.Trait
import com.zhengcode.detask.utils.Helpers
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_dashboard_skills.*
import kotlinx.android.synthetic.main.activity_dashboard_traits.*

class DashboardTraitsActivity: AppCompatActivity() {
    private lateinit var editTextTraitName: EditText

    private lateinit var databaseTraits: DatabaseReference

    lateinit var traits: MutableList<Trait>

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewTraits.layoutManager = layoutManager

        val adapter = DashboardTraitsAdapter(this, traits)
        recyclerViewTraits.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_traits)
        editTextTraitName = findViewById(R.id.editTextTraitName)
        traits = arrayListOf()

        Helpers.getCurrentUserUid()?.let {
            databaseTraits = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("traits")
        }

        buttonAddTrait.setOnClickListener {
            saveTrait()
        }

        traits = mutableListOf()

        databaseTraits.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    traits.clear()
                    for (traitSnapshot in dataSnapshot.children) {
                        val trait = traitSnapshot.getValue(Trait::class.java)
                        trait?.let {
                            traits.add(it)
                        }
                    }

                    setupRecycleView()
                }
            }

        })
    }

    private fun saveTrait() {
        val traitName = editTextTraitName.text.toString().trim()
        if (!TextUtils.isEmpty(traitName)) {
            val traitId = databaseTraits.push().key
            val trait = Trait(traitName, traitId)

            traitId?.let {
                databaseTraits.child(traitId).setValue(trait)
                showToast("Trait successfully added")
            }
        } else {
            showToast("Adding of trait is unsuccessful")
        }
    }
}
