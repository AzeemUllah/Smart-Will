package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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
import android.widget.TextView;
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

public class Gifts_and_Legacy2 extends AppCompatActivity {
    ImageButton but;
    EditText edtxt1,edtxt2,edtxt3, cn2, cn3, ga2, ga3, cn1, ga1;
    EditText uptxt;
    TextView txtv;
    SessionManager session;
    Button glNext;
    int currentNumGiftsLegacy = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts_and__legacy2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());

        checkIfAlready();

        txtv = (TextView)findViewById(R.id.addfield);
        edtxt1=(EditText)findViewById(R.id.ed1);
        edtxt2=(EditText)findViewById(R.id.ed2);
        edtxt2.setVisibility(View.GONE);
        edtxt3=(EditText)findViewById(R.id.ed3);
        edtxt3.setVisibility(View.GONE);


        cn1=(EditText)findViewById(R.id.cn1);

        ga1=(EditText)findViewById(R.id.ga1);


        cn2=(EditText)findViewById(R.id.cn2);
        cn2.setVisibility(View.GONE);
        cn3=(EditText)findViewById(R.id.cn3);
        cn3.setVisibility(View.GONE);
        ga2=(EditText)findViewById(R.id.ga2);
        ga2.setVisibility(View.GONE);
        ga3=(EditText)findViewById(R.id.ga3);
        ga3.setVisibility(View.GONE);



        uptxt=(EditText)findViewById(R.id.up1);

        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("giftsLegacy2");
            }
            else{
                session.setCurrentClass("giftsLegacy2");
            }
        }




        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().hasExtra("reuse") ){
                    startActivity(new Intent(Gifts_and_Legacy2.this, Info_block.class).putExtra("back", true));
                }
                else {

                    startActivity(new Intent(Gifts_and_Legacy2.this, Gifts_and_legacy.class));
                }
            }
        });

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtxt2.getVisibility() == View.GONE){
                    edtxt2.setVisibility(View.VISIBLE);
                    cn2.setVisibility(View.VISIBLE);
                    ga2.setVisibility(View.VISIBLE);
                }
                else if (edtxt3.getVisibility() == View.GONE){
                    edtxt3.setVisibility(View.VISIBLE);
                    txtv.setVisibility(View.GONE);
                    cn3.setVisibility(View.VISIBLE);
                    ga3.setVisibility(View.VISIBLE);
                }

            }
        });



        glNext=(Button)findViewById(R.id.gftlegcynext);
        glNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtxt1.length()==0)
                {
                    edtxt1.requestFocus();
                    edtxt1.setError("Please enter");
                }
                else if(cn1.length()==0)
                {
                    cn1.requestFocus();
                    cn1.setError("Please enter");
                }
                else if(ga1.length()==0)
                {
                    ga1.requestFocus();
                    ga1.setError("Please enter");
                }

                else if(edtxt2.length()!=0 || edtxt2.length()!=0){

                    if(edtxt2.length()!=0 ) {
                        if (cn2.length() == 0) {
                            cn2.requestFocus();
                            cn2.setError("Please enter");
                        } else if (ga2.length() == 0) {
                            ga2.requestFocus();
                            ga2.setError("Please enter");
                        }
                        else if(edtxt3.length()!=0 ) {
                            if (cn3.length() == 0) {
                                cn3.requestFocus();
                                cn3.setError("Please enter");
                            } else if (ga3.length() == 0) {
                                ga3.requestFocus();
                                ga3.setError("Please enter");
                            }
                            else{
                                insert();
                            }
                        }
                        else{
                            insert();
                        }
                    }




                }

                else
                {
                    insert();
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
            Intent in=new Intent(Gifts_and_Legacy2.this,logina1.class);
            startActivity(in);
            return(true);
     }
        return(super.onOptionsItemSelected(item));
    }

    public void insert(){
        String url = "http://128.199.50.69/api/api-insertGiftsLegacy2.php";
        if(currentNumGiftsLegacy > 0){
            url = "http://128.199.50.69/api/api-updateGiftsLegacy2.php";
        }
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("foundation1", edtxt1.getText().toString());
            jsonBody.put("foundation2", edtxt2.getText().toString());
            jsonBody.put("foundation3", edtxt3.getText().toString());

            jsonBody.put("charity_no_1", cn1.getText().toString());
            jsonBody.put("charity_no_2", cn2.getText().toString());
            jsonBody.put("charity_no_3", cn3.getText().toString());

            jsonBody.put("gift_1", ga1.getText().toString());
            jsonBody.put("gift_2", ga2.getText().toString());
            jsonBody.put("gift_3", ga3.getText().toString());


            if(currentNumGiftsLegacy > 0){
                jsonBody.put("foundationId", uptxt.getText().toString());
            }
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
            Log.e("to be sent",message);
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Gifts_and_Legacy2.ConnectSave insertObject = new Gifts_and_Legacy2.ConnectSave();
        insertObject.execute(url,message);
    }

    private class ConnectSave extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.postData(params[0],params[1]);

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    if(true){
                        if(getIntent().hasExtra("reuse")){
                            startActivity(new Intent(Gifts_and_Legacy2.this, Special_request.class).putExtra("stop", "no"));
                        }
                        else if(getIntent().hasExtra("back")) {
                            startActivity(new Intent(Gifts_and_Legacy2.this, Info_block.class).putExtra("stop", "no").putExtra("back",true));
                        }
                        else  {
                            startActivity(new Intent(Gifts_and_Legacy2.this, Info_block.class).putExtra("stop", "no"));
                        }


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Gifts legacy 2");
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

    public void checkIfAlready(){
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        String url = "http://128.199.50.69/api/api-getNumGiftsLegacy.php";
        Gifts_and_Legacy2.CheckIfAlready checkObject = new Gifts_and_Legacy2.CheckIfAlready();
        checkObject.execute(url,message);
    }

    private class CheckIfAlready extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
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
                    currentNumGiftsLegacy = Integer.parseInt(data);

                    if(currentNumGiftsLegacy > 0){
                        getGiftsLegacyList();
                    }

                    Log.e("Current Num Gifts2",Integer.toString(currentNumGiftsLegacy));
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

    public void getGiftsLegacyList(){
        String url = "http://128.199.50.69/api/api-getGiftsAndLegacy.php";
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Gifts_and_Legacy2.GetGiftsLegacyList pe = new Gifts_and_Legacy2.GetGiftsLegacyList();
        pe.execute(url, message);
    }



    @Override
    public void onBackPressed() {
        but.performClick();
    }

    private class GetGiftsLegacyList extends AsyncTask<String, Void, String> {
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
                    JSONObject lastElement = partialData.getJSONObject(0);
                    Log.e("Data response: ",data);
                    fillData(lastElement.getString("foundation1"),lastElement.getString("foundation2"),lastElement.getString("foundation3"),lastElement.getString("id"),
                            lastElement.getString("charity_no_1"),lastElement.getString("charity_no_2"),lastElement.getString("charity_no_3"),
                            lastElement.getString("gift_1"),lastElement.getString("gift_2"),lastElement.getString("gift_3"));
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

    public void fillData(String... foundations){
        if(!foundations[3].isEmpty()){
            uptxt.setText(foundations[3]);
        }
        if(!foundations[0].isEmpty()){
            edtxt1.setText(foundations[0]);
            cn1.setText(foundations[4]);
            ga1.setText(foundations[7]);

        }
        if(!foundations[1].isEmpty()){
            edtxt2.setText(foundations[1]);
            edtxt2.setVisibility(View.VISIBLE);
            cn2.setText(foundations[5]);
            cn2.setVisibility(View.VISIBLE);
            ga2.setText(foundations[8]);
            ga2.setVisibility(View.VISIBLE);
        }
        if(!foundations[2].isEmpty()){
            edtxt3.setText(foundations[2]);
            edtxt3.setVisibility(View.VISIBLE);

            cn3.setText(foundations[6]);
            cn3.setVisibility(View.VISIBLE);
            ga3.setText(foundations[9]);
            ga3.setVisibility(View.VISIBLE);

            txtv.setVisibility(View.GONE);
        }
    }

}
