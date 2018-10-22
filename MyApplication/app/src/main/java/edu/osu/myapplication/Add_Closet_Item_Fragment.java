package edu.osu.myapplication;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Add_Closet_Item_Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Closet_Add_Frag_Activ";

    private Spinner spinner1, spinner2,spinner3;
    private Button btnSubmit,addPhoto;
    private ImageView picture;
    private Uri selectedImage;
    private String[] spinnerValues;

    public static final int GET_FROM_GALLERY = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.activity_add_closet_item, container, false);


        spinner1 = (Spinner) v.findViewById(R.id.spinner1);
        spinner2 = (Spinner) v.findViewById(R.id.spinner2);
        spinner3 = (Spinner) v.findViewById(R.id.spinner3);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        addPhoto = (Button) v.findViewById(R.id.btnAddPicture);
        picture = (ImageView) v.findViewById(R.id.closet_item_image) ;

        spinnerValues=new String[]{"Shoes","Unknown","Red"};

        btnSubmit.setOnClickListener(this);
        addPhoto.setOnClickListener(this);

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
            case R.id.btnSubmit:
                Log.d(TAG,"button clicked : submit");
                Log.d(TAG,"type : " +spinner1.getSelectedItem().toString());
                Log.d(TAG,"store : " +spinner2.getSelectedItem().toString() );
                Log.d(TAG,"color : "+spinner3.getSelectedItem().toString() );
                Log.d(TAG,"image : "+selectedImage );

                //send to firebase

                break;
            case R.id.btnAddPicture:
                Log.d(TAG,"button clicked "+v);

                Intent ImageGet = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //startActivity(newUser);

                startActivityForResult(ImageGet, GET_FROM_GALLERY);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImage);
                picture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
