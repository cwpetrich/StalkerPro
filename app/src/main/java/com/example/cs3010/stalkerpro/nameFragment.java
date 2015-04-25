package com.example.cs3010.stalkerpro;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class nameFragment extends Fragment {
    String name;

    public nameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setName(String n){
        name = n;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View nameView = inflater.inflate(R.layout.fragment_name,container,false);
        Button namePlace = (Button)nameView.findViewById(R.id.namePlace);
        namePlace.setText(name);
        namePlace.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Home.makeToast("bob was clicked");

            }
        });
        // Inflate the layout for this fragment
        return nameView;
    }

}
