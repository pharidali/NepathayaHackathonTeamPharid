package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.text.NumberFormat;

import com.example.myapplication.R;
import com.example.myapplication.model.Restaurant;
import com.example.myapplication.util.Utils;

import static com.example.myapplication.activity.DetailsActivity.INTENT_EXTRA;

public class RestaurantDetailFragment extends Fragment implements View.OnClickListener {

    private Restaurant restaurant;

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(INTENT_EXTRA, restaurant);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Restaurant restaurant = (Restaurant) getArguments().getSerializable(INTENT_EXTRA);
            if (restaurant != null) {
                this.restaurant = restaurant;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_restaurant_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView mainImg = view.findViewById(R.id.image);
        TextView title =  view.findViewById(R.id.name);
        TextView type =  view.findViewById(R.id.type);
        TextView desc =  view.findViewById(R.id.desc);
        TextView rating = view.findViewById(R.id.rating);
        TextView reviewcount = view.findViewById(R.id.reviewCount);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView directions = view.findViewById(R.id.directions);
        TextView call = view.findViewById(R.id.call);
        TextView website = view.findViewById(R.id.website);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Utils.setUpToolbar(getActivity(), toolbar, restaurant.getName());

        directions.setOnClickListener(this);
        call.setOnClickListener(this);
        website.setOnClickListener(this);

        mainImg.setImageResource(restaurant.getImageId());
        title.setText(restaurant.getName());
        type.setText(restaurant.getType());
        desc.setText(R.string.dummy_text);
        rating.setText(String.valueOf(restaurant.getRating()));
        String numberFormat = NumberFormat.getNumberInstance().format(restaurant.getReviewCount());
        reviewcount.setText(String.format(getString(R.string.reviewCount) , numberFormat));
        ratingBar.setRating(restaurant.getRating());
    }

    @Override
    public void onClick(View view) {
        if (getContext() != null) {
            switch (view.getId()) {
                case R.id.directions:
                    Utils.directionsIntent(getContext(), restaurant.getLocation());
                    break;
                case R.id.call:
                    Utils.phoneIntent(getContext(), restaurant.getPhone());
                    break;
                case R.id.website:
                    Utils.websiteIntent(getContext(), restaurant.getWebsite());
                    break;
            }
        }

    }
}
