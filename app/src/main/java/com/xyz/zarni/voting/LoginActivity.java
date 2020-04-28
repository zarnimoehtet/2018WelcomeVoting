package com.xyz.zarni.voting;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.sdsmdg.tastytoast.TastyToast;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class LoginActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private Button scan;
    private ImageView qr;
    private DatabaseReference mUserRef,mVoteName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;
    private String year = "";
    String key = "";
    private SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);

        mVoteName = FirebaseDatabase.getInstance().getReference().child("VotedName");
        mVoteName.keepSynced(true);


        sp = getSharedPreferences("Vote",MODE_PRIVATE);
        editor = sp.edit();



        scan = (Button) findViewById(R.id.btn_scan);
        qr = (ImageView) findViewById(R.id.icon);

        scannerView = new ZXingScannerView(getApplicationContext());


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });
        // setupWindowAnimations();

    }


    private void scanQR() {
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//       // startActivity(new Intent(LoginActivity.this,MainActivity.class));
//        finish();
//    }

    @Override
    public void handleResult(Result result) {
        key = result.toString().toLowerCase();



        mAuth.signInWithEmailAndPassword(key+"@gmail.com",key).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();

                    DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    mUserRef.keepSynced(true);

                    editor.clear();
                    editor.commit();

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Users users = dataSnapshot.getValue(Users.class);

                                    if (users.getKvote().equals("1")) {
                                        editor.putString("King", "yes");
                                        editor.commit();
                                        Log.i("King ==>","yes");
                                    } else {
                                        editor.putString("King", "no");
                                        editor.commit();
                                        Log.i("King ==>","no");
                                    }

                                    if (users.getQvote().equals("1")) {
                                        editor.putString("Queen", "yes");
                                        editor.commit();
                                    } else {
                                        editor.putString("Queen", "no");
                                        editor.commit();
                                    }


//                                    editor.putString("login","yes");
//                                    editor.commit();

                                    editor.putString("card",key);
                                    editor.commit();


                                    Log.i("NO ERROR ==>","No Error");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        mVoteName.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Name name = dataSnapshot.getValue(Name.class);

                                    Log.i("King Name", name.getKing());
                                    Log.i("Queen Name",name.getQueen());

                                    if (!name.getKing().equals("") && !name.getQueen().equals("")){


                                        editor.putString("voted_king",name.getKing());
                                        editor.commit();
                                        editor.putString("voted_queen",name.getQueen());
                                        editor.commit();

                                    }else {
                                        editor.putString("voted_king","");
                                        editor.commit();
                                        editor.putString("voted_queen","");
                                        editor.commit();

                                    }
                                }
                            }
//TODO fix error above

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                }else {
                    Log.i("Error =====>","Task Error");
                }
            }
        });



        startActivity(new Intent(LoginActivity.this,MainActivity.class));


        scannerView.resumeCameraPreview(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}

