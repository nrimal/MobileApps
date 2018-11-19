package edu.osu.myapplication;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    private static final String TAG = "LoginFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.lm_fragment, container, false);

        mUsernameView = v.findViewById(R.id.username);
        mPasswordView = v.findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        Button mUsernameRegisterButton = v.findViewById(R.id.username_register_button);
        mUsernameRegisterButton.setOnClickListener(this);

        Button mUsernameSignInButton = v.findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(this);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){ //if user is already signed in go to homepage
            Intent homePage = new Intent(getActivity(), HomeActivity.class);
            startActivity(homePage);
        }
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
        mAuth = FirebaseAuth.getInstance();
//        setRetainInstance(true);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.username_sign_in_button:
                  attemptSignIn();
//                Intent ClosetIntent = new Intent(getActivity(),Closet.class);
//                startActivity(ClosetIntent);
//                Intent homeActivity = new Intent(getActivity(), HomeActivity.class);
//                startActivity(homeActivity);
//                Intent ClosetIntent = new Intent(getActivity(),ClosetActivity.class);
//                startActivity(ClosetIntent);

                break;
            case R.id.username_register_button:
//                Intent PreferencesIntent = new Intent(getActivity(), PreferencesActivity.class);
//                startActivity(PreferencesIntent);
                Intent newUser = new Intent(getActivity(), NewUsersActivity.class);
                startActivity(newUser);
                break;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignIn() {
        if (mAuth == null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            attemptLogin(username,password);
        }
    }

    public void attemptLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent homeActivity = new Intent(getActivity(), HomeActivity.class);
                            startActivity(homeActivity);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed, please check if email and password are correct", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length()>4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


}
