package com.example.myapplication.data;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

import com.example.myapplication.R;
import com.example.myapplication.model.Restaurant;

/*
define methods to fetch data for Restaurants
each restaurant has a name, image, type, rating and a place
 */
public class RestaurantData {
    public static ArrayList<Restaurant> fetchRestaurants(Context context) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Resources resources = context.getResources();

        TypedArray typedArray = resources.obtainTypedArray(R.array.restaurant_mainImgID);
        int[] mainImgId = new int[typedArray.length()];
        for (int index = 0; index < mainImgId.length; index++) {
            mainImgId[index] = typedArray.getResourceId(index, 0);
        }

        typedArray = resources.obtainTypedArray(R.array.restaurant_rating);
        float[] rating = new float[typedArray.length()];
        for (int index = 0; index < rating.length; index++) {
            rating[index] = typedArray.getFloat(index, 0);
        }

        typedArray = resources.obtainTypedArray(R.array.restaurant_review_count);
        int[] reviewCount = new int[typedArray.length()];
        for (int index = 0; index < reviewCount.length; index++) {
            reviewCount[index] = typedArray.getInt(index, 0);
        }

        typedArray.recycle();

        String[] title = resources.getStringArray(R.array.restaurant_name);
        String[] type = resources.getStringArray(R.array.restaurant_type);
        String[] place = resources.getStringArray(R.array.restaurant_place);
        String[] location = resources.getStringArray(R.array.restaurant_location);
        String[] phone = resources.getStringArray(R.array.restaurant_phone);
        String[] website = resources.getStringArray(R.array.restaurant_website);

        for (int i = 0; i < mainImgId.length; i++) {
            Restaurant restaurant = new Restaurant(title[i], mainImgId[i], type[i], rating[i], reviewCount[i], place[i], location[i], phone[i], website[i]);
            restaurants.add(restaurant);
        }

        return restaurants;
    }
}
