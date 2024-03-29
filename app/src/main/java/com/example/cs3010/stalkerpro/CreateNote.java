package com.example.cs3010.stalkerpro;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;


public class CreateNote extends ActionBarActivity {

    Note mNote;
    ArrayList<Person> mPeople;
    EditText noteText;
    EditText searchText;
    NoteDatabaseAdapter databaseAdapter;
    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Setting value ahead of time
        //Need to put it somewhere more useful probably
        setResult(1);
        //Using specifically for when ViewNotes
        //calls createNote, and then updates view.


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        databaseAdapter = new NoteDatabaseAdapter(this);
        Bundle b = getIntent().getExtras();
        String inPuuid = b!= null && b.containsKey("puuid") ? b.getString("puuid") : null;
        if(inPuuid != null){
            mNote = new Note();
            mNote.puuid = UUID.fromString(inPuuid);
            String inNuuid = b.containsKey("nuuid") ? b.getString("nuuid") : null;
            if(inNuuid != null){
                mNote.nuuid = UUID.fromString(inNuuid);
                mNote.note = b.getString("note");
                mNote.created_at = b.getString("created_at");
                mNote.modified_at = b.getString("modified_at");
            }else{
                databaseAdapter.insertNote(mNote);
            }
        }else {
            mNote = new Note();
            databaseAdapter.insertNote(mNote);
        }



        noteText = (EditText) findViewById(R.id.createNoteEditText);
        noteText.setText(mNote.note);
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNote.note = s.toString();
                int countOfChangedNotes = databaseAdapter.updateNoteText(mNote.nuuid, mNote.note);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchText = (EditText) findViewById(R.id.createNoteSearchBox);
        searchText.setText(Home.getDatabase().getPersonName(mNote.puuid));
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LinearLayout searchResults = (LinearLayout) findViewById(R.id.createNoteSearchResultsList);
                if (s.toString().length() >= 1){
                    mPeople = databaseAdapter.getPeople(s.toString());
                    searchResults.removeAllViews();
                    for (int i = 0; i < mPeople.size(); i++) {
                        final Person person = mPeople.get(i);
                        TextView tv = new TextView(context);
                        tv.setText(person.name);
                        tv.setTextSize((float) 25.0);
                        tv.setPadding(0, 0, 0, 5);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchText.setText(person.name);
                                databaseAdapter.updateNotePuuid(mNote.nuuid, person.puuid);
                            }
                        });
                        searchResults.addView(tv);
                    }
                }else{
                    searchResults.removeAllViews();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void deleteNote(){
        Home.getDatabase().deleteNoteRow(mNote.nuuid);
        finish();
    }

    public void saveNote(){
        onBackPressed();
    }

    public void printDB(View view)
    {
        Note note = databaseAdapter.getNote(mNote.nuuid);
        Toast.makeText(this, "Note uuid: "+note.nuuid+"\nNote text: "+note.note, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveNote();
            return true;
        }
        if (id == R.id.action_delete){
            deleteNote();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
