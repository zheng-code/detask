package com.zhengcode.detask

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.zhengcode.detask.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.annotation.StringRes
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    // This is required to fire off the Activity that you want to test
    // TODO: ENSURE USER IS NOT LOGGED IN FIRST
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityLoadsCorrectly() {
        onView(withId(R.id.login_title)).check(matches(withText(R.string.sign_in_page)))
    }

    @Test
    fun whenAllFieldsEmpty_raiseError() {
        onView(withId(R.id.button_sign_in)).perform(click())
        onView(withId(R.id.edit_text_email)).check(matches(hasErrorText("Email Required")))
    }

    @Test
    fun whenEmailIsInvalid_raiseError() {
        onView(withId(R.id.edit_text_email))
            .perform(typeText("test7"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())
        onView(withId(R.id.edit_text_email)).check(matches(hasErrorText("Valid Email Required")))
    }

    @Test
    fun whenPasswordIsEmpty_raiseError() {
        onView(withId(R.id.edit_text_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())
        onView(withId(R.id.edit_text_password)).check(matches(hasErrorText("6 char password required")))
    }

    @Test
    fun whenPasswordIsTooShort_raiseError() {
        onView(withId(R.id.edit_text_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password))
            .perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())
        onView(withId(R.id.edit_text_password)).check(matches(hasErrorText("6 char password required")))
    }

    @Test
    fun whenLoginSuccessful_loadDashboardActivity() {
        onView(withId(R.id.edit_text_email))
            .perform(typeText("test7@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password))
            .perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())
        // TODO: USE IDLING RESOURCES INSTEAD OF MANUALLY WAITING
        // NOTE: IF IT FAILS, CONSIDER HIGHER WAITING TIME
        Thread.sleep(2500)
        onView(withId(R.id.dashboard_username)).check(matches(isDisplayed()))
        logout()
    }

    @Test
    fun whenLoginUnsuccessful_errorToastIsShown() {
       // TODO: NEED TO FIND A WAY TO ASSERT TOAST MESSAGES
    }

    private fun logout() {
        onView(withId(R.id.btn_sign_out)).perform(click())
        onView(withText("Yes"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());
    }

    // For getting the string value of R.string.something
    private fun getString(@StringRes resourceId: Int): String {
        return rule.activity.getString(resourceId)
    }
}
