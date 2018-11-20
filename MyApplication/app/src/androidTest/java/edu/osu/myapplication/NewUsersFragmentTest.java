package edu.osu.myapplication;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;


/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
public class NewUsersFragmentTest {


    //getActivity
    @Rule
    public ActivityTestRule<NewUsersActivity> activityActivityTestRule = new ActivityTestRule<NewUsersActivity>(NewUsersActivity.class);

    //getFragment
    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void newUserRegistrationAttempt(){
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).perform(clearText(),typeText("testUser"));
        onView(withId(R.id.username)).check(matches(isDisplayed()));


        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).perform(clearText(),typeText("test123@gmail.com"));
        onView(withId(R.id.email)).check(matches(isDisplayed()));


        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).perform(clearText(),typeText("password"));
        onView(withId(R.id.password)).check(matches(isDisplayed())).perform(closeSoftKeyboard());


        onView(withId(R.id.design_pref)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Athletic"))).perform(click());  // select "Shoes"

        onView(withId(R.id.store_pref)).perform(click());            //Assert "Shoes is selected
        onData(allOf(is(instanceOf(String.class)), is("Zara"))).perform(click());  // select "Shoes"

        //check to see if a new intent is launched
        Intents.init();

        Intents.release();
    }
}
