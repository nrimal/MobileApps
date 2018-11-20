package edu.osu.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class ClosetTest {
    private final String TAG = super.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<ClosetActivity> mActivity =
            new ActivityTestRule(ClosetActivity.class);


    private ClosetFragment mFragment; // Fragment to be tested
    //private ImageView mImage; // Member variable of activity
    private Button Shoes,Pants;
    private LinearLayout ShoesLayout,PantsLayout;
    @Before
    public void createAddClosetItemActivity() {
        mFragment = new ClosetFragment();

        //spinnerType=mFragment.getView().findViewById(R.id.spinner1);
        //spinnerSubType=mFragment.getView().findViewById(R.id.spinnerSubType);
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
    public void ShowHide_Shoes_isCorrect() {

        onView(withId(R.id.showShoes)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ShowHideShoes)).perform(click()); //click on
        onView(withId(R.id.showShoes)).check(matches(isDisplayed()));
    }

    @Test
    public void ShowHide_Pants_isCorrect() {

        onView(withId(R.id.showPants)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ShowHidePants)).perform(click()); //click on
        onView(withId(R.id.showPants)).check(matches(isDisplayed()));
    }

    @Test
    public void ShowHide_TShirt_isCorrect() {

        onView(withId(R.id.showTShirts)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ShowHideTShirts)).perform(click()); //click on
        onView(withId(R.id.showTShirts)).check(matches(isDisplayed()));
    }

    @Test
    public void ShowHide_Jacket_isCorrect() {

        onView(withId(R.id.showJackets)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ShowHideJackets)).perform(click()); //click on
        onView(withId(R.id.showJackets)).check(matches(isDisplayed()));
    }

    @Test
    public void ShowHide_Shirt_isCorrect() {

        onView(withId(R.id.showShirts)).check(matches(not(isDisplayed())));
        onView(withId(R.id.ShowHideShirts)).perform(click()); //click on
        onView(withId(R.id.showShirts)).check(matches(isDisplayed()));
    }
}