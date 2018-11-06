package edu.osu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EventListFragment extends Fragment {
    private static final String TAG = EventListFragment.class.getSimpleName();
    private ListView mListView;
    private FirebaseAuth mAuth;
    private String Username;
    private DatabaseReference database;
    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, this + ": onCreateView()");
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);

        mListView = v.findViewById(R.id.listOfEvents);

        Bundle extras = getActivity().getIntent().getExtras();
        int year = extras.getInt("year");
        int month = extras.getInt("month") + 1;
        int dayOfMonth = extras.getInt("dayOfMonth");
        final String date = String.format("%04d%02d%02d", year, month, dayOfMonth);

        mAuth = FirebaseAuth.getInstance();
        Username = mAuth.getCurrentUser().getUid()+"";
        database = FirebaseDatabase.getInstance().getReference().child("users").child(Username).child("Calendar");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mArrayList);
        mListView.setAdapter(arrayAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, dataSnapshot.toString());
                mArrayList.clear();

                for (DataSnapshot dateSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, dateSnapshot.toString());
                    String dateChild = dateSnapshot.getKey();
                    if (dateChild.equals(date)) { // Only display events for selected date
                        HashMap<String, String> map;
                        for (DataSnapshot timeItem : dateSnapshot.getChildren()) {
                            Log.d(TAG, timeItem.toString());
                            map = (HashMap<String, String>) timeItem.getValue();
                            mArrayList.add(map.get("eventTitleKey"));
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        // Inflate the layout for this fragment
        return v;
    }
}
