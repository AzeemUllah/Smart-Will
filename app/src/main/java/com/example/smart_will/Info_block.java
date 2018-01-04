package com.example.smart_will;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import utilities.NetworkUtilities;

public class Info_block extends AppCompatActivity {

    Button infNext;
    RadioGroup Rg1, Rg2, Rg3;
    RadioButton r1, r2, r3;
    ImageButton but;
    SessionManager session;
    int count = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_block);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(getApplicationContext());

        if (getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("residueOption");
            } else {
                session.setCurrentClass("residueOption");
            }
        }

        but = (ImageButton) findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Info_block.this, Gifts_and_legacy.class).putExtra("back", true).putExtra("backwards", true));
            }
        });
        Rg1 = (RadioGroup) findViewById(R.id.RG1);
        Rg2 = (RadioGroup) findViewById(R.id.RG2);
        Rg3 = (RadioGroup) findViewById(R.id.RG3);

        r1 = (RadioButton) findViewById(Rg1.getCheckedRadioButtonId());
        r2 = (RadioButton) findViewById(Rg2.getCheckedRadioButtonId());
        r3 = (RadioButton) findViewById(Rg3.getCheckedRadioButtonId());

        infNext = (Button) findViewById(R.id.infNext);
        infNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nextfunc();
            }
        });
    }


    private void Nextfunc()

    {


        r1 = (RadioButton) findViewById(Rg1.getCheckedRadioButtonId());
        r2 = (RadioButton) findViewById(Rg2.getCheckedRadioButtonId());
        r3 = (RadioButton) findViewById(Rg3.getCheckedRadioButtonId());
        if (Rg1.getCheckedRadioButtonId() == -1) {
            Rg1.requestFocus();
            Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();
        } else if (Rg2.getCheckedRadioButtonId() == -1) {
            Rg1.requestFocus();
            Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();

        } else if (Rg3.getCheckedRadioButtonId() == -1) {
            Rg1.requestFocus();
            Toast.makeText(Info_block.this, "All field required", Toast.LENGTH_SHORT).show();

        } else if (r1.getText().toString().equals("No") && r2.getText().toString().equals("No") && r3.getText().toString().equals("No"))

        {
            supportDbConn();





            String url5 = "http://128.199.50.69/api/api-cleanResidue.php";
            String message5 = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                message5 = jsonBody.toString();
            } catch (Exception e) {
                Log.e("JSON error: ", e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
            Info_block.cleanResidue pe5 = new Info_block.cleanResidue();
            pe5.execute(url5, message5);



        } else if ( r2.getText().toString().equals("Yes"))

        {

            supportDbConn();

            String url5 = "http://128.199.50.69/api/api-equalShare.php";
            String message5 = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                message5 = jsonBody.toString();
            } catch (Exception e) {
                Log.e("JSON error: ", e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
            Info_block.equalShare pe5 = new Info_block.equalShare();
            pe5.execute(url5, message5);



        }



        else {
            if(getIntent().hasExtra("back")){
                Log.e("Residue", "has back while redirect");
                supportDbConn();
                Intent intent1 = new Intent(this, Residue_of_estate.class).putExtra("forwards",true).putExtra("back",true);
                startActivity(intent1);
            }
            else {
                supportDbConn();
                Log.e("Residue", "doesnt has back while redirect");
                Intent intent1 = new Intent(this, Residue_of_estate.class).putExtra("stop", "no");
                startActivity(intent1);
            }
        }


    }

    private class cleanResidue extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    Intent intent = new Intent(Info_block.this, Residue_of_estate.class).putExtra("stop","no").putExtra("reuse","yes");
                    //Intent intent = new Intent(Info_block.this, Special_request.class).putExtra("stop","no");
                    startActivity(intent);
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@InfoBlock");
                    Log.e("Api error ", error);
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO: Loader and stuff to add later here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }




    private class supportDecission extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("resut new", result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                Log.e("data response new", jsonResult.toString());
            }
            catch(Exception e){
                Log.e("JSON error new: " , e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO: Loader and stuff to add later here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }






    private class equalShare extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    Intent intent = new Intent(Info_block.this, Special_request.class).putExtra("stop","no");
                    //Intent intent = new Intent(Info_block.this, Special_request.class).putExtra("stop","no");
                    startActivity(intent);
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@InfoBlock");
                    Log.e("Api error ", error);
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO: Loader and stuff to add later here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }




    @Override
    public void onBackPressed() {
        but.performClick();
    }





    private class countResidue extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    String data = jsonResult.getString("Data");
                    count = Integer.parseInt(data);

                    if(count == 0){





                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@InfoBlock");
                    Log.e("Api error ", error);
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO: Loader and stuff to add later here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }








    public void supportDbConn(){
        Log.e("Options:",  r1.getText().toString() + " " + r2.getText().toString() + " " +r3.getText().toString());



        String url6 = "http://128.199.50.69/api/api-support_decission.php";
        String message6 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            jsonBody.put("option1", r1.getText().toString()); //TODO: Session id validation (if/else)
            jsonBody.put("option2", r2.getText().toString()); //TODO: Session id validation (if/else)
            jsonBody.put("option3", r3.getText().toString()); //TODO: Session id validation (if/else)
            message6 = jsonBody.toString();
            Log.e("msg new:",  message6);
        } catch (Exception e) {
            Log.e("JSON error new: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Info_block.supportDecission sd = new Info_block.supportDecission();
        sd.execute(url6, message6);

    }






}




