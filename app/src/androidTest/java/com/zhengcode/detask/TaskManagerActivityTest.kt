package com.zhengcode.detask

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.firebase.auth.FirebaseAuth
import com.zhengcode.detask.activities.taskmanager.TaskManagerActivity
import com.zhengcode.detask.adapters.tasks.TasksAdapter
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class TaskManagerActivityTest{
    @Rule
    @JvmField
//    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    val rule: ActivityTestRule<TaskManagerActivity> = ActivityTestRule(TaskManagerActivity::class.java)


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
    fun taskManagerActivityLoadsCorrectly() {
        // May not be an important test
        onView(withId(R.id.task_manager_message)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingDashboardTab_loadDashboardActivity() {
        // Pressing the Dashboard tab
        onView(withId(R.id.navigation_dashboard)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTasksTab_loadTasksActivity() {
        // Pressing the Tasks tab
        onView(withId(R.id.navigation_tasks)).perform(click())

        onView(withId(R.id.dashboard_task_history_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTaskManagerTab_nothingHappens() {
        // Pressing the Task Manager tab
        onView(withId(R.id.navigation_task_manager)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.task_manager_message)).check(matches(isDisplayed()))
    }

    @Test
    fun inputs() {
        onView(withId(R.id.offer_input))
            .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.title_input))
            .perform(typeText("Espresso Test Title"), closeSoftKeyboard())
        onView(withId(R.id.description_input))
            .perform(typeText("Espresso Test Description"), closeSoftKeyboard())
        onView(withId(R.id.location_x_coordinate))
            .perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.location_y_coordinate))
            .perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.date_input))
            .perform(typeText("20190724"), closeSoftKeyboard())
        /* TODO: Submit this task, switch page and scroll to the very end.
           Need to find out how to get to the end of the items
         */
    }

    /*
    @Test
    fun switchPage() {
        onView(withId(R.id.navigation_tasks)).perform(click())

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
    */

}
