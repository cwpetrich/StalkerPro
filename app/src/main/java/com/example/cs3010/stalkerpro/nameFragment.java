package com.example.cs3010.stalkerpro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.UUID;


public class nameFragment extends Fragment {
    String name;
    private UUID puuid;

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
    public void setPuuid(UUID u){puuid = u;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View nameView = inflater.inflate(R.layout.fragment_name,container,false);
        Button namePlace = (Button)nameView.findViewById(R.id.namePlace);
        namePlace.setText(name);
        namePlace.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Home.getMain(), ViewNotes.class);
                Bundle b = new Bundle();
                b.putString("uuid",puuid.toString());
                b.putString("name",name);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return nameView;
    }

}
