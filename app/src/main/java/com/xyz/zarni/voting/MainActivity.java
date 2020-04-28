package com.xyz.zarni.voting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.sdsmdg.tastytoast.TastyToast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.MMToast;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {



    private static final int REQUEST_CAMERA = 0;


SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sp = getSharedPreferences("Vote",MODE_PRIVATE);
        String a = sp.getString("login","");
        if(a.equals("yes")){
          //  TastyToast.makeText(getApplicationContext(),"Login Success!",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
        }

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_home: changeFragment(new BoyFragment());
                        break;

                    case R.id.tab_new: changeFragment(new GirlFragment());
                        break;

                    case R.id.tab_fav: changeFragment(new PresentFragment());
                        break;

                    case R.id.tab_set: changeFragment(new SettingFragment());
                        break;

                    default: changeFragment(new BoyFragment());
                        break;
                }
            }
        });


        if (ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(this, CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA, CALL_PHONE}, REQUEST_CAMERA);

        }


    }

    public void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment).commit();
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //   Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                //  Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                .setMessage(MDetect.INSTANCE.getText("Application မှ ထွက်လိုပါသလား ?"))
                .setPositiveButton(MDetect.INSTANCE.getText("ထွက်မယ်"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(MDetect.INSTANCE.getText("မထွက်ပါ"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}