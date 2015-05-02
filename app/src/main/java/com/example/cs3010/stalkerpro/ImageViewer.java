package com.example.cs3010.stalkerpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;


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
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            i.setImageBitmap(bitmap);
        }
        return view;
    }

    public void giveImageData(File f,ImageClass i){
        imageFile = f;
        info = i;
    }

}
