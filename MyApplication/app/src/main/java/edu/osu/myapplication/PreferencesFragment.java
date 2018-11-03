package edu.osu.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreferencesFragment extends PreferenceFragmentCompat {
    private final String TAG = getClass().getSimpleName();

    private FirebaseAuth mAuth;
    String Username;
    FirebaseDatabase database;
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkKey) {
        addPreferencesFromResource(R.xml.accountpreferences);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
    @Override
    public void onPause(){
        super.onPause();

        mAuth = FirebaseAuth.getInstance();
        Username = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        //EditTextPreference mPreferenceEmail = (EditTextPreference) getPreferenceScreen().findPreference("email");

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String email = mSharedPreferences.getString("email","HH@gmail.com");
        Log.d(TAG,email + " ==================================================================");
        DatabaseReference myRef = database.getReference("users/"+Username);
        myRef.child("email").setValue(email);


        ;

    }
}
