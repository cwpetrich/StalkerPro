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
    private Note note;

    public noteViewFragment() {
        // Required empty public constructor
    }

    public void giveNote(Note n){
        note = n;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(note==null){
            note = new Note();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_view, container, false);
        Button b = (Button)view.findViewById(R.id.editButton);
        TextView t = (TextView)view.findViewById(R.id.noteData);
        t.setText(note.note);
        TextView s = (TextView)view.findViewById(R.id.noteDate);
        s.setText(note.modified_at.toString());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e = (EditText)getView().findViewById(R.id.editNoteData);
                TextView t = (TextView)getView().findViewById(R.id.noteData);
                Button b = (Button)getView().findViewById(R.id.editButton);
                Button more = (Button)getView().findViewById(R.id.moreButton);
                if(e.getVisibility()==View.GONE){
                    e.setText(t.getText());
                    e.setVisibility(View.VISIBLE);
                    t.setVisibility(View.GONE);
                    b.setText("Save");
                    more.setVisibility(View.VISIBLE);
                }
                else{
                    t.setText(e.getText());
                    t.setVisibility(View.VISIBLE);
                    e.setVisibility(View.GONE);
                    more.setVisibility(View.GONE);
                    b.setText("Edit");
                }

            }
        });

        Button more = (Button)view.findViewById(R.id.moreButton);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

}
