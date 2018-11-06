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

public class ClosetFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Closet_Frag_Activity";

    private FirebaseAuth mAuth;
    String Username;
    FirebaseDatabase database;

    List<ClothingInstance> Shoes;

    LinearLayout ShoesItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.activity_closet_fragment, container, false);


        Button additem = v.findViewById(R.id.btnAddItem);
        additem.setOnClickListener(this);

        //Username = FirebaseInstanceId.getInstance().getId()+"";
        mAuth = FirebaseAuth.getInstance();
        Username = mAuth.getCurrentUser().getUid()+"";
        database = FirebaseDatabase.getInstance();

        ShoesItem = v.findViewById(R.id.showShoes);

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

                Shoes = new LinkedList<ClothingInstance>();
                ShoesItem.removeAllViews();
                for (DataSnapshot clothingTypeSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, clothingTypeSnapshot.toString());
                    String Type = clothingTypeSnapshot.getKey();
                    for(DataSnapshot ClothingItem : clothingTypeSnapshot.getChildren()){
                        Log.d(TAG, ClothingItem.toString());
                        String ID = ClothingItem.getKey();
                        ClothingInstance item = ClothingItem.getValue(ClothingInstance.class);

                            AddItem(ID,Type,item);

                    }

                }

                Log.d(TAG, "Shoes :"+Shoes.size());


                //ClosetActivity = dataSnapshot.getValue(Clothes.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private static class ClothingInstance {
        public String Type;
        public String Store;
        public String Color;
        public String Image;
        public ClothingInstance(){}
        public ClothingInstance(String Type, String Store, String Color, String Image) {
            this.Type = Type;
            this.Store = Store;
            this.Color = Color;
            this.Image = Image;
        }
    }

    private void AddItem(final String ID,final String Type, ClothingInstance item){

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



        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(3*size.x/5),
                LinearLayout.LayoutParams.WRAP_CONTENT);

        text.setLayoutParams(lp);

        //Set Items
        if(Type.equals("Shoes")){
            Shoes.add(item);
            //text.setText();
            ShoesItem.addView(layout);

            text.append("Shoes("+item.Color+ ")\t From :" + item.Store);
            layout.addView(text);
            layout.addView(edit);
            layout.addView(delete);
            //ShoesItem.append( + "\n");
        }


    }


}

