package com.xyz.zarni.voting;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.MMTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoyFragment extends Fragment implements View.OnClickListener {

    private SliderLayout mDemoSlider;
    private DatabaseReference mBoys, mUsers, mVote;
    private ImageView p1, p2, p3, p4, p5, p6, p7, p8, p9;
    private MMTextView pmh, aks, swkk, hhha, tpa, nmtm, kzh, nnmo, hal;
    private Model model1, model2, model3, model4, model5, model6, model7, model8, model9;
    private Button v1, v2, v3, v4, v5, v6, v7, v8, v9;
    private Button vo1, vo2, vo3, vo4, vo5, vo6, vo7, vo8, vo9;
    private Users users;
    SharedPreferences sharedPreferences;

    public BoyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_boy, container, false);

        mUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsers.keepSynced(true);

        mVote = FirebaseDatabase.getInstance().getReference().child("mVote");
        mVote.keepSynced(true);

        mBoys = FirebaseDatabase.getInstance().getReference().child("Boys");
        mBoys.keepSynced(true);

        sharedPreferences = getActivity().getSharedPreferences("Vote", Context.MODE_PRIVATE);

        p1 = (ImageView) v.findViewById(R.id.p1);
        p2 = (ImageView) v.findViewById(R.id.p2);
        p3 = (ImageView) v.findViewById(R.id.p3);
        p4 = (ImageView) v.findViewById(R.id.p4);
        p5 = (ImageView) v.findViewById(R.id.p5);
        p6 = (ImageView) v.findViewById(R.id.p6);
        p7 = (ImageView) v.findViewById(R.id.p7);
     //   p8 = (ImageView) v.findViewById(R.id.p8);
      //  p9 = (ImageView) v.findViewById(R.id.p9);

        pmh = (MMTextView) v.findViewById(R.id.pmh);
        aks = (MMTextView) v.findViewById(R.id.aks);
        swkk = (MMTextView) v.findViewById(R.id.swkk);
        hhha = (MMTextView) v.findViewById(R.id.hhha);
        tpa = (MMTextView) v.findViewById(R.id.tpa);
        nmtm = (MMTextView) v.findViewById(R.id.nmtm);
        kzh = (MMTextView) v.findViewById(R.id.kzh);
      //  nnmo = (MMTextView) v.findViewById(R.id.nnmo);
     //   hal = (MMTextView) v.findViewById(R.id.hal);

        v1 = (Button) v.findViewById(R.id.v1);
        v2 = (Button) v.findViewById(R.id.v2);
        v3 = (Button) v.findViewById(R.id.v3);
        v4 = (Button) v.findViewById(R.id.v4);
        v5 = (Button) v.findViewById(R.id.v5);
        v6 = (Button) v.findViewById(R.id.v6);
        v7 = (Button) v.findViewById(R.id.v7);
      //  v8 = (Button) v.findViewById(R.id.v8);
     //   v9 = (Button) v.findViewById(R.id.v9);

        vo1 = (Button) v.findViewById(R.id.vo1);
        vo2 = (Button) v.findViewById(R.id.vo2);
        vo3 = (Button) v.findViewById(R.id.vo3);
        vo4 = (Button) v.findViewById(R.id.vo4);
        vo5 = (Button) v.findViewById(R.id.vo5);
        vo6 = (Button) v.findViewById(R.id.vo6);
        vo7 = (Button) v.findViewById(R.id.vo7);
     //   vo8 = (Button) v.findViewById(R.id.vo8);
     //   vo9 = (Button) v.findViewById(R.id.vo9);


        mBoys.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model1 = dataSnapshot.getValue(Model.class);

                    pmh.setMMText(model1.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model1.getProfile()).into(p1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model2 = dataSnapshot.getValue(Model.class);

                    aks.setMMText(model2.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model2.getProfile()).into(p2);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model3 = dataSnapshot.getValue(Model.class);

                    swkk.setMMText(model3.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model3.getProfile()).into(p3);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model4 = dataSnapshot.getValue(Model.class);

                    hhha.setMMText(model4.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model4.getProfile()).into(p4);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model5 = dataSnapshot.getValue(Model.class);

                    tpa.setMMText(model5.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model5.getProfile()).into(p5);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("6").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model6 = dataSnapshot.getValue(Model.class);

                    nmtm.setMMText(model6.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model6.getProfile()).into(p6);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBoys.child("7").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model7 = dataSnapshot.getValue(Model.class);

                    kzh.setMMText(model7.getName());
                    Glide.with(getActivity().getApplicationContext()).load(model7.getProfile()).into(p7);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mBoys.child("8").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    model8 = dataSnapshot.getValue(Model.class);
//
//                    nnmo.setMMText(model8.getName());
//                    Glide.with(getActivity().getApplicationContext()).load(model8.getProfile()).into(p8);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//        mBoys.child("9").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    model9 = dataSnapshot.getValue(Model.class);
//
//                    hal.setMMText(model9.getName());
//                    Glide.with(getActivity().getApplicationContext()).load(model9.getProfile()).into(p9);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        statusList = (RecyclerView)v.findViewById(R.id.recycler_view);
//        layoutManager = new GridLayoutManager(getContext(),2);
//        statusList.setLayoutManager(layoutManager);
//        albumAdapter = new AlbumAdapter(getContext(), currentApprove,"KING");
//
//        statusList.setAdapter(albumAdapter);

        mDemoSlider = (SliderLayout) v.findViewById(R.id.slider);

        HashMap<String, String> url_maps = new HashMap<String, String>();


        url_maps.put("a", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/Group%2Fgoup_one.jpg?alt=media&token=75e6bec2-ce0c-4b0c-8c63-5ace1f4b53ec");
        url_maps.put("b", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/Group%2Fgroup_three.jpg?alt=media&token=7ebc9513-d868-4f43-869f-5eb3f0547ba8");
        url_maps.put("c", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/Group%2Fgroup_two.jpg?alt=media&token=cf6730fd-ba2d-408a-bbff-0c8e3b4063d3");
        url_maps.put("d", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/Group%2Fgroup_four.jpg?alt=media&token=30fc6cb3-5267-4f7c-ad41-fb0c5cc87900");


        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
        v4.setOnClickListener(this);
        v5.setOnClickListener(this);
        v6.setOnClickListener(this);
        v7.setOnClickListener(this);
//        v8.setOnClickListener(this);
      //  v9.setOnClickListener(this);

        vo1.setOnClickListener(this);
        vo2.setOnClickListener(this);
        vo3.setOnClickListener(this);
        vo4.setOnClickListener(this);
        vo5.setOnClickListener(this);
        vo6.setOnClickListener(this);
        vo7.setOnClickListener(this);
     //   vo8.setOnClickListener(this);
     //   vo9.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.v1:
                Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i.putExtra("name", model1.getName());
                i.putExtra("profile", model1.getProfile());
                i.putExtra("section", model1.getSection());
                i.putExtra("facebook", model1.getFb_acc());
                i.putExtra("phone", model1.getPhone());
                i.putExtra("image_one", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/B1%2FIMG_2596.JPG?alt=media&token=7f2db0e4-ccf4-43c8-9185-456019dea005");
                i.putExtra("image_two", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/B1%2FIMG_2633.JPG?alt=media&token=cfc53c07-1364-4b20-b2fb-b64eb5262b63");
                i.putExtra("image_three", "https://firebasestorage.googleapis.com/v0/b/welcome-voting-application.appspot.com/o/B1%2FIMG_2597.JPG?alt=media&token=9bbb484c-cc2f-4e3f-872a-fd4c3db1afea");


                View sharedView = p1;
                String transitionName = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView, transitionName);
                getContext().startActivity(i, transitionActivityOptions.toBundle());
                break;

            case R.id.v2:
                Intent i2 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i2.putExtra("name", model2.getName());
                i2.putExtra("profile", model2.getProfile());
                i2.putExtra("section", model2.getSection());
                i2.putExtra("facebook", model2.getFb_acc());
                i2.putExtra("phone", model2.getPhone());
                i2.putExtra("image_one", model2.getImage_one());
                i2.putExtra("image_two", model2.getImage_two());
                i2.putExtra("image_three", model2.getImage_three());


                View sharedView2 = p2;
                String transitionName2 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions2 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView2, transitionName2);
                getContext().startActivity(i2, transitionActivityOptions2.toBundle());
                break;

            case R.id.v3:

                Intent i3 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i3.putExtra("name", model3.getName());
                i3.putExtra("profile", model3.getProfile());
                i3.putExtra("section", model3.getSection());
                i3.putExtra("facebook", model3.getFb_acc());
                i3.putExtra("phone", model3.getPhone());
                i3.putExtra("image_one", model3.getImage_one());
                i3.putExtra("image_two", model3.getImage_two());
                i3.putExtra("image_three", model3.getImage_three());


                View sharedView3 = p3;
                String transitionName3 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions3 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView3, transitionName3);
                getContext().startActivity(i3, transitionActivityOptions3.toBundle());

                break;

            case R.id.v4:

                Intent i4 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i4.putExtra("name", model4.getName());
                i4.putExtra("profile", model4.getProfile());
                i4.putExtra("section", model4.getSection());
                i4.putExtra("facebook", model4.getFb_acc());
                i4.putExtra("phone", model4.getPhone());
                i4.putExtra("image_one", model4.getImage_one());
                i4.putExtra("image_two", model4.getImage_two());
                i4.putExtra("image_three", model4.getImage_three());


                View sharedView4 = p4;
                String transitionName4 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions4 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView4, transitionName4);
                getContext().startActivity(i4, transitionActivityOptions4.toBundle());


                break;

            case R.id.v5:

                Intent i5 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i5.putExtra("name", model5.getName());
                i5.putExtra("profile", model5.getProfile());
                i5.putExtra("section", model5.getSection());
                i5.putExtra("facebook", model5.getFb_acc());
                i5.putExtra("phone", model5.getPhone());
                i5.putExtra("image_one", model5.getImage_one());
                i5.putExtra("image_two", model5.getImage_two());
                i5.putExtra("image_three", model5.getImage_three());


                View sharedView5 = p5;
                String transitionName5 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions5 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView5, transitionName5);
                getContext().startActivity(i5, transitionActivityOptions5.toBundle());


                break;

            case R.id.v6:
                Intent i6 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i6.putExtra("name", model6.getName());
                i6.putExtra("profile", model6.getProfile());
                i6.putExtra("section", model6.getSection());
                i6.putExtra("facebook", model6.getFb_acc());
                i6.putExtra("phone", model6.getPhone());
                i6.putExtra("image_one", model6.getImage_one());
                i6.putExtra("image_two", model6.getImage_two());
                i6.putExtra("image_three", model6.getImage_three());


                View sharedView6 = p6;
                String transitionName6 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions6 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView6, transitionName6);
                getContext().startActivity(i6, transitionActivityOptions6.toBundle());

                break;

            case R.id.v7:

                Intent i7 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                i7.putExtra("name", model7.getName());
                i7.putExtra("profile", model7.getProfile());
                i7.putExtra("section", model7.getSection());
                i7.putExtra("facebook", model7.getFb_acc());
                i7.putExtra("phone", model7.getPhone());
                i7.putExtra("image_one", model7.getImage_one());
                i7.putExtra("image_two", model7.getImage_two());
                i7.putExtra("image_three", model7.getImage_three());


                View sharedView7 = p7;
                String transitionName7 = getActivity().getApplicationContext().getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions7 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView7, transitionName7);
                getContext().startActivity(i7, transitionActivityOptions7.toBundle());


                break;

