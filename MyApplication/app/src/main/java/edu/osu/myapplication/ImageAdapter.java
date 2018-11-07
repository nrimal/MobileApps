package edu.osu.myapplication;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.osu.myapplication.Data.Clothing;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Clothing> mClothes;

    public ImageAdapter(Context context, List<Clothing> clothes){
        mContext = context;
        mClothes = clothes;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Clothing clothCurrent = mClothes.get(i);

        Picasso.with(mContext)
                .load(Uri.parse(clothCurrent.Image))
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .into(imageViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mClothes.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ImageViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.suggestion_image);
        }
    }
}
