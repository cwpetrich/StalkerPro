package com.example.cs3010.stalkerpro;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.UUID;


public class ViewNotes extends ActionBarActivity {
    private UUID puuid;

    private void updateView(){
        LinearLayout ll = (LinearLayout)findViewById(R.id.notesContainer);
        ll.removeAllViews();
        NoteDatabaseAdapter db = Home.getDatabase();
        ArrayList<Note> notes = db.getNotesFor(puuid);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for(int i = 0; i < notes.size(); i++){
            Note n = notes.get(i);
            noteViewFragment nv = new noteViewFragment();
            nv.giveNote(n);
            ft.add(R.id.notesContainer,nv);
        }
        ft.commit();
    }

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
            Intent intent = new Intent(this, SoundRecording.class);
            Bundle b = new Bundle();
            b.putString("puuid",puuid.toString());
            intent.putExtras(b);
            startActivityForResult(intent, 1);
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
    }
}
