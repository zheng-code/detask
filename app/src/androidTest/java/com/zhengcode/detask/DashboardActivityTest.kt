package com.zhengcode.detask

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.zhengcode.detask.activities.MainActivity
import org.junit.runner.RunWith
import androidx.annotation.StringRes
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.firebase.auth.FirebaseAuth
import com.zhengcode.detask.activities.dashboard.DashboardActivity
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
// Before running this test, ensure a user is logged in (now handled by before and after class)
class DashboardActivityTest {
    // This is required to fire off the Activity that you want to test
    @Rule
    @JvmField
//    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    val rule: ActivityTestRule<DashboardActivity> = ActivityTestRule(DashboardActivity::class.java)

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
        onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingDashboardTab_nothingHappens() {
        // Pressing the Dashboard tab
        onView(withId(R.id.navigation_dashboard)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
        // TODO: How to check if no new Activity is launched?
    }

    @Test
    fun onClickingTasksTab_loadTasksActivity() {
        // Pressing the Tasks tab
        onView(withId(R.id.navigation_tasks)).perform(click())

        onView(withId(R.id.dashboard_task_history_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTaskManagerTab_loadTaskManagerActivity() {
        // Pressing the Task Manager tab
        onView(withId(R.id.navigation_task_manager)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.task_manager_message)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingCurrentTasks_loadDashboardCurrentTasksActivity() {
        onView(withId(R.id.btn_current_tasks)).perform(click())

        onView(withId(R.id.dashboard_task_history_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingEditProfile_loadDashboardEditProfileActivity() {
        onView(withId(R.id.btn_edit_profile)).perform(click())

        onView(withId(R.id.button_save)).check(matches(isDisplayed()))
        onView(withId(R.id.image_view)).check(matches(isDisplayed()))
    }


    @Test
    fun onClickingSkills_loadDashboardSkillsActivity() {
        onView(withId(R.id.btn_skills)).perform(click())

        onView(withId(R.id.recyclerViewSkills)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTraits_loadDashboardTraitsActivity() {
        onView(withId(R.id.btn_traits)).perform(click())

        onView(withId(R.id.recyclerViewTraits)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingTaskHistory_loadDashboardTaskHistoryActivity() {
        onView(withId(R.id.btn_task_history)).perform(click())

        onView(withId(R.id.dashboard_task_history_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingSettings_loadDashboardSettingsActivity() {
        onView(withId(R.id.btn_settings)).perform(click())

        // TODO: CHANGE THIS WHEN THE SETTINGS PAGE HAVE BEEN IMPLEMENTED
        onView(withId(R.id.text_settings)).check(matches(isDisplayed()))
    }

    @Test
    fun onClickingSignOut_loadMainActivity() {
        onView(withId(R.id.btn_sign_out)).perform(click())
        onView(withText("Yes"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());

        onView(withId(R.id.button_sign_in)).check(matches(isDisplayed()))

        // To maintain "Loop Invariant"
        FirebaseAuth.getInstance().signInWithEmailAndPassword("test7@gmail.com", "123456")
        Thread.sleep(2000)
    }

    /*
    // THIS IS NOT NECESSARY BECAUSE YOU CAN JUMP INTO THE THING DIRECTLY???? COOOL
    // I found out it's probably because there is no verification when the respective
    // activity is created
    @Before
    fun login() {
        onView(withId(R.id.edit_text_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password))
            .perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())
        // TODO: USE IDLING RESOURCES INSTEAD OF MANUALLY WAITING
        // NOTE: IF IT FAILS, CONSIDER HIGHER WAITING TIME
        Thread.sleep(2000)
    }

    @After
    fun logout() {
        onView(withId(R.id.btn_sign_out)).perform(click())
        onView(withText("Yes"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());
    }
    */

}
