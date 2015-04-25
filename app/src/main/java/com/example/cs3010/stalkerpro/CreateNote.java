package com.example.cs3010.stalkerpro;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class CreateNote extends ActionBarActivity {

    Note mNote;
    EditText noteText;
    NoteDatabaseAdapter databaseAdapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mNote = new Note();
        databaseAdapter = new NoteDatabaseAdapter(this);
        databaseAdapter.insertNote(mNote);

        noteText = (EditText) findViewById(R.id.createNoteEditText);
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.note = s.toString();
                int countOfChangedNotes = databaseAdapter.updateNoteText(mNote.nuuid, mNote.note);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void printDB(View view)
    {
        Note note = databaseAdapter.getNote(mNote.nuuid);
        Toast.makeText(this, "Note uuid: "+note.nuuid+"\nNote text: "+note.note, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blank_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}