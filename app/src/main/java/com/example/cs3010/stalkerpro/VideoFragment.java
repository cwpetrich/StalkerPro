package com.example.cs3010.stalkerpro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;


public class VideoFragment extends Fragment {
    VideoClass data;
    Uri fileuri;
    File file;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void giveVideoData(VideoClass vc){
        data = vc;
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),data.video_name);
        fileuri = Uri.fromFile(file);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Button watch = (Button) view.findViewById(R.id.watchVideo);
        Button delete = (Button) view.findViewById(R.id.deleteVideo);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileuri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(fileuri, "video/*");
                    startActivity(intent);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file.delete();
                Home.getDatabase().deleteVideoRow(data.video_name);
                ViewNotes notesview = (ViewNotes) getActivity();
                notesview.updateView();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
