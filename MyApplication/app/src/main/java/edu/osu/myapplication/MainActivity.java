package edu.osu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

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
        getMenuInflater().inflate(R.menu.single_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        View menuItemView = findViewById(R.id.single_menu); // SAME ID AS MENU ID
        PopupMenu popupMenu = new PopupMenu(this, menuItemView);
        popupMenu.inflate(R.menu.menu_preferences);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_activity:
                        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        currentRunning = homeIntent;
                        startActivity(homeIntent);
                        return true;
                    case R.id.calender:
                        Intent calendarIntent = new Intent(MainActivity.this, CalendarActivity.class);
                        calendarIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(calendarIntent);
                        return true;
                    case R.id.closet:
                        Intent closetIntent = new Intent(MainActivity.this, ClosetActivity.class);
                        closetIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        currentRunning = closetIntent;
                        startActivity(closetIntent);
                        return true;
                    case R.id.account:
                        Intent accountIntent = new Intent(MainActivity.this, PreferencesActivity.class);
                        accountIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        currentRunning = accountIntent;
                        startActivity(accountIntent);
                        return true;
                    default:
                        return true;
                }
            }
        });
        popupMenu.show();
        return true;

    }
    private void killRunningActivity(){
//        currentRunning.stack
    }
}