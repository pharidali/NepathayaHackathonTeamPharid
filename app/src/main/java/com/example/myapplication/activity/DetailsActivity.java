package com.example.myapplication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.fragment.PlaceDetailFragment;
import com.example.myapplication.fragment.RestaurantDetailFragment;
import com.example.myapplication.model.Place;
import com.example.myapplication.model.Restaurant;

public class DetailsActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA = "extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Object object = getIntent().getSerializableExtra(INTENT_EXTRA);
        Fragment fragment = null;
        if (object instanceof Place) {
            fragment = PlaceDetailFragment.newInstance((Place) object);
        } else if (object instanceof Restaurant) {
            fragment = RestaurantDetailFragment.newInstance((Restaurant) object);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
