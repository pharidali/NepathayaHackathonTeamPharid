package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.Place;
import com.example.myapplication.util.Utils;

import static com.example.myapplication.activity.DetailsActivity.INTENT_EXTRA;


public class PlaceDetailFragment extends Fragment implements View.OnClickListener {

    private Place place;

    /**
     * initialize a new fragment
     * @param place place object to be used to display the details
     * @return detail fragment instance
     */
    public static PlaceDetailFragment newInstance(Place place) {
        PlaceDetailFragment fragment = new PlaceDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(INTENT_EXTRA, place);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Place place = (Place) getArguments().getSerializable(INTENT_EXTRA);
            if (place != null) {
                this.place = place;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_place_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView mainImg = view.findViewById(R.id.mainImage);
        ImageView overlayImg = view.findViewById(R.id.overlayImage);
        TextView title = view.findViewById(R.id.title);
        TextView hours = view.findViewById(R.id.hours);
        TextView desc = view.findViewById(R.id.desc);
        TextView directions = view.findViewById(R.id.directions);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Utils.setUpToolbar(getActivity(), toolbar, place.getTitle());

        directions.setOnClickListener(this);

        mainImg.setImageResource(place.getOverlayImgId());
        overlayImg.setImageResource(place.getMainImgId());
        title.setText(place.getTitle());
        hours.setText(place.getOpenHours());
        desc.setText(R.string.dummy_text);
    }

    @Override
    public void onClick(View view) {
        if (getContext() != null) {
            Utils.directionsIntent(getContext(),place.getLocation());
        }
    }
}
