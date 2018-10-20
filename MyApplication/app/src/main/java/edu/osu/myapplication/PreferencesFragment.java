package edu.osu.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreferencesFragment extends PreferenceFragmentCompat {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkKey) {
        addPreferencesFromResource(R.xml.accountpreferences);
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onResume() {
        super.onResume();
        try {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            if (activity != null) {
                ActionBar actionBar = activity.getSupportActionBar();

                if (actionBar != null) {
                    actionBar.setSubtitle("Account Preferences");
                }
            }
        } catch (NullPointerException err) {
            Log.e(TAG, "Could not set subtitle");
        }
    }
}
