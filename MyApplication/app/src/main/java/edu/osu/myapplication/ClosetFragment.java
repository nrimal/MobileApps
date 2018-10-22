package edu.osu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ClosetFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Closet_Frag_Activity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.activity_closet_fragment, container, false);


        Button mUsernameRegisterButton = v.findViewById(R.id.btnAddItem);
        mUsernameRegisterButton.setOnClickListener(this);



        return v;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
//        setRetainInstance(true);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAddItem:
//                attemptLogin();
                Intent ClosetIntent = new Intent(getActivity(),AddClosetItemActivity.class);
                startActivity(ClosetIntent);
                break;
        }
    }
}

