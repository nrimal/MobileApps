package edu.osu.myapplication;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import edu.osu.myapplication.Data.Clothing;

public class ClosetFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Closet_Frag_Activity";

    private FirebaseAuth mAuth;
    String Username;
    FirebaseDatabase database;

    LinearLayout ShoesItem;
    LinearLayout PantsItem;
    LinearLayout TShirtItem;
    LinearLayout JacketItem;
    LinearLayout ShirtItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.activity_closet_fragment, container, false);


        Button additem = v.findViewById(R.id.btnAddItem);
        additem.setOnClickListener(this);

        Button ShowHideShoes = v.findViewById(R.id.ShowHideShoes);
        ShowHideShoes.setOnClickListener(this);

        Button ShowHidePants = v.findViewById(R.id.ShowHidePants);
        ShowHidePants.setOnClickListener(this);

        Button ShowHideTShirts = v.findViewById(R.id.ShowHideTShirts);
        ShowHideTShirts.setOnClickListener(this);

        Button ShowHideJacket = v.findViewById(R.id.ShowHideJackets);
        ShowHideJacket.setOnClickListener(this);

        Button ShowHideShirts = v.findViewById(R.id.ShowHideShirts);
        ShowHideShirts.setOnClickListener(this);

        //Username = FirebaseInstanceId.getInstance().getId()+"";
        mAuth = FirebaseAuth.getInstance();
        Username = mAuth.getCurrentUser().getUid()+"";
        database = FirebaseDatabase.getInstance();

        ShoesItem = v.findViewById(R.id.showShoes);
        PantsItem = v.findViewById(R.id.showPants);
        TShirtItem = v.findViewById(R.id.showTShirts);
        JacketItem = v.findViewById(R.id.showJackets);
        ShirtItem = v.findViewById(R.id.showShirts);

        getClothes();
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
            case R.id.ShowHideShoes:
                if(ShoesItem.getVisibility() == View.VISIBLE) {
                    ShoesItem.setVisibility(View.GONE);
                }else{
                    ShoesItem.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ShowHidePants:
                if(PantsItem.getVisibility() == View.VISIBLE) {
                    PantsItem.setVisibility(View.GONE);
                }else{
                    PantsItem.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ShowHideTShirts:
                if(TShirtItem.getVisibility() == View.VISIBLE) {
                    TShirtItem.setVisibility(View.GONE);
                }else{
                    TShirtItem.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ShowHideJackets:
                if(JacketItem.getVisibility() == View.VISIBLE) {
                    JacketItem.setVisibility(View.GONE);
                }else{
                    JacketItem.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ShowHideShirts:
                if(ShirtItem.getVisibility() == View.VISIBLE) {
                    ShirtItem.setVisibility(View.GONE);
                }else{
                    ShirtItem.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private void getClothes(){
        DatabaseReference myRef = database.getReference("users/"+Username+"/Closet");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, dataSnapshot.toString());

                ShoesItem.removeAllViews();
                PantsItem.removeAllViews();
                TShirtItem.removeAllViews();
                JacketItem.removeAllViews();
                ShirtItem.removeAllViews();

                for (DataSnapshot clothingTypeSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, clothingTypeSnapshot.toString());
                    String Type = clothingTypeSnapshot.getKey();
                    for(DataSnapshot ClothingItem : clothingTypeSnapshot.getChildren()){
                        Log.d(TAG, ClothingItem.toString());
                        String ID = ClothingItem.getKey();
                        Clothing item = ClothingItem.getValue(Clothing.class);

                            AddItem(ID,Type,item);

                    }

                }


                //ClosetActivity = dataSnapshot.getValue(Clothes.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    private void AddItem(final String ID,final String Type, Clothing item){

        //Format Layout
        LinearLayout layout = new LinearLayout(this.getActivity());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        TextView text = new TextView(this.getActivity());
        Button edit = new Button(this.getActivity());
        edit.setText("Edit");
        Button delete = new Button(this.getActivity());
        delete.setText("X");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("users/"+Username+"/Closet/"+Type+"/"+ID);
                myRef.setValue(null);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ClosetIntent = new Intent(getActivity(),AddClosetItemActivity.class);
                ClosetIntent.putExtra("EditID", ID);
                ClosetIntent.putExtra("EditType", Type);
                startActivity(ClosetIntent);

            }
        });

        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(size.x/2),
                LinearLayout.LayoutParams.WRAP_CONTENT);

        text.setLayoutParams(lp);

        switch (Type){
            case "Shoes":ShoesItem.addView(layout);text.append("("+item.Color+ ")\t From :" + item.Store);break;
            case "Pants":PantsItem.addView(layout);text.append("("+item.Color+ ")\t From :" + item.Store);break;
            case "T_Shirt":TShirtItem.addView(layout);text.append("("+item.Color+ ")\t From :" + item.Store);break;
            case "Jacket":JacketItem.addView(layout);text.append("("+item.Color+ ")\t From :" + item.Store);break;
            case "Shirt":ShirtItem.addView(layout);text.append("("+item.Color+ ")\t From :" + item.Store);break;
        }

        layout.addView(text);
        layout.addView(edit);
        layout.addView(delete);
    }


}

