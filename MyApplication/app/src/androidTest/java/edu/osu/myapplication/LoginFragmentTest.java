package edu.osu.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity mLoginActivity; // Activity to be tested
    private LoginFragment mLoginFragment; // Fragment to be tested

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.osu.myapplication", appContext.getPackageName());
    }

    protected void setUp() throws Exception { // Access member variables
        //super.setUp();
        mLoginActivity = getCallingActivity();
        mLoginFragment = new LoginFragment();
        mLoginActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mLoginFragment, null)
                .commit();
    }
    // ...
    protected void tearDown() throws Exception { // cleans up
        mLoginActivity.finish();
        //super.tearDown();
    }

    @Test
    public void testPreconditions() {
        assertNotNull(mLoginActivity);
        assertNotNull(mLoginFragment);
    }
}
