package edu.osu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class CalendarFragment extends Fragment {
    private static final String TAG = CalendarFragment.class.getSimpleName();
    private CalendarView mCalendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, this + ": onCreateView()");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        mCalendarView = v.findViewById(R.id.mycalendar);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent eventList = new Intent(getActivity(), EventListActivity.class);
                eventList.putExtra("year", year);
                eventList.putExtra("month", month);
                eventList.putExtra("dayOfMonth", dayOfMonth);
                startActivity(eventList);
            }
        });

        return v;
    }
}
