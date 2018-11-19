package edu.osu.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreferencesFragment extends PreferenceFragmentCompat {
    private final String TAG = getClass().getSimpleName();

    private FirebaseAuth mAuth;
    String UUID;
    String Username;
    String Email;
    String Password;
    String Design;
    Set<String> Store;
    int Temp;
    FirebaseDatabase database;
    SharedPreferences mSharedPreferences;

    EditTextPreference PrefUsername;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkKey) {
        addPreferencesFromResource(R.xml.accountpreferences);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mAuth = FirebaseAuth.getInstance();
        UUID = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();



        DatabaseReference myRef = database.getReference("users/"+UUID);

        myRef = database.getReference("users/"+UUID+"/userName");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Username = (String) dataSnapshot.getValue();
                ((EditTextPreference) findPreference("username")).setText(Username);
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Username="";
            }
        });
        myRef = database.getReference("users/"+UUID+"/pereferencePk");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Design = (String) dataSnapshot.getValue();
                 ((ListPreference) findPreference("designPrefs")).setValue(Design);

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
        myRef = database.getReference("users/"+UUID+"/storePreferencePk");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Store = new HashSet<String>();
                if(dataSnapshot.getValue()==null){return;}
                Store.addAll((List<String>)dataSnapshot.getValue());
                Store = mSharedPreferences.getStringSet("storePrefs",Store);
                ((MultiSelectListPreference) findPreference("storePrefs")).setValues(Store);
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Store = new HashSet<String>();
            }
        });


        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        Email = mSharedPreferences.getString("email",mAuth.getCurrentUser().getEmail());
        ((EditTextPreference) findPreference("email")).setText(Email);
        Password = mSharedPreferences.getString("password","");
        Temp =  mSharedPreferences.getInt("tempPref",3);

        mSharedPreferences.edit().apply();
        mSharedPreferences.edit().commit();

    }

    @Override
    @SuppressLint("LogNotTimber")
    public void onResume() {
        super.onResume();
        try {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            if (activity != null) {
                ActionBar actionBar = activity.getSupportActionBar();

                if (actionBar != null) {
                    actionBar.setSubtitle("Account Preferences");
                }
            }
        } catch (NullPointerException err) {
            Log.e(TAG, "Could not set subtitle");
        }
    }
    @Override
    public void onPause(){
        super.onPause();

        //display current

        DatabaseReference myRef = database.getReference("users/"+UUID);
        //Log.d(TAG,Email + " ==================================================================");
        if(Username!=null && !Username.equals("")){myRef.child("userName").setValue(Username);}
        if(Email!=null && !Email.equals("")){myRef.child("email").setValue(Email);
        mAuth.getCurrentUser().updateEmail(Email);}
        myRef.child("pereferencePk").setValue(Design);
        List<String> StorePref =new ArrayList<>();
        if(Store!=null){StorePref.addAll(Store);}
        myRef.child("storePreferencePk").setValue(StorePref);
        if(Password!=null &&!Password.equals("")){mAuth.getCurrentUser().updatePassword(Password);}
        //Base64.encode();
               // Base64.decode();

    }
}
