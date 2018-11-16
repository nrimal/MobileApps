package edu.osu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

public class EventListActivity extends MainActivity {
    private String TAG = EventListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.event_list);

        if (fragment == null) {
            fragment = new EventListFragment();
            manager.beginTransaction()
                    .add(R.id.event_list, fragment)
                    .commit();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.bringToFront();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Intent addEvent = new Intent(EventListActivity.this, AddEventActivity.class);
                addEvent.putExtra("year", extras.getInt("year"));
                addEvent.putExtra("month", extras.getInt("month"));
                addEvent.putExtra("dayOfMonth", extras.getInt("dayOfMonth"));
                addEvent.putExtra("changeEvent", 0);
                startActivity(addEvent);
            }
        });

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
