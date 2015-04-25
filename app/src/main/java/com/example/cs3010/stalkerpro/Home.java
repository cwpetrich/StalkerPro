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


public class Home extends ActionBarActivity {
    public static Home main;

    public static Home getMain(){return main;}

    public static void makeToast(String st){
        Toast.makeText(main.getApplicationContext(), st, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout ll = (LinearLayout) this.findViewById(R.id.namesContainer);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        nameFragment nf = new nameFragment();
        nf.setName("bob");
        ft.add(R.id.namesContainer,nf);
        ft.commit();
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
            Intent intent = new Intent(this, BlankNote.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