//            case R.id.v8:
//
//                Intent i8 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
//                i8.putExtra("name", model8.getName());
//                i8.putExtra("profile", model8.getProfile());
//                i8.putExtra("section", model8.getSection());
//                i8.putExtra("facebook", model8.getFb_acc());
//                i8.putExtra("phone", model8.getPhone());
//                i8.putExtra("image_one", model8.getImage_one());
//                i8.putExtra("image_two", model8.getImage_two());
//                i8.putExtra("image_three", model8.getImage_three());
//
//
//                View sharedView8 = p8;
//                String transitionName8 = getActivity().getApplicationContext().getString(R.string.blue_name);
//
//                ActivityOptions transitionActivityOptions8 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView8, transitionName8);
//                getContext().startActivity(i8, transitionActivityOptions8.toBundle());
//
//
//                break;

//            case R.id.v9:
//
//                Intent i9 = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
//                i9.putExtra("name", model9.getName());
//                i9.putExtra("profile", model9.getProfile());
//                i9.putExtra("section", model9.getSection());
//                i9.putExtra("facebook", model9.getFb_acc());
//                i9.putExtra("phone", model9.getPhone());
//                i9.putExtra("image_one", model9.getImage_one());
//                i9.putExtra("image_two", model9.getImage_two());
//                i9.putExtra("image_three", model9.getImage_three());
//
//
//                View sharedView9 = p9;
//                String transitionName9 = getActivity().getApplicationContext().getString(R.string.blue_name);
//
//                ActivityOptions transitionActivityOptions9 = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), (View) sharedView9, transitionName9);
//                getContext().startActivity(i9, transitionActivityOptions9.toBundle());
//
//
//                break;

            //Vote-------------------------------------

            case R.id.vo1:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                       TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model1.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model1.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model1.getName());
                                                        editor.commit();

                                                        mVote.child("1").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("1").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model1.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }

                break;

            case R.id.vo2:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                       TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model2.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model2.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model2.getName());
                                                        editor.commit();
                                                        mVote.child("2").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("2").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model2.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }

                break;

            case R.id.vo3:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model3.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model3.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model3.getName());
                                                        editor.commit();
                                                        mVote.child("3").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("3").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model3.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }

                break;

            case R.id.vo4:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model4.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model4.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model4.getName());
                                                        editor.commit();
                                                        mVote.child("4").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("4").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model4.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }

                break;

            case R.id.vo5:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model5.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model5.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model5.getName());
                                                        editor.commit();
                                                        mVote.child("5").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("5").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model5.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }


                break;

            case R.id.vo6:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model6.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model6.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model6.getName());
                                                        editor.commit();
                                                        mVote.child("6").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("6").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model6.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }


                break;

            case R.id.vo7:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    if (sharedPreferences.getString("King","").equals("yes")) {
                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model7.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),0);
                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                                .setMessage(MDetect.INSTANCE.getText(model7.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("King","yes");
                                                        editor.commit();
                                                        editor.putString("voted_king",model7.getName());
                                                        editor.commit();
                                                        mVote.child("7").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                mVote.child("7").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model7.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                                                                dialog.dismiss();
                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });

                                                    }
                                                })
                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                        builder.show();

                                    }


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();

                }

                break;
