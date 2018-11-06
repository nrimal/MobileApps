package edu.osu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends MainActivity {
    private Intent currentRunning;
    private String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        recyclerView = (RecyclerView)findViewById(R.id.recycler_image_view);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        recyclerView.setAdapter(new PhotoAdapter());



        Log.d(TAG, "onCreate(Bundle) called");

    }

//    private class PhotoViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView imageView1, imageView2, imageView3;
//        public PhotoViewHolder(View view) {
//            super(view);
//            imageView1 = (ImageView)view.findViewById(R.id.first_image_suggestion);
//            imageView2 = (ImageView)view.findViewById(R.id.second_image_suggestion);
//            imageView3 = (ImageView)view.findViewById(R.id.third_image_suggestion);
//        }
//
//    }
//    // Photo adapter
//    private class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
//        @Override
//        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
//        }
//        @Override
//        public void onBindViewHolder(PhotoViewHolder holder, int position) {
//            ContactsContract.CommonDataKinds.Photo photo = photos.get(position);
//            // Attempt to load image from cache
//            Bitmap thumbnail = photoThumbnails.get(photo.getId());
//            if (thumbnail == null) {
//                // Image was not found in cache; load it from the server
//            } else {
//                holder.imageView.setImageBitmap(thumbnail);
//            }
//            holder.textView.setText(photo.getTitle());
//        }
//        @Override
//        public int getItemCount() {
//            return (photos == null) ? 0 : photos.size();
//        }
//    }


}
