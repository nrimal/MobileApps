package edu.osu.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class new_users_Fragment extends Fragment implements View.OnClickListener {

    private Button mRegisterBtn;
    private Spinner mStorePref, mDesignPref;
    private EditText mUsername,mEmail, mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_users_, container, false);
        mRegisterBtn = v.findViewById(R.id.registerBtn);
        mStorePref = v.findViewById(R.id.store_pref);
        mDesignPref = v.findViewById(R.id.design_pref);
        mUsername = v.findViewById(R.id.username);
        mEmail = v.findViewById(R.id.email);
        mPassword = v.findViewById(R.id.password);

        mRegisterBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View  v){
        switch(v.getId()) {
            case R.id.registerBtn:
                if(areValuesValid()){
                    //go to main page/home page
                }
                break;
        }
    }

    public boolean areValuesValid(){

        return true;
    }

}
