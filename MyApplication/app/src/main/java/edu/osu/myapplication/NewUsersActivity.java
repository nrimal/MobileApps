package edu.osu.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

        if (!isNetworkAvailable()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("No Internet Accesss");
            dialog.setMessage("You currently don't have internet access. Please try again when you are connected to the internet.")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            Fragment newUser = manager.findFragmentById(R.id.new_users_Fragment);
            if (newUser == null) {
                newUser = new NewUsersFragment();
                manager.beginTransaction()
                        .add(R.id.new_users_Fragment, newUser)
                        .commit();
            }
        }
        Log.d(TAG, "onCreate(Bundle) called");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
