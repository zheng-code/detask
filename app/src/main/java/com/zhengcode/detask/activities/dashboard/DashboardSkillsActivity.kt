package com.zhengcode.detask.activities.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zhengcode.detask.adapters.dashboard.DashboardSkillsAdapter
import com.zhengcode.detask.R
import com.zhengcode.detask.models.Skill
import com.zhengcode.detask.utils.showToast
import kotlinx.android.synthetic.main.activity_dashboard_skills.*

class DashboardSkillsActivity: AppCompatActivity() {
    private lateinit var editTextSkillName: EditText

    private lateinit var databaseSkills: DatabaseReference

    lateinit var skills: MutableList<Skill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_skills)
        editTextSkillName = findViewById(R.id.editTextSkillName)
        skills = ArrayList()

        /* Design: Each user has a "skills" node and the skills you input will be added
        to this particular node
        */
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            databaseSkills = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(it)
                .child("skills")
        }

        buttonAddSkill.setOnClickListener {
            saveSkill()
        }

        // Section that creates list of tasks taskList and displays them ///////////
        skills = mutableListOf()


        databaseSkills.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    skills.clear()
                    for (skillSnapshot in dataSnapshot.children) {
                        val skill= skillSnapshot.getValue(Skill::class.java)
                        skill?.let {
                            skills.add(it)
                        }
                        Log.d("DashboardSkillsActivity", "Inside enhanced for loop: " + skillSnapshot)
                    }

                    setupRecycleView()
                }
            }
        })
    }

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewSkills.layoutManager = layoutManager

        val adapter = DashboardSkillsAdapter(this, skills)
        recyclerViewSkills.adapter = adapter
        Log.i("TasksActivity", "Number of items: ${skills.size}")

    }

    private fun saveSkill() {
        val skillName = editTextSkillName.text.toString().trim()
        if (!TextUtils.isEmpty(skillName)) {
            val skillId = databaseSkills.push().key

            val skill = Skill(skillName, skillId)

            skillId?.let {
                databaseSkills.child(skillId).setValue(skill)
                showToast("Skill successfully added")
            }

        } else {
            showToast("Adding of skill is unsuccessful")
        }
    }
}
