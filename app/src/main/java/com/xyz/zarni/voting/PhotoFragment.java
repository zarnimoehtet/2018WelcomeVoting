package com.xyz.zarni.voting;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class PhotoFragment extends Fragment {

    private String imageUrl;



    public PhotoFragment() {
        // Required empty public constructor
    }

    public PhotoFragment(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

        Glide.with(v.getContext()).load(imageUrl).into(imageView);

        return v;
    }

}
