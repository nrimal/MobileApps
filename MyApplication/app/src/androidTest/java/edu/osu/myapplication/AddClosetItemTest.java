package edu.osu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class AddClosetItemTest {
    private final String TAG = super.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<AddClosetItemActivity> mActivity =
            new ActivityTestRule(AddClosetItemActivity.class);


    private AddClosetItemFragment mFragment; // Fragment to be tested

    @Before
    public void createAddClosetItemActivity() {
        mFragment = new AddClosetItemFragment();
    }
        /*=====================================   Test Cases Here   =====================================*/

    /*===== Required =====*/
    @Test
    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
    }

    /*===== Other =====*/

    @Test
    public void SubType_Default_isCorrect() {
        onView(withId(R.id.spinner1)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Shoes"))).perform(click());  // select "Shoes"

        onView(withId(R.id.spinner1)).check(matches(withSpinnerText("Shoes")));            //Assert "Shoes is selected
        onView(withId(R.id.spinnerSubType)).check(matches(withSpinnerText("Sandals")));    //Assert "SubType is "Sandals"
    }
    @Test
    public void SubType_Pants_isCorrect() {
        onView(withId(R.id.spinner1)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Pants"))).perform(click());  // select "Pants"

        onView(withId(R.id.spinner1)).check(matches(withSpinnerText("Pants")));            //Assert "Pants is selected
        onView(withId(R.id.spinnerSubType)).check(matches(withSpinnerText("Athletic Shorts")));    //Assert "SubType is "Athletic Shorts"
    }
    @Test
    public void SubType_Shirts_isCorrect() {
        onView(withId(R.id.spinner1)).perform(click()); //click on spinner 1
        onData(allOf(is(instanceOf(String.class)), is("Shirt"))).perform(click());  // select "Shirt"

        onView(withId(R.id.spinner1)).check(matches(withSpinnerText("Shirt")));            //Assert "Shirt is selected
        onView(withId(R.id.spinnerSubType)).check(matches(withSpinnerText("None")));    //Assert "SubType is "None"
    }


}
