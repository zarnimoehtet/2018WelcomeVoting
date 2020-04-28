package com.xyz.zarni.voting;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.MMTextView;

/**
 * Created by Zarni on 12/17/2017.
 */

public class AlbumAdapter extends FirebaseRecyclerAdapter<Model, AlbumAdapter.ViewHolder> {

    private Context context;
    private String Tag;

    private DatabaseReference mUserRef;
    private DatabaseReference mVote;
    private DatabaseReference fVote;


    public AlbumAdapter(Context context, Query ref, String Tag) {
        super(Model.class, R.layout.item_card, ViewHolder.class, ref);
        this.context = context;
        this.Tag = Tag;
    }

    @Override
    protected void populateViewHolder(final ViewHolder viewHolder, final Model model, final int position) {
        viewHolder.mUsersName.setText(MDetect.INSTANCE.getText(model.getName()));

        Glide.with(context).load(model.getProfile()).into(viewHolder.profile);

        viewHolder.vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
                // mUserRef.keepSynced(true);

                mVote = FirebaseDatabase.getInstance().getReference().child("mVote");
                mVote.keepSynced(true);

                fVote = FirebaseDatabase.getInstance().getReference().child("fVote");
                fVote.keepSynced(true);


                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                    mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                final Users users = dataSnapshot.getValue(Users.class);

                                if (users.getPermission().equals("not_allowed")) {
                                    TastyToast.makeText(context, MDetect.INSTANCE.getText("Vote ​ပေးလို့ မရ နိုင်သေးပါ။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle(MDetect.INSTANCE.getText("သတိပေးချက်"))
                                            .setMessage(MDetect.INSTANCE.getText(model.getName()) + MDetect.INSTANCE.getText(" ကို vote ပေးမှာသေချာပြီလား။"))
                                            .setPositiveButton(MDetect.INSTANCE.getText("သေချာပါပြီ"), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Toast.makeText(context, "Ready to Vote", Toast.LENGTH_SHORT).show();

                                                    if (Tag.equals("KING")) {

                                                        if (users.getKvote().equals("1")) {
                                                            TastyToast.makeText(context, MDetect.INSTANCE.getText(model.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                                            dialog.dismiss();
                                                        } else {
                                                            mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("kvote").setValue("1");

                                                            mVote.child(position+1+"").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                                    mVote.child(position+1+"").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");

                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });

                                                            dialog.dismiss();
                                                        }

                                                    } else {
                                                        if (users.getQvote().equals("1")) {
                                                            TastyToast.makeText(context, MDetect.INSTANCE.getText(model.getName() + " ကို Vote ထပ်ပေးလို့မရနိုင်ပါ။"), Toast.LENGTH_LONG, TastyToast.INFO).show();
                                                            dialog.dismiss();
                                                        } else {
                                                            mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("qvote").setValue("1");
                                                            fVote.child(position+1+"").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    fVote.child(position+1+"").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Vote");
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
                                                            dialog.dismiss();
                                                        }
                                                    }

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

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    TastyToast.makeText(context, MDetect.INSTANCE.getText("Vote ပေးဖို့အတွက် Login ဝင်ရန်လိုအပ်ပါသည်။"), TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                }
            }
        });

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("name", model.getName());
                i.putExtra("profile", model.getProfile());
                i.putExtra("section", model.getSection());
                i.putExtra("facebook", model.getFb_acc());
                i.putExtra("phone", model.getPhone());
                i.putExtra("image_one", model.getImage_one());
                i.putExtra("image_two", model.getImage_two());
                i.putExtra("image_three", model.getImage_three());


                View sharedView = viewHolder.profile;
                String transitionName = context.getString(R.string.blue_name);

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, (View) sharedView, transitionName);
                context.startActivity(i, transitionActivityOptions.toBundle());
            }
        });


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profile;
        private MMTextView mUsersName;
        private Button vote, view;
        private SharedPreferences preferences;

        public ViewHolder(View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.profile);
            mUsersName = (MMTextView) itemView.findViewById(R.id.name);

            vote = (Button) itemView.findViewById(R.id.vote);
            view = (Button) itemView.findViewById(R.id.view);
        }
    }
}