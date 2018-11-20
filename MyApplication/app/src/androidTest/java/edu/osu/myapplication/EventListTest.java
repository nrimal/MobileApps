package edu.osu.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class EventListTest {
    private final String TAG = super.getClass().getSimpleName();

    @Rule
    public ActivityTestRule<EventListActivity> mActivity =
            new ActivityTestRule(EventListActivity.class);


    private EventListFragment mFragment; // Fragment to be tested

    @Before
    public void createEventListActivity() {
        mFragment = new EventListFragment();
    }
        /*=====================================   Test Cases Here   =====================================*/

    /*===== Required =====*/
    @Test
    public void testPreconditions() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
    }
}
