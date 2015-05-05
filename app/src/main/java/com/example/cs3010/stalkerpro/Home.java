package com.example.cs3010.stalkerpro;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class Home extends ActionBarActivity {
    public EditText searchText;
    public ArrayList<Person> list;
    public ArrayList<Person> people;
    public static Home main;
    private static NoteDatabaseAdapter DB;
    private final Context context = this;

    public static NoteDatabaseAdapter getDatabase(){
        return DB;
    }

    public static Home getMain(){return main;}

    public static void clearNewNameArea(){
        LinearLayout ll = (LinearLayout) main.findViewById(R.id.newNameContainer);
        ll.removeAllViews();
    }

    public static void addNewName(String name){
        Person p = new Person();
        p.name = name;
        DB.insertPerson(p);
        main.updateView();
    }

    public static void makeToast(String st){
        Toast.makeText(main.getApplicationContext(), st, Toast.LENGTH_LONG).show();
    }

    private void updateView() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.namesContainer);
        ll.removeAllViews();

        list = DB.getPeople();

        searchText = (EditText) findViewById(R.id.homeSearchBox);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                people = DB.getPeople(s.toString());
                LinearLayout searchResults = (LinearLayout) findViewById(R.id.homeSearchResultsList);
                searchResults.removeAllViews();
                for (int i = 0; i < people.size(); i++) {
                    final Person person = people.get(i);
                    TextView tv = new TextView(context);
                    tv.setText(person.name);
                    tv.setTextSize((float) 25.0);
                    tv.setPadding(0, 0, 0, 5);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ViewNotes.class);
                            Bundle b = new Bundle();
                            b.putString("uuid",person.puuid.toString());
                            b.putString("name",person.name);
                            intent.putExtras(b);
                            startActivityForResult(intent, 1);
                        }
                    });
                    searchResults.addView(tv);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        FragmentManager fm = main.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        for (int i = 0; i < list.size(); i++) {
            NameFragment nf = new NameFragment();
            nf.setName(list.get(i).name);
            nf.setPuuid(list.get(i).puuid);
            ft.add(R.id.namesContainer, nf);
        }

        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main = this;
        DB = new NoteDatabaseAdapter(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "StalkerPro");
        if (!f.exists()) {
            f.mkdirs();
        }
        updateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        if (id == R.id.action_search) {
            if (findViewById(R.id.homeSearchContainer).getVisibility() == View.GONE){
                findViewById(R.id.homeSearchContainer).setVisibility(View.VISIBLE);
            }else{
                findViewById(R.id.homeSearchContainer).setVisibility(View.GONE);
            }
        }
        if (id == R.id.action_quickNote) {
            Intent intent = new Intent(this, CreateNote.class);
            startActivity(intent);
        }

        if (id == R.id.action_newPerson){
            clearNewNameArea();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            NewPersonFragment np = new NewPersonFragment();
            ft.add(R.id.newNameContainer,np);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            updateView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
