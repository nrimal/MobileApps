package edu.osu.myapplication;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreferencesActivityFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    private EditText username;
    private EditText email;
    private EditText password;
    private Spinner desgin_prefs;
    private Spinner store_prefs;
    private Switch closet_only;
    private SeekBar temp_pref;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preferences, container, false);
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");

        try {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            if (activity != null) {
                ActionBar actionBar = activity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setSubtitle("Preferences");
                }
            }
        }
        catch (NullPointerException err) {
            Log.d(TAG, "Could not set subtitle");
        }
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_changes:
                updateAccountPreferences();
                break;
            case R.id.btn_cancel:
                username.setText("");
                email.setText("");
                password.setText("");
                desgin_prefs.setPrompt("");
                store_prefs.setPrompt("");
                closet_only.setChecked(false);
                break;
        }
    }

    private void updateAccountPreferences() {
        String user_name = username.getText().toString();
        String e_mail = email.getText().toString();
        String pass = password.getText().toString();
        String style = desgin_prefs.getPrompt().toString();
        String store = store_prefs.getPrompt().toString();
        boolean just_closet = closet_only.isChecked();
    }
}
