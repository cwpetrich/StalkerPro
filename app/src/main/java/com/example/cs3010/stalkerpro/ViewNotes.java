package com.example.cs3010.stalkerpro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;


public class ViewNotes extends ActionBarActivity {
    private UUID puuid;
    private File imageFile;
    private File videoFile;
    private ImageClass imageClass;
    private VideoClass videoClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        Bundle b = getIntent().getExtras();
        String value = b.getString("uuid");
        puuid = UUID.fromString(value);
        //TextView name =(TextView) findViewById(R.id.notesOwner);
        String n = b.getString("name");
        setTitle(n);
        updateView();
    }

    public void updateView(){
        LinearLayout tll = (LinearLayout)findViewById(R.id.textNotesContainer);
        LinearLayout ill = (LinearLayout) findViewById(R.id.imageNotesContainer);
        LinearLayout vll = (LinearLayout) findViewById(R.id.videoNotesContainer);
        tll.removeAllViews();
        ill.removeAllViews();
        vll.removeAllViews();
        NoteDatabaseAdapter db = Home.getDatabase();
        ArrayList<Note> notes = db.getNotesFor(puuid);
        ArrayList<ImageClass> images = db.getImagesFor(puuid);
        ArrayList<VideoClass> videos = db.getVideosFor(puuid);

        if(notes.size() <= 0){
            tll.setVisibility(View.GONE);
            findViewById(R.id.textNotesContainerTitle).setVisibility(View.GONE);
            findViewById(R.id.textNotesContainerBoarder).setVisibility(View.GONE);
        }else{
            tll.setVisibility(View.VISIBLE);
            findViewById(R.id.textNotesContainerTitle).setVisibility(View.VISIBLE);
            findViewById(R.id.textNotesContainerBoarder).setVisibility(View.VISIBLE);
        }
        if(images.size() <= 0){
            ill.setVisibility(View.GONE);
            findViewById(R.id.imageNotesContainerTitle).setVisibility(View.GONE);
            findViewById(R.id.imageNotesContainerBoarder).setVisibility(View.GONE);
        }else{
            ill.setVisibility(View.VISIBLE);
            findViewById(R.id.imageNotesContainerTitle).setVisibility(View.VISIBLE);
            findViewById(R.id.imageNotesContainerBoarder).setVisibility(View.VISIBLE);
        }
        if(videos.size() <= 0){
            vll.setVisibility(View.GONE);
            findViewById(R.id.videoNotesContainerTitle).setVisibility(View.GONE);
            findViewById(R.id.videoNotesContainerBoarder).setVisibility(View.GONE);
        }else{
            vll.setVisibility(View.VISIBLE);
            findViewById(R.id.videoNotesContainerTitle).setVisibility(View.VISIBLE);
            findViewById(R.id.videoNotesContainerBoarder).setVisibility(View.VISIBLE);
        }
        if(notes.size() <= 0 && images.size() <= 0 && videos.size() <= 0) {
            findViewById(R.id.emptyNotesList).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.emptyNotesList).setVisibility(View.GONE);
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        for(int i = 0; i < notes.size(); i++){
            Note n = notes.get(i);
            NoteFragment nv = new NoteFragment();
            nv.giveNote(n);
            ft.add(R.id.textNotesContainer,nv);
        }


        for(int i = 0; i < images.size(); i++){
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/StalkerPro/", images.get(i).image_name);
            if(f.exists()){
                ImageFragment img = new ImageFragment();
                img.giveImageData(f,images.get(i));
                ft.add(R.id.imageNotesContainer,img);
            }else{
                Home.getDatabase().deleteImageRow(images.get(i).image_name);
            }
        }

        for (int i = 0; i < videos.size(); i++) {
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/StalkerPro/", videos.get(i).video_name);
            if (f.exists()) {
                VideoFragment vid = new VideoFragment();
                vid.giveVideoData(videos.get(i));
                ft.add(R.id.videoNotesContainer, vid);
            } else {
                Home.makeToast("Deleting Video from Database.");
                Home.getDatabase().deleteVideoRow(videos.get(i).video_name);
            }
        }

        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_person) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this person?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Home.getDatabase().deletePersonRow(puuid);
                            setResult(1);
                            finish();
                        }
                    })
                    .setNegativeButton("Nevermind",null).show();
        }

        if(id == R.id.action_new_note) {
            Intent intent = new Intent(this, CreateNote.class);
            Bundle b = new Bundle();
            b.putString("puuid",puuid.toString());
            intent.putExtras(b);
            startActivityForResult(intent, 1);

        }

        if(id == R.id.action_new_recording) {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoClass = new VideoClass();
            videoClass.puuid = puuid;
            videoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/StalkerPro/", videoClass.video_name);
            Uri tempuri = Uri.fromFile(videoFile);
            takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, 2);
            }
        }

        if(id == R.id.action_new_picture){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageClass = new ImageClass();
            imageClass.puuid = puuid;
            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/StalkerPro/", imageClass.image_name);
            Uri tempuri = Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, 0);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            updateView();
        }
        if(requestCode == 0){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(imageFile.exists()){
                        Home.makeToast(imageFile.getName());
                        Home.getDatabase().insertImage(imageClass);
                    }else{
                        Home.makeToast("Error Saving File");
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    break;
            }
            updateView();
        }
        if(requestCode == 2){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(videoFile.exists()) {
                        Home.makeToast(videoFile.getName());
                        Home.getDatabase().insertVideo(videoClass);
                    }else{
                        Home.makeToast("Error Saving File");
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
            updateView();
        }
    }
}
