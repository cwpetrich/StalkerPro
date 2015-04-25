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

public class NewPersonFragment extends Fragment {

    public NewPersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_new_person, container, false);
        Button b = (Button)newView.findViewById(R.id.clearButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.clearNewNameArea();
            }
        });
        Button add = (Button)newView.findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText)getView().findViewById(R.id.editText);
                Home.addNewName(name.getText().toString());
                Home.clearNewNameArea();
            }
        });
        // Inflate the layout for this fragment
        return newView;
    }

}
