package edu.osu.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.osu.myapplication.Data.Clothing;
import edu.osu.myapplication.Data.DataConstants;


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

    private int temperature;
    private int temperaturePref;
    private String weatherType;
    private String userDesignPref;
    private String nextEventCategory;

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
        setNextEventCategory();
        giveSuggestion();

        return v;
    }

    public void giveSuggestion(){
        clothingDBRetreive.clear();

        if (getNextEventCategoryTop() == "Shirt") {
            getImageData(DataConstants.SHIRTS);
        }
        else {
            getImageData(DataConstants.TSHIRTS);
        }

        getImageData(DataConstants.PANTS);
        getImageData(DataConstants.SHOES);

        Toast val = Toast.makeText(getActivity(), "Generating new suggestion...", Toast.LENGTH_LONG);
        val.setGravity(Gravity.CENTER,0,0);
        val.show();
    }
    private void getImageData(final int num){

        switch (num){
            case DataConstants.SHIRTS: //get Shirt
                currRef.child("Shirt").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            List<Clothing> shirts = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                shirts.add(child.getValue(Clothing.class));
                            }

                            if (shirts.size() > 0) {
                                clothingDBRetreive.add(shirts.get(rand.nextInt(shirts.size())));
                                mAdapter = new ImageAdapter(getActivity(), clothingDBRetreive);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        }

                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case DataConstants.TSHIRTS: //get T-Shirt
                currRef.child("T_Shirt").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            List<Clothing> top = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                top.add(child.getValue(Clothing.class));
                            }

                            if (top.size() > 0) {
                                clothingDBRetreive.add(top.get(rand.nextInt(top.size())));
                                mAdapter = new ImageAdapter(getActivity(), clothingDBRetreive);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        }

                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case DataConstants.PANTS://get Pants
                currRef.child("Pants").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            List<Clothing> pants = new ArrayList<>();
                            String type;

                            if (temperature > 65 - ((temperaturePref - 3) * 2)) {
                                type = "Shorts";
                            } else {
                                type = "Pants";
                            }

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    clothingItem = child.getValue(Clothing.class);
                                    //if (clothingItem.SubType.contains(type)) {
                                        pants.add(clothingItem);
                                        break;
                                    //}
                            }

                            int count = 0;
                            while (count < pants.size()) {
                                int index = rand.nextInt(pants.size());
                                Clothing pantsSelection = pants.get(index);
                                if (true /*pantsSelection.SubType.equals(getNextEventCategoryPants(type))*/) {
                                    clothingDBRetreive.add(pantsSelection);
                                    break;
                                }

                                count++;
                            }
                            mAdapter = new ImageAdapter(getActivity(), clothingDBRetreive);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        mProgressCircle.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case DataConstants.SHOES://get random other vals
                currRef.child("Shoes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()) {
                            List<Clothing> shoes = new ArrayList<>();
                            String weather;

                            if (temperature > 65 - ((temperaturePref - 3) * 2)) {
                                weather = "warm";
                            } else {
                                weather = "cold";
                            }

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                shoes.add(child.getValue(Clothing.class));
                            }

                            int count = 0;
                            while (count < shoes.size()) {
                                int index = rand.nextInt(shoes.size());
                                Clothing shoesSelection = shoes.get(index);
                                if (true /*shoessSelection.SubType.equals(getNextEventCategoryShoes(weather))*/) {
                                    clothingDBRetreive.add(shoesSelection);
                                    break;
                                }

                                count++;
                            }

                            mAdapter = new ImageAdapter(getActivity(), clothingDBRetreive);
                            mRecyclerView.setAdapter(mAdapter);
                        }
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

    public void setNextEventCategory() {
        nextEventCategory = "Professional";
    }

    private String getNextEventCategoryPants(String type) {
        String value;
        switch (nextEventCategory) {
            case "Professional":
                value = "Dress Pants";
                break;
            case "Business Casual":
            case "Romantic":
                value = ((type.equals("Pants") ? "Chino Pants" : "Chino Shorts"));
                break;
            case "Athletic":
                value = ((type.equals("Pants") ? "Athletic Pants" : "Athletic Shorts"));
                break;
            default:
                value = ((type.equals("Pants") ? "Jeans" : "Chino Shorts"));
                break;
        }

        return value;
    }

    private String getNextEventCategoryShoes(String weather) {
        String value;
        switch (nextEventCategory) {
            case "Professional":
                value = "Dress Shoes";
                break;
            case "Business Casual":
            case "Romantic":
                value = ((weather.equals("Cold") ? "Boots" : "Sandals"));
                break;
            case "Athletic":
                value = "Athletic Shoes";
                break;
            default:
                value = ((weather.equals("Cold") ? "Boots" : "Athletic Shoes"));
                break;
        }

        return value;
    }

    private String getNextEventCategoryTop() {
        String value;
        switch (nextEventCategory) {
            case "Professional":
            case "Business Casual":
            case "Romantic":
                value = "Shirt";
                break;
            default:
                value = "TShirt";
                break;
        }

        return value;
    }


}
