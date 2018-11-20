package edu.osu.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class AddEventTest {
    private final String TAG = super.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<AddEventActivity> mActivity =
            new ActivityTestRule(AddEventActivity.class);


    private AddEventFragment mFragment; // Fragment to be tested

    @Before
    public void createAddEventActivity() {
        mFragment = new AddEventFragment();
    }
        /*=====================================   Test Cases Here   =====================================*/

    /*===== Required =====*/
    @Test
    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
    }

    @Test
    public void eventCategoryTest_Casual() {
        onView(withId(R.id.add_event_category_spinner)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Casual"))).perform(click()); // Select "Casual"

        onView(withId(R.id.add_event_category_spinner)).check(matches(withSpinnerText("Casual"))); // Assert "Casual" is selected
    }

    @Test
    public void eventCategoryTest_Professional() {
        onView(withId(R.id.add_event_category_spinner)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Professional"))).perform(click()); // Select "Professional"

        onView(withId(R.id.add_event_category_spinner)).check(matches(withSpinnerText("Professional"))); // Assert "Professional" is selected
    }

    @Test
    public void eventCategoryTest_BusinessCasual() {
        onView(withId(R.id.add_event_category_spinner)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Business Casual"))).perform(click()); // Select "Business Casual"

        onView(withId(R.id.add_event_category_spinner)).check(matches(withSpinnerText("Business Casual"))); // Assert "Business Casual" is selected
    }

    @Test
    public void eventCategoryTest_Romantic() {
        onView(withId(R.id.add_event_category_spinner)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Romantic"))).perform(click()); // Select "Romantic"

        onView(withId(R.id.add_event_category_spinner)).check(matches(withSpinnerText("Romantic"))); // Assert "Romantic" is selected
    }

    @Test
    public void eventCategoryTest_Athletic() {
        onView(withId(R.id.add_event_category_spinner)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Athletic"))).perform(click()); // Select "Athletic"

        onView(withId(R.id.add_event_category_spinner)).check(matches(withSpinnerText("Athletic"))); // Assert "Athletic" is selected
    }

    @Test
    public void eventNameTest_Normal() {
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.add_event_name_edittext)).perform(clearText(), typeText("My Event"));
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
    }

    @Test
    public void eventNameTest_Empty() {
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.add_event_name_edittext)).perform(clearText(), typeText(""));
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
    }

    @Test
    public void eventNameTest_Long() {
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.add_event_name_edittext)).perform(clearText(), typeText("This is my very long event name so that I can test what happens with a long event name"));
        onView(withId(R.id.add_event_name_edittext)).check(matches(isDisplayed()));
    }

}
