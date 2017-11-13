package com.example.smart_will;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import utilities.NetworkUtilities;

public class Guardians extends AppCompatActivity  {
    private TextView mDisplayDate,showdate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

//        public void reg(View view) {
//            Intent intern = new Intent(this, Gifts_and_legacy.class);
//            startActivity(intern);
//        }
//String url_guardians ="http://110.37.231.10:8080/projects/Test_laravel/public/guardians";

    SessionManager session;
     EditText grdn1,grdn2,grdn3,grdn4,grdn5,grdn7, updateId;
     TextView grdn6;
     Button Grd_skip,guar_add;
    ImageButton but;
     Button btn_Executor;
    static int count1;
    int currentNumExecutor;
    String passingArray = "";
    boolean goAhead = false;

     @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_guardians);
         // Find the toolbar view inside the activity layout
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         // Sets the Toolbar to act as the ActionBar for this Activity window.
         // Make sure the toolbar exists in the activity and is not null
         setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayShowTitleEnabled(false);
         session = new SessionManager(getApplicationContext());
         //HashMap<String, String> user = session.getUserDetails();
        // final String uid = user.get(SessionManager.KEY_NAME);
         //--Radiogroup--//
         mDisplayDate = (TextView) findViewById(R.id.picdate);
         showdate1 = (TextView) findViewById(R.id.showdate_guar);


         if(getIntent().hasExtra("stop")) {
             if (getIntent().getStringExtra("stop").equals("yes")) {
                 session.statusLogin("guardian");
             }
             else{
                 session.setCurrentClass("guardian");
             }
         }


        if(getIntent().hasExtra("forwardProp")){
if(getIntent().getIntExtra("num",-1) == 0) {
    String url = "http://128.199.50.69/api/api-getGuardians.php";
    String message = "";
    try {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
        message = jsonBody.toString();
    } catch (Exception e) {
        Log.e("JSON error: ", e.toString());
        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
    }
    Guardians.getExecutersListForward pe = new Guardians.getExecutersListForward();
    pe.execute(url, message, "forward");
}
else if(getIntent().getIntExtra("num",-1) == 1){
    String url = "http://128.199.50.69/api/api-getGuardians.php";
    String message = "";
    try {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
        message = jsonBody.toString();
    } catch (Exception e) {
        Log.e("JSON error: ", e.toString());
        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
    }
    Guardians.getExecutersListForward pe = new Guardians.getExecutersListForward();
    pe.execute(url, message, "forward1");
}
        }
         else if(getIntent().hasExtra("back")) {
             String url = "http://128.199.50.69/api/api-getGuardians.php";
             String message = "";
             try {
                 JSONObject jsonBody = new JSONObject();
                 jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                 message = jsonBody.toString();
             } catch (Exception e) {
                 Log.e("JSON error: ", e.toString());
                 Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
             }
             Guardians.getExecutersList pe = new Guardians.getExecutersList();
             pe.execute(url, message,"");
         }
         else if(getIntent().getIntExtra("middleOutNow", -1) >0){
            String url = "http://128.199.50.69/api/api-getGuardians.php";
            String message = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                message = jsonBody.toString();
            } catch (Exception e) {
                Log.e("JSON error: ", e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
            Guardians.getExecutersList pe = new Guardians.getExecutersList();
            pe.execute(url, message,"middleOutNow");
        }




         mDisplayDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Calendar cal = Calendar.getInstance();
                 int year = cal.get(Calendar.YEAR);
                 int month = cal.get(Calendar.MONTH);
                 int day = cal.get(Calendar.DAY_OF_MONTH);

                 DatePickerDialog dialog = new DatePickerDialog(
                         Guardians.this,
                         android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                         mDateSetListener,
                         year,month,day);
                 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 dialog.show();
             }
         });
         mDateSetListener = new DatePickerDialog.OnDateSetListener() {
             @Override
             public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                 month = month + 1;
                 // Log.d(, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                 String date = year+"-"+ month+"-"+day ;
                 showdate1.setText(date);
                 mDisplayDate.setText(date);
                 grdn6.clearFocus();
             }
         };

         SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
         SharedPreferences.Editor editor = sp.edit();
         editor.putInt("your_int_key", count1);
         editor.commit();



         grdn1 = (EditText)findViewById(R.id.Grd1);
         grdn2 = (EditText)findViewById(R.id.Grd2);
         updateId = (EditText)findViewById(R.id.updateGuardianId);
         grdn3 = (EditText)findViewById(R.id.Grd3);
         grdn4 = (EditText)findViewById(R.id.Grd4);
         grdn5 = (EditText)findViewById(R.id.Grd5);
         grdn6 = (TextView)findViewById(R.id.showdate_guar);
         grdn7 = (EditText)findViewById(R.id.Grd7);
         Grd_skip=(Button)findViewById(R.id.grdSkip);
         guar_add=(Button)findViewById(R.id.grdGrdn);

         but=(ImageButton)findViewById(R.id.back);
         but.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if(getIntent().hasExtra("back")){
                     if(getIntent().hasExtra("num")){
                         if(getIntent().getIntExtra("num",-1) == 0 && goAhead){
                             startActivity(new Intent(Guardians.this,Guardians.class).putExtra("back",true).putExtra("num",1).putExtra("middleOut",0));
                         }
                         else{
                             startActivity(new Intent(Guardians.this,Executors.class).putExtra("back",true).putExtra("fromGuardians",true).putExtra("middleOut",0));
                         }
                     }
                 }
                 else{
                     startActivity(new Intent(Guardians.this,Executors.class).putExtra("back",true).putExtra("fromGuardians",true));
                 }




             }
         });
         Grd_skip.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 if(!updateId.getText().toString().trim().equals("") && !updateId.getText().toString().trim().equals(null)){
                     String message = "";
                     String url = "http://128.199.50.69/api/api-updateGuardian.php";
                     try {
                         JSONObject jsonBody = new JSONObject();
                         jsonBody.put("firstName", grdn1.getText().toString());
                         jsonBody.put("middleName", grdn2.getText().toString());
                         jsonBody.put("lastName", grdn3.getText().toString());
                         jsonBody.put("address", grdn4.getText().toString());
                         jsonBody.put("postalCode", grdn5.getText().toString());
                         jsonBody.put("date", showdate1.getText().toString());
                         jsonBody.put("relation", grdn7.getText().toString());
                         jsonBody.put("user_id", session.getSession());
                         jsonBody.put("updateId", updateId.getText().toString()); //TODO: Session id validation (if/else)
                         message = jsonBody.toString();
                     }
                     catch (Exception e){
                         Log.e("JSON error: " , e.toString());
                         Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                     }
                     Guardians.UpdateExec pe = new Guardians.UpdateExec();
                     pe.execute(url,message);

                     if (getIntent().getIntExtra("num", -1) == 0 && currentNumExecutor > 0 && getIntent().hasExtra("forwardProp")) {
                         startActivity(new Intent(Guardians.this, Guardians.class).putExtra("forwardProp", true).putExtra("numGuardian", 0).putExtra("back", true).putExtra("num", 1));
                     } else if (getIntent().getIntExtra("middleOut", -1) == 0) {
                         startActivity(new Intent(Guardians.this, Guardians.class).putExtra("middleOutNow", 1));
                     } else if (getIntent().getIntExtra("middleOutNow", -1) == 1) {
                         //TODO code here for saving
                     } else {
                         Intent intent = new Intent(Guardians.this, Gifts_and_legacy.class);
                         intent.putExtra("stop", "no");
                         startActivity(intent);
                     }


                 }

                 else {

                     if (getIntent().getIntExtra("num", -1) == 0 && currentNumExecutor > 0 && getIntent().hasExtra("forwardProp")) {
                         startActivity(new Intent(Guardians.this, Guardians.class).putExtra("forwardProp", true).putExtra("numGuardian", 0).putExtra("back", true).putExtra("num", 1));
                     } else if (getIntent().getIntExtra("middleOut", -1) == 0) {
                         startActivity(new Intent(Guardians.this, Guardians.class).putExtra("middleOutNow", 1));
                     } else if (getIntent().getIntExtra("middleOutNow", -1) == 1) {
                         //TODO code here for saving
                     } else {
                         Intent intent = new Intent(Guardians.this, Gifts_and_legacy.class);
                         intent.putExtra("stop", "no");
                         startActivity(intent);
                     }

                 }

             }
         });


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
         String url = "http://128.199.50.69/api/api-getNumGuardians.php";
         Guardians.getCurrentNumExecutors pe = new Guardians.getCurrentNumExecutors();
         pe.execute(url,message);



         guar_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SharedPreferences sp1 = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                 int myIntValue = sp1.getInt("your_int_key", -1);
                 count1++;
                 String a=Integer.toString(myIntValue);

                 if(grdn1.length()==0)
                 {
                     grdn1.requestFocus();
                     grdn1.setError("Please enter first name");
                 }

                 else if(grdn3.length()==0)
                 {
                     grdn3.requestFocus();
                     grdn3.setError("Please enter surname");
                 }
                 else if(grdn4.length()==0)
                 {
                     grdn4.requestFocus();
                     grdn4.setError("Please enter address");
                 }
                 else if(grdn5.length()==0)
                 {
                     grdn5.requestFocus();
                     grdn5.setError("Please enter Post Code");
                 }
                 else if(grdn6.length()==0)
                 {
                     grdn6.requestFocus();
                     grdn6.setError("Please Select date");
                 }

                 else if(grdn6.length() != 0){
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     Date date = null;
                     try {
                         date = sdf.parse(grdn6.getText().toString());
                     } catch (java.text.ParseException e) {
                         e.printStackTrace();
                     }
                     Calendar dob = Calendar.getInstance();
                     dob.setTime(date);

                     Calendar today = Calendar.getInstance();

                     int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                     if(age < 18 ){
                         grdn6.requestFocus();
                         grdn6.setError("Minimum age Requirment is 18");
                     }
                     else{

                  if(grdn7.length()==0)
                         {
                             grdn7.requestFocus();
                             grdn7.setError("Please enter Your Relation");
                         }

                         else
                         {
                             String url = "http://128.199.50.69/api/api-insertGuardian.php";
                             if(getIntent().hasExtra("back")) {
                                 if (getIntent().getExtras().getBoolean("back") && !updateId.getText().toString().trim().equals("") && !updateId.getText().toString().trim().equals(null)) {
                                     url = "http://128.199.50.69/api/api-updateGuardian.php";
                                     Log.e("Update URL " , "abc " + updateId.getText());
                                 }
                             }
                             String message = "";
                             try {
                                 JSONObject jsonBody = new JSONObject();
                                 jsonBody.put("firstName", grdn1.getText().toString());
                                 jsonBody.put("middleName", grdn2.getText().toString());
                                 jsonBody.put("lastName", grdn3.getText().toString());
                                 jsonBody.put("address", grdn4.getText().toString());
                                 jsonBody.put("postalCode", grdn5.getText().toString());
                                 jsonBody.put("date", showdate1.getText().toString());
                                 jsonBody.put("relation", grdn7.getText().toString());
                                 jsonBody.put("user_id", session.getSession());
                                 jsonBody.put("updateId", updateId.getText().toString()); //TODO: Session id validation (if/else)
                                 message = jsonBody.toString();
                             }
                             catch (Exception e){
                                 Log.e("JSON error: " , e.toString());
                                 Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                             }

                             Guardians.ParallelExec pe = new Guardians.ParallelExec();
                             if(getIntent().hasExtra("forwardProp") && goAhead == true){
                                 pe.execute(url,message,"forward");
                             }
                             else if(currentNumExecutor == 1){
                                 pe.execute(url,message,"forwardProp");
                             }
                             else if(currentNumExecutor >= 2){
                                 pe.execute(url,message,"skip");
                             }
                             else{
                                 pe.execute(url,message,"add");
                             }



//                     Insert_guardians(uid);
//                     //guardiansData(uid);
//                     Intent i = new Intent(Guardians.this, Guardians.class);
//                     startActivity(i);
                         }
                     }
                 }



             }
         });

        }



    private class ParallelExec extends AsyncTask<String, Void, String> {
        String redirect;
        @Override
        protected String doInBackground(String... params) {
            redirect = params[2];
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    if(getIntent().hasExtra("numGuardian")){
                        if(getIntent().getIntExtra("numGuardian",-1) == 2){
                            startActivity(new Intent(Guardians.this, Gifts_and_legacy.class));
                        }

                    }
                    if(redirect.equals("forward") && goAhead){
                        startActivity(new Intent(Guardians.this,Guardians.class).putExtra("forwardProp",true).putExtra("back",true).putExtra("num",1));
                    }
                    if(redirect.equals("forwardProp") && getIntent().hasExtra("back")){
                        startActivity(new Intent(Guardians.this,Guardians.class).putExtra("forwardProp",true).putExtra("back",true).putExtra("num",1).putExtra("numGuardian",2));
                    }
                    else if(redirect.equals("forwardProp")){
                        startActivity(new Intent(Guardians.this,Gifts_and_legacy.class).putExtra("forwardProp",true).putExtra("back",true).putExtra("num",1).putExtra("numGuardian",2));
                    }
                    else if(redirect.equals("add")){
                        startActivity(new Intent(Guardians.this, Guardians.class));
                    }
                    else if(redirect.equals("skip")){
                        startActivity(new Intent(Guardians.this, Gifts_and_legacy.class));
                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
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






    private class UpdateExec extends AsyncTask<String, Void, String> {

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

                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
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



    private class getCurrentNumExecutors extends AsyncTask<String, Void, String> {
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
                    currentNumExecutor = Integer.parseInt(data);
                    Log.e("Current Num Exex",Integer.toString(currentNumExecutor));
                    if(currentNumExecutor >= 2){
                        guar_add.setVisibility(View.GONE);
                        Grd_skip.setText("Next");
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







    private class getExecutersList extends AsyncTask<String, Void, String> {
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

                    passingArray = data;

                    Log.e("Passing Data is",passingArray.toString());


                    if(!passingArray.equals("") || !passingArray.equals(null)){

                        if(getIntent().hasExtra("middleOutNow")){
                            JSONObject lastElement = partialData.getJSONObject(1);
                            grdn1.setText(lastElement.getString("first_name"));
                            grdn2.setText(lastElement.getString("mid_name"));
                            grdn3.setText(lastElement.getString("sur_name"));
                            grdn4.setText(lastElement.getString("address"));
                            grdn5.setText(lastElement.getString("post_code"));
                            grdn7.setText(lastElement.getString("relationship"));
                            showdate1.setText(lastElement.getString("date_of_birth"));
                            mDisplayDate.setText(lastElement.getString("date_of_birth"));
                            updateId.setText(lastElement.getString("id"));

                            if(partialData.length() > 1){
                                goAhead = false;
                            }
                        }

                        if(!(getIntent().getIntExtra("num",-1) < 0)){
                            if(partialData.length() > 0){


                                if(getIntent().getIntExtra("num",-1) == 0 && getIntent().hasExtra("back")) {
                                    JSONObject lastElement = partialData.getJSONObject(partialData.length()-1);
                                    grdn1.setText(lastElement.getString("first_name"));
                                    grdn2.setText(lastElement.getString("mid_name"));
                                    grdn3.setText(lastElement.getString("sur_name"));
                                    grdn4.setText(lastElement.getString("address"));
                                    grdn5.setText(lastElement.getString("post_code"));
                                    grdn7.setText(lastElement.getString("relationship"));
                                    showdate1.setText(lastElement.getString("date_of_birth"));
                                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                                    updateId.setText(lastElement.getString("id"));

                                    if(partialData.length() > 1){
                                        goAhead = true;
                                    }


                                }

                                if(getIntent().getIntExtra("num",-1) == 1 && getIntent().hasExtra("back")) {
                                    JSONObject lastElement = partialData.getJSONObject(0);
                                    grdn1.setText(lastElement.getString("first_name"));
                                    grdn2.setText(lastElement.getString("mid_name"));
                                    grdn3.setText(lastElement.getString("sur_name"));
                                    grdn4.setText(lastElement.getString("address"));
                                    grdn5.setText(lastElement.getString("post_code"));
                                    grdn7.setText(lastElement.getString("relationship"));
                                    showdate1.setText(lastElement.getString("date_of_birth"));
                                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                                    updateId.setText(lastElement.getString("id"));

                                    if(partialData.length() > 1){
                                        goAhead = false;
                                    }


                                }

                            }
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



    @Override
    public void onBackPressed() {
        but.performClick();
    }




    // TODO FORWARD PROP HERE

    private class getExecutersListForward extends AsyncTask<String, Void, String> {
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

                    passingArray = data;

                    Log.e("Passing Data is",passingArray.toString());


                    if(!passingArray.equals("") || !passingArray.equals(null)){
                        if((getIntent().getBooleanExtra("forwardProp",false))){
                            if(partialData.length() > 0){

                                if(getIntent().getIntExtra("num",-1) == 0 && partialData.length() > 0 && getIntent().hasExtra("forwardProp")) {
                                    JSONObject lastElement = partialData.getJSONObject(0);
                                    grdn1.setText(lastElement.getString("first_name"));
                                    grdn2.setText(lastElement.getString("mid_name"));
                                    grdn3.setText(lastElement.getString("sur_name"));
                                    grdn4.setText(lastElement.getString("address"));
                                    grdn5.setText(lastElement.getString("post_code"));
                                    grdn7.setText(lastElement.getString("relationship"));
                                    showdate1.setText(lastElement.getString("date_of_birth"));
                                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                                    updateId.setText(lastElement.getString("id"));

                                    if(partialData.length() > 1){
                                        goAhead = true;
                                    }
                                    Log.e("JSON DATA IN " , "a" );

                                }
                                if(getIntent().getIntExtra("num",-1) == 0 && partialData.length() > 0 && getIntent().hasExtra("forwardProp")) {
                                    JSONObject lastElement = partialData.getJSONObject(0);
                                    grdn1.setText(lastElement.getString("first_name"));
                                    grdn2.setText(lastElement.getString("mid_name"));
                                    grdn3.setText(lastElement.getString("sur_name"));
                                    grdn4.setText(lastElement.getString("address"));
                                    grdn5.setText(lastElement.getString("post_code"));
                                    grdn7.setText(lastElement.getString("relationship"));
                                    showdate1.setText(lastElement.getString("date_of_birth"));
                                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                                    updateId.setText(lastElement.getString("id"));

                                    if(partialData.length() > 1){
                                        goAhead = true;
                                    }
                                    Log.e("JSON DATA IN " , "a" );

                                }

                                else if(getIntent().getIntExtra("num",-1) == 1 && getIntent().hasExtra("forwardProp")) {
                                    JSONObject lastElement = partialData.getJSONObject(1);

                                    grdn1.setText(lastElement.getString("first_name"));
                                    grdn2.setText(lastElement.getString("mid_name"));
                                    grdn3.setText(lastElement.getString("sur_name"));
                                    grdn4.setText(lastElement.getString("address"));
                                    grdn5.setText(lastElement.getString("post_code"));
                                    grdn7.setText(lastElement.getString("relationship"));
                                    showdate1.setText(lastElement.getString("date_of_birth"));
                                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                                    updateId.setText(lastElement.getString("id"));

                                    if(partialData.length() > 1){
                                        goAhead = false;
                                    }

                                    Log.e("YOYO",".");

                                }


                            }
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
            Intent in=new Intent(Guardians.this,logina1.class);
            startActivity(in);
            return(true);

    }
        return(super.onOptionsItemSelected(item));
    }









//    private void guardiansData(final String uid){
//
//            Intent intent = new Intent(this, Gifts_and_legacy.class);
//            intent.putExtra("gar1",grdn1.getText().toString());
//            intent.putExtra("gar2",grdn2.getText().toString());
//            intent.putExtra("gar3",grdn3.getText().toString());
//            intent.putExtra("gar4",grdn4.getText().toString());
//            intent.putExtra("gar5",grdn5.getText().toString());
//            intent.putExtra("gar6",grdn6.getText().toString());
//            intent.putExtra("gar7",grdn7.getText().toString());
//
//           // String uid = getIntent().getExtras().getString("u_id");
//            intent.putExtra("u_id",uid);
//
//
//            intent.putExtras(getIntent());
//            startActivity(intent);
//
//        }

//    public void Insert_guardians(final String uid){
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_guardians, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//
//                //Toast.makeText(getApplication(), "44444", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(Guardians.this, error + "", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                Map<String,String> params=new HashMap<String, String>();
//
////                String newString = newString.getText().toString();
////                String email = mail.getText().toString();
////                String passwrd = pass.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtras(getIntent());
//                String gr1 =grdn1.getText().toString();
//                String gr2=grdn2.getText().toString();
//                String gr3 = grdn3.getText().toString();
//                String gr4 = grdn4.getText().toString();
//                String gr5 = grdn5.getText().toString();
//
//
//                String gr6 =grdn6.getText().toString();
//                String gr7 = grdn7.getText().toString();
//
//
//                intent.putExtra("u_id",uid);
//
//
//                params.put("name", gr1);
//                params.put("mdname", gr2);
//                params.put("srname", gr3);
//                params.put("adres", gr4);
//                params.put("pst-code", gr5);
//                params.put("dob", gr6);
//                params.put("relation", gr7);
//                params.put("uid",uid);
//
//
//                return params;
//            }
//
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        int socketTimeout = 30000;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
//    }


        public void datepicker(View view) {

            com.example.smart_will.Executors.DatePickerFregment fragment = new com.example.smart_will.Executors.DatePickerFregment();
            fragment.show(getSupportFragmentManager(), "date");
        }

        private void setDate(final Calendar calendar) {

            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            ((TextView) findViewById(R.id.Grd6)).setText(dateFormat.format(calendar.getTime()));
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }


        public static class DatePickerFregment extends DialogFragment {


            public Dialog onCreateDialog(Bundle savedInstancestate) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(),
                        (DatePickerDialog.OnDateSetListener)
                                getActivity(), year, month, day);

            }

        }
}
