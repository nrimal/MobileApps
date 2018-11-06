package edu.osu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import edu.osu.myapplication.Data.Events;

public class AddEventFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = AddEventFragment.class.getSimpleName();
    private EditText mEditText;
    private Spinner mSpinner;
    private TimePicker mTimePicker;
    private FirebaseAuth mAuth;
    private String Username;
    private DatabaseReference database;
    private Map<String, Object> map;
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, this + ": onCreateView()");
        View v = inflater.inflate(R.layout.fragment_add_event, container, false);

        mEditText = v.findViewById(R.id.add_event_name_edittext);
        mSpinner = v.findViewById(R.id.add_event_category_spinner);
        mTimePicker = v.findViewById(R.id.add_event_timepicker);

        Button addEventBtn = v.findViewById(R.id.add_event_btn);
        addEventBtn.setOnClickListener(this);

        // Setup firebase connection
        mAuth = FirebaseAuth.getInstance();
        Username = mAuth.getCurrentUser().getUid()+"";
        database = FirebaseDatabase.getInstance().getReference().child("users").child(Username).child("Calendar");

        map = new HashMap<>();

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_event_btn:
                Bundle extras = getActivity().getIntent().getExtras();
                int year = extras.getInt("year");
                int month = extras.getInt("month");
                int dayOfMonth = extras.getInt("dayOfMonth");
                int hour = mTimePicker.getHour();
                int minute = mTimePicker.getMinute();

                Calendar calendar = new GregorianCalendar();
                calendar.set(year, month, dayOfMonth, hour, minute);

                // Create new event
                Events newEvent = new Events(mEditText.getText().toString(),
                        mSpinner.getSelectedItem().toString(), calendar);

                // Store event in firebase
                String date = format1.format(calendar.getTime());
                map.put("dateKey", date);
                map.put("eventTitleKey", newEvent.getEventName());
                map.put("eventCategoryKey", newEvent.getCategoryID());
                map.put("hourKey", hour);
                map.put("minuteKey", minute);
                database.child(date).child(hour + "" + minute).setValue(map);

                getActivity().finish();
                break;
        }

    }
}
