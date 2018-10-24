package edu.osu.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.constraint.Constraints.TAG;

public class FirebaseHelper {

    private static FirebaseDatabase firebaseInstance= null;
    private static DatabaseReference DBreference;

    public static FirebaseDatabase getInstance(){
        if(firebaseInstance == null){
            firebaseInstance = FirebaseDatabase.getInstance();
        }
        return firebaseInstance;
    }

    public static DatabaseReference setReference(String reference){
        if(firebaseInstance==null){
            getInstance();
        }
        DBreference = firebaseInstance.getReference(reference);
        return DBreference;
    }

    public void readData(String reference){
        // Read from the database
        DatabaseReference DBreferenceTemp = firebaseInstance.getReference(reference);

        DBreferenceTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
