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

public class AddEventActivity extends AppCompatActivity {
    private String TAG = AddEventActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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
            Fragment fragment = manager.findFragmentById(R.id.add_event);

            if (fragment == null) {
                fragment = new AddEventFragment();
                manager.beginTransaction()
                        .add(R.id.add_event, fragment)
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

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
