package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Place;
import com.example.myapplication.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlacesViewHolder> {

    private ArrayList<Place> places;
    private Context context;

    public PlaceAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlacesViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlacesViewHolder holder, final int position) {
        final Place place = places.get(position);
        holder.title.setText(place.getTitle());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), place.getMainImgId());
        Bitmap blurredBitmap;
        blurredBitmap = Utils.blur(context, bitmap);
        holder.mainImg.setImageBitmap(blurredBitmap);

        holder.overlayImg.setImageResource(place.getOverlayImgId());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.fireIntent(context, place, holder.overlayImg);
            }
        });
    }


    @Override
    public int getItemCount() {
        return places.size();
    }

    class PlacesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleText)
        TextView title;
        @BindView(R.id.mainImage)
        ImageView mainImg;
        @BindView(R.id.overlayImage)
        ImageView overlayImg;
        @BindView(R.id.parentLayout)
        ConstraintLayout constraintLayout;

        PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}