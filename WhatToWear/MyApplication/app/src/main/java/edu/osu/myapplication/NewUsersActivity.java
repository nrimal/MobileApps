package edu.osu.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class NewUsersActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("New Users");
        setContentView(R.layout.activity_new__users);
        FragmentManager manager= getSupportFragmentManager();
        Fragment newUser = manager.findFragmentById(R.id.new_users_Fragment);
        if(newUser == null){
            newUser = new NewUsersFragment();
            manager.beginTransaction()
                    .add(R.id.new_users_Fragment,newUser)
                    .commit();
        }
        Log.d(TAG, "onCreate(Bundle) called");
    }
}
