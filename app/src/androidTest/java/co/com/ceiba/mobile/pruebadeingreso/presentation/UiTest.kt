package co.com.ceiba.mobile.pruebadeingreso.presentation

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.com.ceiba.mobile.pruebadeingreso.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents


@LargeTest
@RunWith(AndroidJUnit4::class)
class UiTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Before
    fun intentsInit() {
        Intents.init()
    }

    @After
    fun intentsTeardown() {
        Intents.release()
    }


    @Test
    fun emptyTest() {
        clickInputSearch()
        keypressInputSearch("empty")
        onView(Matchers.allOf(ViewMatchers.withText("List is empty")))
            .check(ViewAssertions.matches(ViewMatchers.withText("List is empty")))
    }

    @Test
    fun ervinTest() {
        clickInputSearch()
        keypressInputSearch("Ervin")
        onView(Matchers.allOf(ViewMatchers.withId(R.id.name)))
            .check(ViewAssertions.matches(ViewMatchers.withText("Ervin Howell")))
        onView(Matchers.allOf(ViewMatchers.withId(R.id.phone)))
            .check(ViewAssertions.matches(ViewMatchers.withText("010-692-6593 x09125")))
        onView(Matchers.allOf(ViewMatchers.withId(R.id.email)))
            .check(ViewAssertions.matches(ViewMatchers.withText("Shanna@melissa.tv")))
    }

    @Test
    fun leanneTest() {
        clickInputSearch()
        keypressInputSearch("Leanne")
        onView(Matchers.allOf(ViewMatchers.withId(R.id.name)))
            .check(ViewAssertions.matches(ViewMatchers.withText("Leanne Graham")))
        onView(Matchers.allOf(ViewMatchers.withId(R.id.phone)))
            .check(ViewAssertions.matches(ViewMatchers.withText("1-770-736-8031 x56442")))
        onView(Matchers.allOf(ViewMatchers.withId(R.id.email)))
            .check(ViewAssertions.matches(ViewMatchers.withText("Sincere@april.biz")))
    }

    @Test
    @Throws(InterruptedException::class)
    fun leannePostClickVerPublicacionesTest() {
        clickInputSearch()
        keypressInputSearch("Leanne")
        onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_view_post)))
            .perform(ViewActions.click())
        Thread.sleep(4000)
        onView(Matchers.allOf(ViewMatchers.withId(R.id.name)))
            .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.endsWith("Leanne Graham"))))
        onView(Matchers.allOf(ViewMatchers.withId(R.id.phone)))
            .check(ViewAssertions.matches(ViewMatchers.withText("1-770-736-8031 x56442")))
        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.title),
                ViewMatchers.withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")))
    }

    private fun keypressInputSearch(valueToWrite: String) {
        onView(Matchers.allOf(ViewMatchers.withId(R.id.editTextSearch)))
            .perform(ViewActions.replaceText(valueToWrite), ViewActions.closeSoftKeyboard())
    }

    private fun clickInputSearch() {
        onView(Matchers.allOf(ViewMatchers.withId(R.id.editTextSearch)))
            .perform(ViewActions.click())
    }

    companion object {
        private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
        ): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("Child at position $position in parent ")
                    parentMatcher.describeTo(description)
                }

                public override fun matchesSafely(view: View): Boolean {
                    val parent = view.parent
                    return (parent is ViewGroup && parentMatcher.matches(parent)
                            && view == parent.getChildAt(position))
                }
            }
        }
    }
}