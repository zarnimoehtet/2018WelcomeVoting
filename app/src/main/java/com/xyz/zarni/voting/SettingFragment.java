package com.xyz.zarni.voting;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.myatminsoe.mdetect.MDetect;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private LinearLayout l1, l2;
    private RelativeLayout r1, r2;
    private TextView t1, t2, t3, t4, t5, t6;

    private FirebaseAuth mAuth;

    private DatabaseReference mUserRef,mVoteName;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);

        mVoteName = FirebaseDatabase.getInstance().getReference().child("VotedName");
        mVoteName.keepSynced(true);


        l1 = (LinearLayout) v.findViewById(R.id.LoginArea);
        l2 = (LinearLayout) v.findViewById(R.id.LogoutArea);

        r1 = (RelativeLayout) v.findViewById(R.id.login_btn);
        r2 = (RelativeLayout) v.findViewById(R.id.logout_btn);



        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
        } else {
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);
        }

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final SharedPreferences sp = getActivity().getSharedPreferences("Vote", Context.MODE_PRIVATE);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                        .setMessage(MDetect.INSTANCE.getText("ဒီ Account ကို logout လုပ်မှာသေချာပြီလား။"))
                        .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mAuth.getCurrentUser() != null) {

                                    if (sp.getString("King", "").equals("yes")) {
                                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("kvote").setValue("1");

                                    }

                                    if (sp.getString("Queen", "").equals("yes")) {
                                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("qvote").setValue("1");
                                    }

                                    if (!sp.getString("voted_king","").equals(null)){
                                        mVoteName.child(mAuth.getCurrentUser().getUid()).child("king").setValue(sp.getString("voted_king",""));
                                    }

                                    if (!sp.getString("voted_king","").equals(null)){
                                        mVoteName.child(mAuth.getCurrentUser().getUid()).child("queen").setValue(sp.getString("voted_queen",""));
                                    }


//                                    SharedPreferences.Editor editor = sp.edit();
//                                    editor.clear();
//                                    editor.commit();
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("login","no");
                                    editor.apply();
                                    editor.putString("card","000000");
                                    editor.commit();
                                    editor.putString("voted_king","");
                                    editor.commit();
                                    editor.putString("voted_queen","");
                                    editor.commit();
                                }


                                mAuth.signOut();
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
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
        });

//        t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"fb://profile/100009465458056");
//                getContext().startActivity(facebookIntent);
//            }
//        });
//
//        t2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"https://www.facebook.com/jkoko.hein");
//               getContext().startActivity(facebookIntent);
//            }
//        });
//
//        t3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"fb://profile/100009154109815");
//                getContext().startActivity(facebookIntent);
//            }
//        });
//
//        t4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"fb://profile/100013422581068");
//               getContext().startActivity(facebookIntent);
//            }
//        });
//
//        t5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"fb://profile/100009494153339");
//                getContext().startActivity(facebookIntent);
//            }
//        });
//
//        t6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent facebookIntent = getOpenFacebookIntent(getActivity().getApplicationContext(),"fb://profile/100010086073037");
//                getContext().startActivity(facebookIntent);
//            }
//        });
        return v;
    }

//    public  Intent getOpenFacebookIntent(Context context, String uri) {
//
//        try {
//            context.getPackageManager()
//                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(uri)); //Trys to make intent with FB's URI
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(uri)); //catches and opens a url to the desired page
//        }
//    }

}
