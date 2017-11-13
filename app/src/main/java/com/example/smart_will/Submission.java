package com.example.smart_will;

import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Submission extends AppCompatActivity {

    Button summary, smrt,edit, so,sm;
    ImageButton back;
    SessionManager session;
    CheckBox termsAndConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());




        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("submission");
            }
            else{
                session.setCurrentClass("submission");
            }
        }

        termsAndConditions=(CheckBox)findViewById(R.id.termsAndConditions);
        so = (Button) findViewById(R.id.so);
        sm = (Button) findViewById(R.id.sm);

//        edit=(Button)findViewById(R.id.imageButton);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Submission.this,Edit.class);
//                startActivity(intent);
//
//            }
//        });





        back = (ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Submission.this, Special_request.class);
                startActivity(intent);

            }
        });

//        termsAndConditions.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if ( ((CheckBox)v).isChecked() ) {
//                    so.setVisibility(View.VISIBLE);
//                    sm.setVisibility(View.VISIBLE);
//                }
//                else{
//                    so.setVisibility(View.GONE);
//                    sm.setVisibility(View.GONE);
//                }
//            }
//        });









        summary=(Button)findViewById(R.id.vsummary);

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Submission.this, viewSummary.class);
                intent.putExtras(getIntent());
                startActivity(intent);

            }
        });

        smrt=(Button)findViewById(R.id.sm);
        smrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert();
                Intent intent = new Intent(Submission.this, thankyou2.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        back.performClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(Submission.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }





    public void reg(View view) {
        Intent intern = new Intent(this, thankyou2.class);
        startActivity(intern);
    }
    public void Tc(View view) {
        Intent intern = new Intent(this, Terms_condition.class);
        startActivity(intern);
    }

    public void solicitor(View view) {
        Intent intern = new Intent(this, thankyou3.class);
        startActivity(intern);
    }


}

