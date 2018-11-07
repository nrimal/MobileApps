package edu.osu.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.osu.myapplication.Data.Clothing;


public class SuggestionFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference currRef;
    private Random rand;
    private Clothing clothingItem;
    List<Clothing> clothingDBRetreive;
    private ProgressBar mProgressCircle;

    private List<Clothing> clothing;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(db==null){
            db =  FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            currRef = db.getReference("users/"+currentUser.getUid()+"/Closet");
        }


        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                giveSuggestion();
                Log.d(TAG, "Device shaken");
            }
        });

        Log.d(TAG, "onCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v=  inflater.inflate(R.layout.fragment_suggestion, container, false);

        mProgressCircle = v.findViewById(R.id.progress_circle);

        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        clothing = new ArrayList<>();
        clothingDBRetreive = new ArrayList<>();

        rand = new Random();
        giveSuggestion();

        return v;
    }

    public void giveSuggestion(){
        clothingDBRetreive.clear();
        for(int i=0; i<3; i++){
//          getImageData(i);

        }
        Toast val = Toast.makeText(getActivity(), "Device shook!", Toast.LENGTH_LONG);
        val.setGravity(Gravity.CENTER,0,0);
        val.show();
    }
    private void getImageData(final int num){

        switch (num){
            case 0: //get Shirt
                currRef.child("Shoes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int randomIndex = rand.nextInt((int)dataSnapshot.getChildrenCount());
                        int count=0;

                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(count==randomIndex){
                                clothingItem = child.getValue(Clothing.class);
                                clothingDBRetreive.add(clothingItem);
                                break;
                            }
                            count++;
                        }
                        mAdapter = new ImageAdapter(getActivity(),clothingDBRetreive);
                        mRecyclerView.setAdapter(mAdapter);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 1://get Pants
                currRef.child("Shoes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int randomIndex = rand.nextInt((int)dataSnapshot.getChildrenCount());
                        int count=0;

                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(count==randomIndex){
                                clothingItem = child.getValue(Clothing.class);
                                clothingDBRetreive.add(clothingItem);
                                break;
                            }
                            count++;
                        }
                        mAdapter = new ImageAdapter(getActivity(),clothingDBRetreive);
                        mRecyclerView.setAdapter(mAdapter);
                        mProgressCircle.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 2://get random other vals
                currRef.child("Shoes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int randomIndex = rand.nextInt((int)dataSnapshot.getChildrenCount());
                        int count=0;

                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(count==randomIndex){
                                clothingItem = child.getValue(Clothing.class);
                                clothingDBRetreive.add(clothingItem);
                                break;
                            }
                            count++;
                        }
                        mAdapter = new ImageAdapter(getActivity(),clothingDBRetreive);
                        mRecyclerView.setAdapter(mAdapter);
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
               break;
            default:
                Log.d(TAG, "wrong argument passed in getImageData, :"+num);
        }
    }
    @Override
    public void onPause(){
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        Log.d(TAG, "onResume called");
    }

}
