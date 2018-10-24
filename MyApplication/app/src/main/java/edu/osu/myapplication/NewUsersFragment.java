package edu.osu.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

import static android.support.constraint.Constraints.TAG;


public class NewUsersFragment extends Fragment implements View.OnClickListener {

    private Button mRegisterBtn;
    private Spinner mStorePref, mDesignPref;
    private EditText mUsername,mEmail, mPassword;
    private FirebaseAuth mAuth;

    private DatabaseReference dbRef;
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View  v){
        switch(v.getId()) {
            case R.id.registerBtn:
                if(areValuesValid()){
                    createAccount();
                }
                break;
        }
    }

    public boolean areValuesValid(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        Pattern emailValid = Patterns.EMAIL_ADDRESS;
        if(password.length()!=0 && emailValid.matcher(email).matches()){
            return true;
        }
        if(password.length()<4){
            mPassword.setError(getString(R.string.error_invalid_password));
        }
        if( emailValid.matcher(email).matches()) {
            mEmail.setError(getString(R.string.invalid_email));
        }
        return false;
    }
    public void createAccount(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
//            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verifyEmailButton).setEnabled(!user.isEmailVerified());
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
//            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
//        }
    }

}
