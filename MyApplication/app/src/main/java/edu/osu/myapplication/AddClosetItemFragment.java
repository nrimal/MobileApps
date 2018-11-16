package edu.osu.myapplication;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.osu.myapplication.Data.Clothing;

import static java.util.stream.Collectors.joining;


public class AddClosetItemFragment extends Fragment implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private Spinner spinner1, spinner2,spinner3,spinnerSubType;
    private Button btnSubmit,addPhoto;
    private ImageView picture;
    private Uri selectedImageUri;

    private FirebaseAuth mAuth;
    private String mUsername;

    private DatabaseReference myRef;
    private StorageReference storageItem;

    private Bundle Extras;
    private String EditID;
    private String CType;
    public static final int GET_FROM_GALLERY = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up the login form.
        Log.d(TAG, this + ": onCreateView()");
        View v =  inflater.inflate(R.layout.activity_add_closet_item, container, false);


        spinner1 = v.findViewById(R.id.spinner1);
        spinnerSubType = v.findViewById(R.id.spinnerSubType);
        spinner2 =  v.findViewById(R.id.spinner2);
        spinner3 =  v.findViewById(R.id.spinner3);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        addPhoto = v.findViewById(R.id.btnAddPicture);
        picture = v.findViewById(R.id.closet_item_image);

        btnSubmit.setOnClickListener(this);
        addPhoto.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUsername = mAuth.getCurrentUser().getUid();


        storageItem = FirebaseStorage.getInstance().getReference("users/"+mUsername+"/Closet/");
        myRef = FirebaseDatabase.getInstance().getReference("users/"+mUsername+"/Closet/");

        Extras = getActivity().getIntent().getExtras();
        EditID=null;
              if (Extras!=null){
                  EditID = Extras.getString("EditID");
                  btnSubmit.setText("Update");
                  spinner1.setVisibility(View.GONE);
              }
              Log.d(TAG,"Editing? : "+EditID+"========================");
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + ": onCreate()");
    }

    @Override
    public void onClick(View v) {
        CType = spinner1.getSelectedItem().toString().replace('-','_');

        switch(v.getId()){
            case R.id.btnSubmit:
                //TODO: .... add category...closet subfolder
                Log.d(TAG,"button clicked : submit");
                Log.d(TAG,"store : " +spinner2.getSelectedItem().toString() );
                Log.d(TAG,"color : "+spinner3.getSelectedItem().toString() );
                Log.d(TAG,"image : "+ selectedImageUri);

                if(EditID!=null){CType = Extras.getString("EditType");}
                uploadFile();
                Log.d(TAG,"Users/"+mUsername+"/Closet/"+CType);
                getActivity().finish();

                break;
            case R.id.btnAddPicture:
                Log.d(TAG,"button clicked "+v);
                //TODO: Camera Intent
                Intent ImageGet = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(ImageGet, GET_FROM_GALLERY);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImageUri);
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

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){

        if(selectedImageUri!=null){
            StorageReference fileReference = storageItem.child(CType).child(System.currentTimeMillis()+"."+getFileExtension(selectedImageUri));
        fileReference.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(getActivity(),"Upload successful", Toast.LENGTH_LONG).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        Clothing cothUpload = new Clothing(
                                        spinner2.getSelectedItem().toString(),
                                        spinner3.getSelectedItem().toString(),
                                downloadUrl+ "",
                                spinnerSubType.getSelectedItem().toString()

                                );
                        String uploadid = myRef.child(CType).push().getKey();
                        myRef.child(CType).child(uploadid).setValue(cothUpload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

}
