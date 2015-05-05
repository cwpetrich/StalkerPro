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
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;


public class VideoFragment extends Fragment {
    VideoClass data;
    File videoFile;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Button videoView = (Button) view.findViewById(R.id.videoFragPlayButton);

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Uri.fromFile(videoFile) != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(videoFile), "video/*");
                    startActivity(intent);
                }
            }
        });

        Button delete = (Button) view.findViewById(R.id.videoFragDeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoFile.delete();
                long return_val = Home.getDatabase().deleteVideoRow(data.video_name);
                if (return_val == -1){
                    Home.makeToast("Error deleting from database.");
                }else{
                    Home.makeToast("Successfully deleted from database.");
                }
                ViewNotes notesview = (ViewNotes) getActivity();
                notesview.updateView();
            }
        });

        TextView timeStamp = (TextView) view.findViewById(R.id.videoFragTimeStamp);
        timeStamp.setText("Taken at:  " + data.created_at);
        // Inflate the layout for this fragment
        return view;
    }

    public void giveVideoData(VideoClass vc){
        data = vc;
        videoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/StalkerPro/",data.video_name);
    }

}
