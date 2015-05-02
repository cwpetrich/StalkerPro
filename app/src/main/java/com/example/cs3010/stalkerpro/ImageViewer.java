package com.example.cs3010.stalkerpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;


public class ImageViewer extends Fragment {
    File imageFile;
    ImageClass info;

    public ImageViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_viewer,container,false);
        // Inflate the layout for this fragment
        ImageView i = (ImageView) view.findViewById(R.id.imageView);
        if(imageFile != null && imageFile.exists()){
            //Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            //i.setImageBitmap(bitmap);
            i.setImageURI(Uri.fromFile(imageFile));
            i.setRotation(90);
            i.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        }

        Button b = (Button) view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewNotes vn = (ViewNotes) getActivity();
                imageFile.delete();
                Home.getDatabase().deleteImageRow(info.image_name);
                vn.updateView();
            }
        });

        return view;
    }

    public void giveImageData(File f,ImageClass i){
        imageFile = f;
        info = i;
    }

}
