package com.xyz.zarni.voting;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.myatminsoe.mdetect.MDetect;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PresentFragment extends Fragment {

    TextView id;
    TextView king,queen;
    SharedPreferences sp;
    String king_name,queen_name,card_id;

    public PresentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_present, container, false);

        id = v.findViewById(R.id.ID);
        king = v.findViewById(R.id.king);
        queen = v.findViewById(R.id.queen);
        sp = getActivity().getSharedPreferences("Vote",MODE_PRIVATE);
         king_name = sp.getString("voted_king","");
         queen_name = sp.getString("voted_queen","");
         card_id = sp.getString("card","");


        if(!king_name.equals(null) && !queen_name.equals(null)){
            king.setText(MDetect.INSTANCE.getText(king_name));
            queen.setText(MDetect.INSTANCE.getText(queen_name));
        }else {
            king.setText("");
            queen.setText("");
        }

        if (!card_id.equals("")){
            id.setText(card_id);
        }else {
            id.setText("");
        }





        return v;
    }

}
