package com.example.cs3010.stalkerpro;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.zip.Inflater;

public class noteViewFragment extends Fragment {

    public noteViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_view, container, false);
        Button b = (Button)view.findViewById(R.id.editButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText)getView().findViewById(R.id.editNoteData);
                TextView t = (TextView)getView().findViewById(R.id.noteData);
                Button b = (Button)getView().findViewById(R.id.editButton);
                if(e.getVisibility()==View.GONE){
                    e.setText(t.getText());
                    e.setVisibility(View.VISIBLE);
                    t.setVisibility(View.GONE);
                    b.setText("Save");
                }
                else{
                    t.setText(e.getText());
                    t.setVisibility(View.VISIBLE);
                    e.setVisibility(View.GONE);
                    b.setText("Edit");
                }

            }
        });
        return view;
    }

}
