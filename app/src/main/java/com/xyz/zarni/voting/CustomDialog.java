package com.xyz.zarni.voting;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Zarni on 12/24/2017.
 */

public class CustomDialog extends android.support.v4.app.DialogFragment {

    private TextView title,body;
    private String t,b;
    private Button yes,no;
    SharedPreferences sharedPreferences;
    public static final String mypreference = "mypref";

    public CustomDialog() {
    }
    public CustomDialog(String t, String b) {
        this.t = t;
        this.b = b;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog, container, false);
        getDialog().setCancelable(false);

        title = (TextView) v.findViewById(R.id.title);

        body = (TextView) v.findViewById(R.id.text);

        yes = (Button) v.findViewById(R.id.Yes);

        no = (Button) v.findViewById(R.id.No);

        sharedPreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        title.setText(t);
        body.setText(b);

        return v;
    }
}