//
//            case R.id.vo8:
//
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//
//                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                users = dataSnapshot.getValue(Users.class);
//
//                                if (users.getPermission().equals("not_allowed")) {
//                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
//                                } else {
//
//                                    if (sharedPreferences.getString("King","").equals("yes")) {
//                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model8.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
//                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
//                                                .setMessage(MDetect.INSTANCE.getText(model8.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
//                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(final DialogInterface dialog, int which) {
//                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                        editor.putString("King","yes");
//                                                        editor.commit();
//                                                        mVote.child("8").addValueEventListener(new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                                mVote.child("8").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
//                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model8.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//                                                                dialog.dismiss();
//                                                            }
//
//                                                            @Override
//                                                            public void onCancelled(DatabaseError databaseError) {
//
//                                                            }
//                                                        });
//
//                                                    }
//                                                })
//                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//
//                                        builder.show();
//
//                                    }
//
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                } else {
//                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
//
//                }
//
//
//                break;

//            case R.id.vo9:
//
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//
//                    mUsers.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                users = dataSnapshot.getValue(Users.class);
//
//                                if (users.getPermission().equals("not_allowed")) {
//                                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
//                                } else {
//
//                                    if (sharedPreferences.getString("King","").equals("yes")) {
//                                        TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model9.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
//                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
//                                                .setMessage(MDetect.INSTANCE.getText(model9.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
//                                                .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(final DialogInterface dialog, int which) {
//                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                        editor.putString("King","yes");
//                                                        editor.commit();
//                                                        mVote.child("9").addValueEventListener(new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                                                mVote.child("9").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
//                                                                TastyToast.makeText(getContext(), MDetect.INSTANCE.getText(model9.getName() + " ကို Vote ပေးပြီးပါပြီ။"), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//                                                               dialog.dismiss();
//                                                            }
//
//                                                            @Override
//                                                            public void onCancelled(DatabaseError databaseError) {
//
//                                                            }
//                                                        });
//
//                                                    }
//                                                })
//                                                .setNegativeButton(MDetect.INSTANCE.getText("မသေချာပါ"), new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//
//                                        builder.show();
//
//                                    }
//
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                } else {
//                    TastyToast.makeText(getContext(), MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
//
//                }
//
//
//                break;
        }
    }
}
