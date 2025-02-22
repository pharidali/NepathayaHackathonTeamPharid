package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Restaurant;
import com.example.myapplication.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_restaurant_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position) {
        final Restaurant restaurant = restaurants.get(position);
        holder.restaurantImg.setImageResource(restaurant.getImageId());
        holder.name.setText(restaurant.getName());
        holder.place.setText(restaurant.getPlace());
        holder.type.setText(restaurant.getType());
        holder.rating.setText(String.valueOf(restaurant.getRating()));
        holder.ratingBar.setRating(restaurant.getRating());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.fireIntent(context, restaurant, holder.restaurantImg);
            }
        });
    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    //ViewHolder design pattern to help reduce the number of calls to findViewById
    class RestaurantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurantImage)
        ImageView restaurantImg;
        @BindView(R.id.restaurantName)
        TextView name;
        @BindView(R.id.restaurantType)
        TextView type;
        @BindView(R.id.place)
        TextView place;
        @BindView(R.id.rating)
        TextView rating;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.cardView)
        CardView cardView;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}