package edu.osu.myapplication;

import android.accounts.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class HomeActivity extends AppCompatActivity {
    private Intent currentRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        switch (item.getItemId()) {
            case R.id.home_activity:
                killRunningActivity();
                return true;
            case R.id.calender:
                return true;
            case R.id.closet:
                Intent closetIntent = new Intent(HomeActivity.this, ClosetActivity.class);
                currentRunning = closetIntent;
                startActivity(closetIntent);
                return true;
            case R.id.account:
                Intent accountIntent = new Intent(HomeActivity.this, PreferencesActivity.class);
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
