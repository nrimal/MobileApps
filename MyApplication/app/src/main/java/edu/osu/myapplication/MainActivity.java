package edu.osu.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent currentRunning;
    private String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate(Bundle) called");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preferences, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

//        View menuItemView = findViewById(R.id.)

        switch (item.getItemId()) {
            case R.id.home_activity:
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                currentRunning = homeIntent;
                startActivity(homeIntent);
                return true;
            case R.id.calender:
                return true;
            case R.id.closet:
                Intent closetIntent = new Intent(MainActivity.this, ClosetActivity.class);
                currentRunning = closetIntent;
                startActivity(closetIntent);
                return true;
            case R.id.account:
                Intent accountIntent = new Intent(MainActivity.this, PreferencesActivity.class);
                currentRunning = accountIntent;
                startActivity(accountIntent);
                return true;
            default:
                return true;
        }
    }

//    public void showPopup(View v){
//        PopupMenu popup = new PopupMenu(this, v);
//
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item){
//
//    }
    private void killRunningActivity(){
//        currentRunning.stack
    }
}
