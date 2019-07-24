package com.zhengcode.detask

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.zhengcode.detask.activities.MainActivity
import org.junit.runner.RunWith
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.ViewHolder
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import com.zhengcode.detask.activities.tasks.TasksActivity
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
// Does not require logging in, but requires at least one task to be loaded
class TasksActivityTest {
    @Rule
    @JvmField
//    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    val rule: ActivityTestRule<TasksActivity> = ActivityTestRule(TasksActivity::class.java)


    companion object {
        @BeforeClass
        @JvmStatic
        fun login() {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("test7@gmail.com", "123456")
            Thread.sleep(2000)
        }

        @AfterClass
        @JvmStatic
        fun logout() {
            FirebaseAuth.getInstance().signOut()
            Thread.sleep(2000)
        }
    }

    @Test
    fun tasksActivityLoadsCorrectly() {
        // May not be an important test
        // TODO: IDK WHY ITS LIKE THIS BUT WE GOTTA CHANGE THIS TO A NORMAL TASK
        onView(withId(R.id.dashboard_task_history_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingDashboardTab_loadDashboardActivity() {
        // Pressing the Dashboard tab
        onView(withId(R.id.navigation_dashboard)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTasksTab_nothingHappens() {
        // Pressing the Tasks tab
        onView(withId(R.id.navigation_tasks)).perform(click())

        // onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        // TODO: How to check if no new Activity is launched?
    }

    @Test
    fun onClickingTaskManagerTab_loadTaskManagerActivity() {
        // Pressing the Task Manager tab
        onView(withId(R.id.navigation_task_manager)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.task_manager_message)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingATask_loadViewTaskActivity() {
        // Wait for data to load
        Thread.sleep(2000)

        onView(withId(R.id.dashboard_task_history_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.scrollView2)).check(matches(isDisplayed()))
    }
}
