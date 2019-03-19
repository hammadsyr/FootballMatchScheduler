package com.example.hammad.fifthkadesubmission.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.main.MainActivity
import com.example.hammad.fifthkadesubmission.matches.MatchDetail
import com.example.hammad.fifthkadesubmission.util.EspressoIdlingResource
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @Test
    fun testRequestApiAddToFav() {
        val activity = activityTestRule.activity
        onView(withId(R.id.matches))
                .perform(click())
        //Thread.sleep(1000)
        //val idlingResources = MainIdlingResources()
        //IdlingRegistry.getInstance().register(idlingResources)
        onView(withId(R.id.prev_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.add_to_favourite))
                .perform(click())
        if (MatchDetail.i == 1)
            onView(withText(R.string.added_to_favorite)).inRoot(withDecorView(not(`is`(activity.window.decorView)))).check(matches(isDisplayed()))
        else
            onView(withText(R.string.removed_from_favorite)).inRoot(withDecorView(not(`is`(activity.window.decorView)))).check(matches(isDisplayed()))
        //IdlingRegistry.getInstance().unregister(idlingResources)
        pressBack()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }
}