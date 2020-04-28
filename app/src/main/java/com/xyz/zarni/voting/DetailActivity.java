package com.xyz.zarni.voting;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sdsmdg.tastytoast.TastyToast;


import java.util.ArrayList;
import java.util.List;

import me.myatminsoe.mdetect.MDetect;


public class DetailActivity extends AppCompatActivity {

    ImageView profile_image;
    TextView profile_name, profile_section;
    Button profile_facebook, profile_phone;
    String name, profile, section, facebook, phone, image_one, image_two, image_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if (extras == null) {
//
//                name = null;
//                profile = null;
//                section = null;
//                facebook = null;
//                phone = null;
//                image_one = null;
//                image_two = null;
//                image_three = null;
//
//            } else {
//
//                name = extras.getString("name");
//                profile = extras.getString("profile");
//                section = extras.getString("section");
//                facebook = extras.getString("facebook");
//                phone = extras.getString("phone");
//                image_one = extras.getString("image_one");
//                image_two = extras.getString("image_two");
//                image_three = extras.getString("image_three");
//
//            }
//        } else {
//
//            name = (String) savedInstanceState.getSerializable("name");
//            profile = (String) savedInstanceState.getSerializable("profile");
//            section = (String) savedInstanceState.getSerializable("section");
//            facebook = (String) savedInstanceState.getSerializable("facebook");
//            phone = (String) savedInstanceState.getSerializable("phone");
//            image_one = (String) savedInstanceState.getSerializable("image_one");
//            image_two = (String) savedInstanceState.getSerializable("image_two");
//            image_three = (String) savedInstanceState.getSerializable("image_three");
//
//        }

        name = getIntent().getExtras().getString("name");
        profile = getIntent().getExtras().getString("profile");
        section = getIntent().getExtras().getString("section");
        facebook = getIntent().getExtras().getString("facebook");
        phone = getIntent().getExtras().getString("phone");
        image_one = getIntent().getExtras().getString("image_one");
        image_two = getIntent().getExtras().getString("image_two");
        image_three = getIntent().getExtras().getString("image_three");


        profile_image = (ImageView) findViewById(R.id.profile);

        profile_name = (TextView) findViewById(R.id.name);

        profile_section = (TextView) findViewById(R.id.section);

        profile_facebook = (Button) findViewById(R.id.facebook);

        profile_phone = (Button) findViewById(R.id.phone);

        profile_name.setText(MDetect.INSTANCE.getText(name));

        profile_section.setText(section);

        ImageView One = (ImageView) findViewById(R.id.imageViewOne);

        ImageView two = (ImageView) findViewById(R.id.imageViewTwo);

        ImageView three = (ImageView) findViewById(R.id.imageViewThree);

        Glide.with(this).load(image_one).into(One);

        Glide.with(this).load(image_two).into(two);

        Glide.with(this).load(image_three).into(three);

        profile_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facebook.equals("no acc")){

                    TastyToast.makeText(getApplicationContext(),MDetect.INSTANCE.getText(name + " မှာ Facebook Account မရှိပါခင်ဗျာ"),TastyToast.LENGTH_LONG,TastyToast.INFO).show();

                }else {

                    Intent facebookIntent = getOpenFacebookIntent(getApplicationContext(),facebook);
                    startActivity(facebookIntent);

                }
            }
        });

        profile_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.equals("no phone")){
                    TastyToast.makeText(getApplicationContext(),MDetect.INSTANCE.getText(name + " ရဲ့ Phone No. ပေးမရပါခင်ဗျာ"),TastyToast.LENGTH_LONG,TastyToast.INFO).show();

                }else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                }
            }
        });

        Glide.with(getApplicationContext()).load(profile).into(profile_image);



    }



    public  Intent getOpenFacebookIntent(Context context, String uri) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)); //catches and opens a url to the desired page
        }
    }



}

