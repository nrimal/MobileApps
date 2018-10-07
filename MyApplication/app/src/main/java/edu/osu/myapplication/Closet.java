package edu.osu.myapplication;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;


public class Closet extends AppCompatActivity {

    private static final String TAG = "Closet_Activity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.activity_closet_fragment);
        if(fragment==null) {
            fragment = new Closet_Fragment();
            manager.beginTransaction()
                    .add(R.id.activity_closet_fragment, fragment)
                    .commit();
        }
        Log.d(TAG, "onCreate(Bundle) called");
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
