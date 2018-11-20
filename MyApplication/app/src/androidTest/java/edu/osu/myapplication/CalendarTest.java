package edu.osu.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class CalendarTest {
    private final String TAG = super.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<CalendarActivity> mActivity =
            new ActivityTestRule(CalendarActivity.class);


    private CalendarFragment mFragment; // Fragment to be tested

    @Before
    public void createCalendarActivity() {
        mFragment = new CalendarFragment();
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
    public void Calendar_Date_to_Event_Succeeds() {
        onView(isRoot()).perform(pressKey(KeyEvent.KEYCODE_TAB),pressKey(KeyEvent.KEYCODE_TAB),pressKey(KeyEvent.KEYCODE_TAB),pressKey(KeyEvent.KEYCODE_ENTER));
    }

    @Test
    public void Calendar_Month_Cycle_Succeeds() {
        onView(isRoot()).perform(pressKey(KeyEvent.KEYCODE_TAB),pressKey(KeyEvent.KEYCODE_TAB),pressKey(KeyEvent.KEYCODE_ENTER),pressKey(KeyEvent.KEYCODE_ENTER),pressKey(KeyEvent.KEYCODE_ENTER));
    }

}
