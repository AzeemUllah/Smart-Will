package com.example.smart_will;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utilities.NetworkUtilities;

public class Gifts extends AppCompatActivity {
    SessionManager session;

    EditText gfts1,gfts2,gfts3,gfts4,gfts5,updateId;
    Button gftNext,gftben;
    ImageButton but;

    int currentNumGifts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(getApplicationContext());

        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("gifts");
            }
            else{
                session.setCurrentClass("gifts");
            }
        }

        checkIfAlready("");


        Log.e("Hi",getIntent().getExtras()+"");


        gfts1=(EditText)findViewById(R.id.gf1);
        gfts2=(EditText)findViewById(R.id.gf2);
        gfts3=(EditText)findViewById(R.id.gf3);
        gfts4=(EditText)findViewById(R.id.gf4);
        gfts5=(EditText)findViewById(R.id.gf5);
        updateId=(EditText)findViewById(R.id.updateGiftsId);

        gftNext=(Button)findViewById(R.id.gftNext);
        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("List length, current" ,session.giftsList.length() + " " + session.giftsCurrent);
                if(getIntent().hasExtra("back") && session.giftsList != null) {
                    if (session.giftsList.length() - session.giftsCurrent != session.giftsList.length()) {
                        insert("backUpdate", Integer.toString(currentNumGifts));
                        startActivity(new Intent(Gifts.this, Gifts.class).putExtra("backwardsCont", true).putExtra("back", true));
                    } else if (session.giftsList.length() - session.giftsCurrent == session.giftsList.length()) {
                        insert("backUpdate", Integer.toString(currentNumGifts));
                        startActivity(new Intent(Gifts.this, Gifts_and_legacy.class).putExtra("back", true));
                    }
                    else{
                        startActivity(new Intent(Gifts.this,Gifts_and_legacy.class));
                    }
                }
                else{
                    startActivity(new Intent(Gifts.this,Gifts_and_legacy.class));
                }
            }
        });

        gftben=(Button)findViewById(R.id.gftBeni);
        gftben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfts1.length()==0)
                {
                    gfts1.requestFocus();
                    gfts1.setError("Please enter first name");
                }

                else if(gfts3.length()==0)
                {
                    gfts3.requestFocus();
                    gfts3.setError("Please enter surname");
                }
                else if(gfts4.length()==0)
                {
                    gfts4.requestFocus();
                    gfts4.setError("Please enter relation");
                }
                else if(gfts5.length()==0)
                {
                    gfts5.requestFocus();
                    gfts5.setError("Please enter Gifts");
                }
                else
                {
                    if(getIntent().hasExtra("back")){
                        if(getIntent().getBooleanExtra("back",false)){
                            if(currentNumGifts < 10){
                                insert("backUpdate",Integer.toString(currentNumGifts));
                                startActivity(new Intent(Gifts.this,Gifts.class).putExtra("dont",true));
                            }
                        }
                    }else{
                        insert("backUpdate",Integer.toString(currentNumGifts));
                        if(getIntent().hasExtra("dont")){
                            startActivity(new Intent(Gifts.this,Gifts.class).putExtra("dont",true));
                        }else {
                            startActivity(new Intent(Gifts.this, Gifts.class));
                        }
                    }
                }
            }
        });


        gftNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfts1.length()==0)
                {
                    gfts1.requestFocus();
                    gfts1.setError("Please enter first name");
                }

                else if(gfts3.length()==0)
                {
                    gfts3.requestFocus();
                    gfts3.setError("Please enter surname");
                }
                else if(gfts4.length()==0)
                {
                    gfts4.requestFocus();
                    gfts4.setError("Please enter relation");
                }
                else if(gfts5.length()==0)
                {
                    gfts5.requestFocus();
                    gfts5.setError("Please enter Gifts");
                }
                else
                {
                    if(getIntent().hasExtra("back") && session.giftsList != null) {
                        Log.e("here", "123456");
                        if (session.giftsList.length() - session.giftsCurrent != 1) {
                            insert("backUpdate", Integer.toString(currentNumGifts));
                            startActivity(new Intent(Gifts.this, Gifts.class).putExtra("forwardCont", true).putExtra("back", true));
                        } else if (session.giftsList.length() - session.giftsCurrent == 1) {
                            insert("backUpdate", Integer.toString(currentNumGifts));
                            session.giftsCurrent = 0;
                            startActivity(new Intent(Gifts.this, Info_block.class));
                        }
                        else{
                            checkIfAlready("next");
                        }
                    }
                    else {
                        Log.e("or here", "123456");
                        insert("backUpdate",Integer.toString(currentNumGifts));
                        if(getIntent().hasExtra("dont")){
                            startActivity(new Intent(Gifts.this,Gifts.class).putExtra("dont",true));
                        }else {
                            startActivity(new Intent(Gifts.this, Info_block.class));
                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(Gifts.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }



    @Override
    public void onBackPressed() {
        but.performClick();
    }


    public void insert(String protocol,String currentNumGifts){
        String url = "http://128.199.50.69/api/api-insertGifts.php";
        if((getIntent().hasExtra("back") && Integer.parseInt(currentNumGifts) > 0) || !updateId.getText().toString().equals("")){
            url = "http://128.199.50.69/api/api-updateGifts.php";
        }
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();

            gfts1=(EditText)findViewById(R.id.gf1);
            gfts2=(EditText)findViewById(R.id.gf2);
            gfts3=(EditText)findViewById(R.id.gf3);
            gfts4=(EditText)findViewById(R.id.gf4);
            gfts5=(EditText)findViewById(R.id.gf5);

            jsonBody.put("firstName", gfts1.getText().toString());
            jsonBody.put("middleName", gfts2.getText().toString());
            jsonBody.put("lastName", gfts3.getText().toString());
            jsonBody.put("relationship", gfts4.getText().toString());
            jsonBody.put("gift", gfts5.getText().toString());

            if((getIntent().hasExtra("back") && Integer.parseInt(currentNumGifts) > 0 ) || !updateId.getText().toString().equals("")){
                jsonBody.put("giftsId", updateId.getText().toString());
            }

            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Log.e("URL",url);
        Gifts.ConnectSave insertObject = new Gifts.ConnectSave();
        insertObject.execute(url,message,protocol,currentNumGifts);
    }

    private class ConnectSave extends AsyncTask<String, Void, String> {
        String protocol = "";
        int currentNum = 0;
        @Override
        protected String doInBackground(String... params) {
            protocol = params[2];
            currentNum = Integer.parseInt(params[3]);
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    if(protocol.equals("add")) {

                        if (getIntent().hasExtra("kill")) {

                            if (getIntent().getBooleanExtra("kill", false) == true) {

                                session.giftsCurrent = -1;
                                startActivity(new Intent(Gifts.this, Info_block.class).putExtra("stop", "no"));
                            }
                        }
                        else if (currentNum == 9) {

                            session.giftsCurrent = -1;
                            startActivity(new Intent(Gifts.this, Info_block.class).putExtra("stop", "no"));
                        }
                        else if (currentNum >= 8) {

                            startActivity(new Intent(Gifts.this, Gifts.class).putExtra("kill", true));
                        }
                        else{

                            startActivity(new Intent(Gifts.this, Gifts.class));
                        }
                    }
                    else if(protocol.equals("next")){
                        session.giftsCurrent = -1;
                        startActivity(new Intent(Gifts.this, Info_block.class).putExtra("stop", "no"));
                    }
                    else if(protocol.equals("backUpdate")){


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Gifts");
                    Log.e("Error","error is: "+ error);
                    Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO: Loader and stuff to add later here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public void checkIfAlready(String protocol){

        if(getIntent().hasExtra("back") ){
            if(getIntent().getBooleanExtra("back",false)){
                Log.e("Got","here");
                if(getIntent().hasExtra("backwards") || getIntent().hasExtra("forwardCont") ||getIntent().hasExtra("forwards") || getIntent().hasExtra("backwardsCont")){
                    String url = "http://128.199.50.69/api/api-getGifts.php";
                    String message = "";
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                        message = jsonBody.toString();
                    } catch (Exception e) {
                        Log.e("JSON error: ", e.toString());
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                    Gifts.getGiftsList pe = new  Gifts.getGiftsList();
                    pe.execute(url, message,"");
                }
            }
        }
        else{
            String message = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                message = jsonBody.toString();
            } catch (Exception e) {
                Log.e("JSON error: ", e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
            String url = "http://128.199.50.69/api/api-getNumGifts.php";
            Gifts.CheckIfAlready checkObject = new Gifts.CheckIfAlready();
            checkObject.execute(url, message, protocol);
        }


    }

    private class CheckIfAlready extends AsyncTask<String, Void, String> {
        String protocol = "";
        @Override
        protected String doInBackground(String... params) {
            protocol = params[2];
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    String data = jsonResult.getString("Data");
                    currentNumGifts = Integer.parseInt(data);

                    if(!(protocol.trim().length() <= 0)){

                        insert(protocol,currentNumGifts+"");
                    }
//                    if(currentNumGifts > 9){
//                        gftben.setVisibility(View.GONE);
//                    }

                    if(currentNumGifts > 0){
                        String url = "http://128.199.50.69/api/api-getGifts.php";
                        String message = "";
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                            message = jsonBody.toString();
                        } catch (Exception e) {
                            Log.e("JSON error: ", e.toString());
                            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                        }
                        Gifts.getGiftsList pe = new  Gifts.getGiftsList();
                        pe.execute(url, message,"");
                    }

                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Gifts");
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



    //comming form back add 'back' (true) + backwards
    // comming from forward add 'back' (true) + forward (back only if it has back)





    private class getGiftsList extends AsyncTask<String, Void, String> {
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
                    JSONArray partialData = new JSONArray(data);
                    session.giftsList = partialData;

                    Log.e("gifts: ", partialData.length() + " " + session.giftsCurrent);


               Log.e("bundle: ", getIntent().getExtras()+"");


                    if(partialData.length() > 9){
                        gftben.setVisibility(View.GONE);
                    }

                    if(session.giftsCurrent < 0 && partialData.length() > 0){
                        if(getIntent().hasExtra("forwards")){
                            if(getIntent().getBooleanExtra("forwards",true)){
                                session.giftsCurrent = 0;
                            }
                        }
                        else if(getIntent().hasExtra("backwards")){
                            if(getIntent().getBooleanExtra("backwards",true)){
                                session.giftsCurrent = partialData.length() - 1;
                            }
                        }
                    }
                    else{
                        if(getIntent().hasExtra("backwardsCont")){
                            if(!getIntent().getBooleanExtra("backwardsCont",false) ){
                                session.giftsCurrent = session.giftsCurrent+1;
                            }
                            else if(getIntent().getBooleanExtra("backwardsCont",true)){
                                session.giftsCurrent = session.giftsCurrent-1;

                            }
                        }
                        else if(getIntent().hasExtra("forwardCont")){
                            if(!getIntent().getBooleanExtra("forwardCont",false) ){
                                session.giftsCurrent = session.giftsCurrent-1;
                            }
                            else if(getIntent().getBooleanExtra("forwardCont",true)){
                                session.giftsCurrent = session.giftsCurrent+1;

                            }
                        }
                    }

                    if(partialData.length() > 0 && !getIntent().hasExtra("dont")){

                        Log.e("fall" , "here" + session.giftsCurrent);

                        if(session.giftsCurrent >= 0){



                            JSONObject lastElement = partialData.getJSONObject(session.giftsCurrent);

                            gfts1.setText(lastElement.getString("first_name"));
                            gfts2.setText(lastElement.getString("mid_name"));
                            gfts3.setText(lastElement.getString("sur_name"));
                            gfts4.setText(lastElement.getString("relationship"));
                            gfts5.setText(lastElement.getString("gifts"));

                            Log.e("Data response: ", data);

                            updateId.setText(lastElement.getString("id"));
                        }
                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
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


}
