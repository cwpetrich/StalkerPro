package com.example.cs3010.stalkerpro;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class Home extends ActionBarActivity {
    public static Home main;
    private static NoteDatabaseAdapter DB;

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

    private void updateView(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.namesContainer);
        ll.removeAllViews();
        ArrayList<Person> list = DB.getPeople();
        FragmentManager fm = main.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for(int i =0;i<list.size();i++){
            nameFragment nf = new nameFragment();
            nf.setName(list.get(i).name);
            nf.setPuuid(list.get(i).puuid);
            ft.add(R.id.namesContainer,nf);
        }
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main = this;
        DB = new NoteDatabaseAdapter(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateView();
        /*FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        nameFragment nf = new nameFragment();
        nf.setName("bob");
        ft.add(R.id.namesContainer,nf);
        ft.commit();*/
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
        if (id == R.id.action_quickNote) {
            Intent intent = new Intent(this, CreateNote.class);
            startActivity(intent);
        }

        if (id == R.id.action_newPerson){
            LinearLayout ll = (LinearLayout) findViewById(R.id.newNameContainer);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            NewPersonFragment np = new NewPersonFragment();
            ft.add(R.id.newNameContainer,np);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
