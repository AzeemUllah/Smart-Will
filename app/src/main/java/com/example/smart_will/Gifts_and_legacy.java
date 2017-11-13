package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.HashMap;

public class Gifts_and_legacy extends AppCompatActivity {
SessionManager session;
    Button btn1, btn2;
    String A1;
    ImageButton but;
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    public void no(View view) {
        final Intent intern = new Intent(this, Gifts_and_Legacy2.class);
        startActivity(intern);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts_and_legacy);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());

        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("giftsLegacy");
            }
            else{
                session.setCurrentClass("giftsLegacy");
            }
        }



        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gifts_and_legacy.this,Guardians.class).putExtra("back",true).putExtra("num", 0));
            }
        });
        btn1 = (Button) findViewById(R.id.glYes);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                giftsLegacyIntent();




            }

        });


        btn2 =(Button)findViewById(R.id.glNo);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                giftsLegacyIntentno();
            }
        });

    }


    @Override
    public void onBackPressed() {
        but.performClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(Gifts_and_legacy.this,logina1.class);
            startActivity(in);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    private void giftsLegacyIntent() {

        A1="true";
        Intent intent;

        if(getIntent().hasExtra("back")) {
            intent = new Intent(this, Gifts.class).putExtra("forwards", true).putExtra("back",true);
        }
        else {
          intent = new Intent(this, Gifts.class).putExtra("stop", "no");
        }
        startActivity(intent);
    }


    private void giftsLegacyIntentno(){

        A1= "false";
        Intent intent;
        if(getIntent().hasExtra("back")) {
            intent = new Intent(this, Gifts_and_Legacy2.class).putExtra("forwards", true).putExtra("back",true);
        }
        else {
            intent = new Intent(this, Gifts_and_Legacy2.class).putExtra("stop", "no");
        }
        startActivity(intent);

    }






}
