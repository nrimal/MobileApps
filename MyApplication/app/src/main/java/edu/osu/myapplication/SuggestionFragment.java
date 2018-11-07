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
    ImageView firstImage, secondImage, thirdImage;
    List<ImageView> imageContainer;
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference currRef;
    private Random rand;
    private Clothing clothingItem;



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
        imageContainer = new ArrayList<>();
        View v=  inflater.inflate(R.layout.fragment_suggestion, container, false);

//        firstImage = v.findViewById(R.id.first_image_suggestion);
//        secondImage = v.findViewById(R.id.second_image_suggestion);
//        thirdImage = v.findViewById(R.id.third_image_suggestion);

//////////////////////

        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clothing = new ArrayList<>();
////////////////////////
        imageContainer.add(firstImage);
        imageContainer.add(secondImage);
        imageContainer.add(thirdImage);
        rand = new Random();
        giveSuggestion();

        return v;
    }

    public void giveSuggestion(){

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
                                break;
                            }
                            count++;
                        }
                        Bitmap bitmap = null;
                        try {
                            if (clothingItem != null && clothingItem.Image != null && !clothingItem.Image.equals("null")) {
                                Uri suggestionSelected = Uri.parse(clothingItem.Image);
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), suggestionSelected);
                                imageContainer.get(num).setImageBitmap(bitmap);
                            }
                        }catch (IOException e){
                            Log.d(TAG, "IO exeption when coverting string to image file");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
                                break;
                            }
                            count++;
                        }
                        Bitmap bitmap = null;
                        try {
                            if (clothingItem != null && clothingItem.Image != null) {
                                Uri suggestionSelected = Uri.parse(clothingItem.Image);
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), suggestionSelected);
                                imageContainer.get(num).setImageBitmap(bitmap);
                            }
                        }catch (IOException e){
                            Log.d(TAG, "IO exeption when coverting string to image file");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
                                break;
                            }
                            count++;
                        }

                        Bitmap bitmap = null;
                        try {
                            if (clothingItem != null && clothingItem.Image != null) {
                                Uri suggestionSelected = Uri.parse(clothingItem.Image);
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), suggestionSelected);
                                imageContainer.get(num).setImageBitmap(bitmap);
                            }
                        }catch (IOException e){
                            Log.d(TAG, "IO exeption when coverting string to image file");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
