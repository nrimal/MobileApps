package edu.osu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Closet_Item_Activity extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private Button btnSubmit;

    private static final String TAG = "Add_Closet_Item_Act";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.activity_closet_fragment);
        if(fragment==null) {
            fragment = new Add_Closet_Item_Fragment();
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
