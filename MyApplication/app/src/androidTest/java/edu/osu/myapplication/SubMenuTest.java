package edu.osu.myapplication;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;


/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SubMenuTest {


    //getActivity

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule2 = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testHomeButtonMenu(){

        onView(withId(R.id.single_menu)).check(matches(isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.single_menu)).perform(ViewActions.click());

        //check to see if a new intent is launched
        Intents.init();

        onData(anything())
                .atPosition(0)
                .perform(click());

        Intents.intended(hasComponent(HomeActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void testCalendarButtonMenu(){

        onView(withId(R.id.single_menu)).check(matches(isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.single_menu)).perform(ViewActions.click());

        //check to see if a new intent is launched
        Intents.init();

        onData(anything())
                .atPosition(1)
                .perform(click());

        Intents.intended(hasComponent(CalendarActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void testClosetButtonMenu(){

        onView(withId(R.id.single_menu)).check(matches(isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.single_menu)).perform(ViewActions.click());
        //check to see if a new intent is launched
        Intents.init();

        onData(anything())
                .atPosition(2)
                .perform(click());

        Intents.intended(hasComponent(ClosetActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testAccountButtonMenu(){

        onView(withId(R.id.single_menu)).check(matches(isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.single_menu)).perform(ViewActions.click());
        //check to see if a new intent is launched
        Intents.init();
        onData(anything())
                .atPosition(3)
                .perform(click());

        Intents.intended(hasComponent(PreferencesActivity.class.getName()));
        Intents.release();
    }

}
