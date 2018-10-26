package edu.osu.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class PreferencesActivity extends HomeActivity {
    private final String TAG = getClass().getSimpleName();

    protected Fragment createFragment() {
        return new PreferencesFragment();
    }

    @Override
    @SuppressLint("LogNotTimber")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Log.d(TAG, "onCreate(Bundle) called");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            Fragment preferenceFragment = createFragment();
            fm.beginTransaction().replace(R.id.fragment_container, preferenceFragment).commit();
        }

        PreferenceManager.setDefaultValues(this, R.xml.accountpreferences, false);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
