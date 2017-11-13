package com.example.smart_will;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import utilities.NetworkUtilities;

public class Executors extends AppCompatActivity {

    //    public void reg(View view) {
//        Intent intern = new Intent(this, Guardians.class);
//        startActivity(intern);
//    }
//    String url_executor ="http://110.37.231.10:8080/projects/Test_laravel/public/executor";





    EditText et1, et2, et3, et4, et5, et7;
    TextView et6,updateId;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate,showdate1;
    Button exenext, exeadd;
    ImageButton but;
    Button btn_Executor;
    static int count;
    SessionManager session;
    int currentNumExecutor;
    String passingArray = "";
    String lastElementGlobal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executors);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("your_int_key", count);
        editor.commit();

//--Executor data getting--//
        et1 = (EditText) findViewById(R.id.Ex_t1);
        et2 = (EditText) findViewById(R.id.Ex_t2);
        et3 = (EditText) findViewById(R.id.Ex_t3);
        et4 = (EditText) findViewById(R.id.Ex_t4);
        et5 = (EditText) findViewById(R.id.Ex_t5);
        et6 = (TextView) findViewById(R.id.showdate_ex);
        et7 = (EditText) findViewById(R.id.Ex_t7);
        updateId = (TextView) findViewById(R.id.updateExecuterId);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
//        final String uid = getIntent().getExtras().getString("u_id");
//--Executor data getting--//
        mDisplayDate = (TextView) findViewById(R.id.picdate);
        showdate1 = (TextView) findViewById(R.id.showdate_ex);



        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("executer");
            }
            else{
                session.setCurrentClass("executer");
            }
        }
        else{

        }



        if(getIntent().hasExtra("back")) {
            if(!(getIntent().hasExtra("innerLoop"))) {
                if (getIntent().getExtras().getBoolean("back") && !getIntent().hasExtra("fromGuardians")) {
                    Log.e("hi", "Back");
                    String url = "http://128.199.50.69/api/api-getExecutor.php";
                    String message = "";
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                        message = jsonBody.toString();
                    } catch (Exception e) {
                        Log.e("JSON error: ", e.toString());
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                    Executors.getExecutersList pe = new Executors.getExecutersList();
                    pe.execute(url, message,"");
                }
                else if(getIntent().hasExtra("fromGuardians")){
                    if(!getIntent().hasExtra("previousData")) {
                        Log.e("hi", "guardian");
                        String url = "http://128.199.50.69/api/api-getExecutor.php";
                        String message = "";
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                            message = jsonBody.toString();
                        } catch (Exception e) {
                            Log.e("JSON error: ", e.toString());
                            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                        }
                        Executors.getExecutersList pe = new Executors.getExecutersList();
                        pe.execute(url, message, "fromGuardians");
                    }
                }
            }
            else if(getIntent().hasExtra("forwardProp")){
                try {
                    JSONArray partialData = new JSONArray(getIntent().getStringExtra("data").toString());
                    JSONObject lastElement = partialData.getJSONObject(0);


                    if(partialData.length()>1) {
                        passingArray = "[";
                        for (int i = 1; i < partialData.length(); i++) {
                            passingArray += partialData.getString(i);
                            passingArray += ",";
                        }
                        passingArray = passingArray.substring(0,passingArray.length()-1);
                        passingArray += "]";
                    }

                    Log.e("forward prop",passingArray.toString());


                    et1.setText(lastElement.getString("first_name"));
                    et2.setText(lastElement.getString("mid_name"));
                    et3.setText(lastElement.getString("sur_name"));
                    et4.setText(lastElement.getString("address"));
                    et5.setText(lastElement.getString("post_code"));
                    et7.setText(lastElement.getString("relationship"));
                    showdate1.setText(lastElement.getString("date_of_birth"));
                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                    updateId.setText(lastElement.getString("id"));



                    //Log.e("Azeem Ullah par dat",partialData.toString());
                    //JSONObject lastElement = partialData.getJSONObject(partialData.length() - 1);
                }
                catch(Exception e){
                    Log.e("Json Exception " , e.toString());
                }
            }
            else if((getIntent().hasExtra("innerLoop"))){
                try {
                    JSONArray partialData = new JSONArray(getIntent().getStringExtra("data").toString());
                    JSONObject lastElement = partialData.getJSONObject(partialData.length()-1);

                    if(partialData.length()>1) {
                        passingArray = "[";
                        for (int i = 0; i < partialData.length() - 1; i++) {
                            passingArray += partialData.getString(i);
                            passingArray += ",";
                        }
                        passingArray = passingArray.substring(0,passingArray.length()-1);
                        passingArray += "]";
                    }


                    et1.setText(lastElement.getString("first_name"));
                    et2.setText(lastElement.getString("mid_name"));
                    et3.setText(lastElement.getString("sur_name"));
                    et4.setText(lastElement.getString("address"));
                    et5.setText(lastElement.getString("post_code"));
                    et7.setText(lastElement.getString("relationship"));
                    showdate1.setText(lastElement.getString("date_of_birth"));
                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                    updateId.setText(lastElement.getString("id"));


                    Log.e("Azeem Ullah par dat",partialData.toString());
                    //JSONObject lastElement = partialData.getJSONObject(partialData.length() - 1);
                }
                catch(Exception e){
                    Log.e("Json Exception " , e.toString());
                }
            }


        }



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
        String url = "http://128.199.50.69/api/api-getNumExecutor.php";
        Executors.getCurrentNumExecutors pe = new Executors.getCurrentNumExecutors();
        pe.execute(url,message);


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Executors.this,
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
                et6.clearFocus();
            }
        };

        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().hasExtra("back") && !passingArray.equals("")) {
                    Log.e("Passing Array back key", passingArray);
                    try{
                        if(getIntent().hasExtra("fromGuardians")){
                            startActivity(new Intent(Executors.this, Executors.class).putExtra("back", true).putExtra("data", passingArray.toString()).putExtra("innerLoop", true).putExtra("previousData",lastElementGlobal));
                        }else {
                            startActivity(new Intent(Executors.this, Executors.class).putExtra("back", true).putExtra("data", passingArray.toString()).putExtra("innerLoop", true));
                        }
                    }
                    catch(Exception e){
                        Log.e("JSON exception",e.toString());
                    }
                }
                else{
                    String url = "http://128.199.50.69/api/api-getExecutor.php";
                    String message = "";
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                        message = jsonBody.toString();
                    } catch (Exception e) {
                        Log.e("JSON error: ", e.toString());
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                    Executors.getExecutersList pe = new Executors.getExecutersList();
                    pe.execute(url, message,"testator_details");
                }
            }
        });
        exenext = (Button) findViewById(R.id.Executor_next);
        exenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                if (et1.length() == 0) {
                    et1.requestFocus();
                    et1.setError("Please enter first name");
                } else if (et3.length() == 0) {
                    et3.requestFocus();
                    et3.setError("Please enter surname");
                } else if (et4.length() == 0) {
                    et4.requestFocus();
                    et4.setError("Please enter address");
                } else if (et5.length() == 0) {
                    et5.requestFocus();
                    et5.setError("Please enter Post Code");
                } else if (et6.length() == 0) {
                    et6.requestFocus();
                    et6.setError("Please Select date");
                }
                else if(et6.length() != 0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(et6.getText().toString());
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar dob = Calendar.getInstance();
                    dob.setTime(date);

                    Calendar today = Calendar.getInstance();

                    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                    if(age < 18 ){
                        et6.requestFocus();
                        et6.setError("Minimum age Requirment is 18");
                    }
                    else{
                            if (et7.length() == 0) {
                            et7.requestFocus();
                            et7.setError("Please enter Your Relation");
                        }

//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                        else {
                            String url = "http://128.199.50.69/api/api-insertExecutor.php";
                            if(getIntent().hasExtra("back")) {
                                if (getIntent().getExtras().getBoolean("back") && TextUtils.isEmpty(updateId.getText()) ) {
                                    url = "http://128.199.50.69/api/api-updateExecuter.php";
                                }
                            }
                            Log.e("URL",url);
                            String message = "";
                            try {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("firstName", et1.getText().toString());
                                jsonBody.put("middleName", et2.getText().toString());
                                jsonBody.put("lastName", et3.getText().toString());
                                jsonBody.put("address", et4.getText().toString());
                                jsonBody.put("postalCode", et5.getText().toString());
                                jsonBody.put("date", et6.getText().toString());
                                jsonBody.put("relation", et7.getText().toString());
                                if (getIntent().hasExtra("back") && TextUtils.isEmpty(updateId.getText())) {
                                    jsonBody.put("updateId", updateId.getText().toString());
                                }
                                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                                message = jsonBody.toString();
                            }
                            catch (Exception e){
                                Log.e("JSON error: " , e.toString());
                                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                            }
                            Executors.ParallelExec pe = new Executors.ParallelExec();
                            if((passingArray.equals("")) || getIntent().hasExtra("fromGuardians")) {
                                pe.execute(url, message, "guardian");
                            }
                            else{
                                Log.e("Here you go", passingArray);
                                pe.execute(url, message, "forwardNext");
                            }
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                            //Insert_executor(uid);
                            // Exec_intent_func(uid);
                        }

                    }

                }








            }
        });

        exeadd = (Button) findViewById(R.id.exeadd);

        exeadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                SharedPreferences sp1 = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                int myIntValue = sp1.getInt("your_int_key", -1);
                count++;
                String a = Integer.toString(myIntValue);
                if (et1.length() == 0) {
                    et1.requestFocus();
                    et1.setError("Please enter first name");
                }  else if (et3.length() == 0) {
                    et3.requestFocus();
                    et3.setError("Please enter surname");
                } else if (et4.length() == 0) {
                    et4.requestFocus();
                    et4.setError("Please enter address");
                } else if (et5.length() == 0) {
                    et5.requestFocus();
                    et5.setError("Please enter Post Code");
                } else if (et6.length() == 0) {
                    et6.requestFocus();
                    et6.setError("Please Select date");
                }
                else if(et6.length() != 0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = sdf.parse(et6.getText().toString());
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar dob = Calendar.getInstance();
                    dob.setTime(date);

                    Calendar today = Calendar.getInstance();

                    int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                    if(age < 18 ){
                        et6.requestFocus();
                        et6.setError("Minimum age Requirment is 18");
                    }
                    else{
                          if (et7.length() == 0) {
                            et7.requestFocus();
                            et7.setError("Please enter Your Relation");
                        } else if (currentNumExecutor >= 3) {
                            //Toast.makeText(Executors.this, "You can't add more than 3 executor", Toast.LENGTH_SHORT).show();
                            //Insert_executor_add(uid);
                            //Exec_intent_func(uid);
                            //Intent in=new Intent(Executors.this,Guardians.class);
                            //startActivity(in);

                            Toast.makeText(Executors.this, "You can't add more than 3 executor", Toast.LENGTH_SHORT).show();

                        } else {

                            String url = "http://128.199.50.69/api/api-insertExecutor.php";
                            if(getIntent().hasExtra("back")) {
                                if (getIntent().getExtras().getBoolean("back") && TextUtils.isEmpty(updateId.getText())) {
                                    url = "http://128.199.50.69/api/api-updateExecuter.php";
                                }

                            }
                            Log.e("URL",url);
                            String message = "";
                            try {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("firstName", et1.getText().toString());
                                jsonBody.put("middleName", et2.getText().toString());
                                jsonBody.put("lastName", et3.getText().toString());
                                jsonBody.put("address", et4.getText().toString());
                                jsonBody.put("postalCode", et5.getText().toString());
                                jsonBody.put("date", et6.getText().toString());
                                if (getIntent().hasExtra("back")  && TextUtils.isEmpty(updateId.getText())) {
                                    jsonBody.put("updateId", updateId.getText().toString());
                                }
                                jsonBody.put("relation", et7.getText().toString());
                                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                                message = jsonBody.toString();
                            }
                            catch (Exception e){
                                Log.e("JSON error: " , e.toString());
                                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                            }

                            Executors.ParallelExec pe = new Executors.ParallelExec();
                            pe.execute(url,message,"executer");

                            // Intent i = new Intent(Executors.this, Executors.class);
                            //startActivity(i);
                        }



                    }


                }




            }
        });
    }



    @Override
    public void onBackPressed() {
        but.performClick();
    }





    private class ParallelExec extends AsyncTask<String, Void, String> {

        String redirect = null;

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
                   // Toast.makeText(getApplicationContext(), "Testator added!", Toast.LENGTH_SHORT).show();
                    if(redirect.equals("guardian")){
                        if(getIntent().hasExtra("back")) {
                            startActivity(new Intent(Executors.this, Guardians.class).putExtra("forwardProp", true).putExtra("numGuardian", 0).putExtra("back", true).putExtra("num", 0));
                        }
                        else{
                            startActivity(new Intent(Executors.this, Guardians.class).putExtra("stop","no"));
                        }
                    }
                    else if(redirect.equals("executer")) {
                        startActivity(new Intent(Executors.this,Executors.class));
                    }
                    else if(redirect.equals("forwardNext")) {
                        startActivity(new Intent(Executors.this,Executors.class).putExtra("data",passingArray).putExtra("back",true).putExtra("forwardProp",true).putExtra("innerLoop",true));
                    }
//                    else if(redirect.equals("previousUtil")) {
//                        startActivity(new Intent(Executors.this,Executors.class).putExtra("data",passingArray).putExtra("back",true).putExtra("forwardProp",true));
//                    }
                    else{
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
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
                    if(currentNumExecutor+1 >=3 && !getIntent().hasExtra("back")){
                        exeadd.setVisibility(View.GONE);
                    }
                    else if(currentNumExecutor >2 && getIntent().hasExtra("back") ){
                     exeadd.setVisibility(View.GONE);
                    }
                    //Toast.makeText(getApplicationContext(), "Testator added!", Toast.LENGTH_SHORT).show();
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
                    Log.e("Api error ", error);
                    //Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
                //Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
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
        String redirect = "";
        @Override
        protected String doInBackground(String... params) {
            redirect = params[2];
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
                    JSONObject lastElement = partialData.getJSONObject(partialData.length()-1);



                    Log.e("Data response: ",data);
//                    Log.e("Data response partial: ",lastElement.toString());



                    et1.setText(lastElement.getString("first_name"));
                    et2.setText(lastElement.getString("mid_name"));
                    et3.setText(lastElement.getString("sur_name"));
                    et4.setText(lastElement.getString("address"));
                    et5.setText(lastElement.getString("post_code"));
                    et7.setText(lastElement.getString("relationship"));
                    showdate1.setText(lastElement.getString("date_of_birth"));
                    mDisplayDate.setText(lastElement.getString("date_of_birth"));
                    updateId.setText(lastElement.getString("id"));

                    if(partialData.length()>1) {
                        passingArray = "[";
                        for (int i = 0; i < partialData.length() - 1; i++) {
                            passingArray += partialData.getString(i);
                            passingArray += ",";
                        }
                    passingArray = passingArray.substring(0,passingArray.length()-1);
                        passingArray += "]";
                    }

                    Log.e("Passing Data is", passingArray.toString());





                    if(redirect.equals("testator_details")){
                        startActivity(new Intent(Executors.this,testator_details.class).putExtra("back",true).putExtra("data",partialData.toString()));
                    }




                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
                    Log.e("Api error ", error);
                    if(redirect.equals("testator_details")){
                        startActivity(new Intent(Executors.this,testator_details.class).putExtra("back",true));
                    }

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










//
//    private void Insert_executor_add(final String uid) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_executor, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//
//                //Toast.makeText(getApplication(), "3333", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(Executors.this, error + "", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                Map<String, String> params = new HashMap<String, String>();
//
////                String newString = newString.getText().toString();
////                String email = mail.getText().toString();
////                String passwrd = pass.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtras(getIntent());
//                String fnm1 = et1.getText().toString();
//                String mdnm1 = et2.getText().toString();
//                String srnm1 = et3.getText().toString();
//                String ad1 = et4.getText().toString();
//                String pst1 = et5.getText().toString();
//
//
//                String db1 = et6.getText().toString();
//                String rl1 = et7.getText().toString();
//
////                String uid1 = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id", uid);
//
//
//                params.put("name", fnm1);
//                params.put("mdname", mdnm1);
//                params.put("srname", srnm1);
//                params.put("adres", ad1);
//                params.put("pst-code", pst1);
//                params.put("dob", db1);
//                params.put("relation", rl1);
//                params.put("uid", "69");
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


//    public void Insert_executor(final String uid) {
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_executor, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//                Intent intent = new Intent(Executors.this, Guardians.class);
//                startActivity(intent);
//                //Toast.makeText(getApplication(), "3333", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(Executors.this, error + "", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//
//                Map<String, String> params = new HashMap<String, String>();
//
////                String newString = newString.getText().toString();
////                String email = mail.getText().toString();
////                String passwrd = pass.getText().toString();
//                Intent intent = new Intent();
//                intent.putExtras(getIntent());
//                String fnm1 = et1.getText().toString();
//                String mdnm1 = et2.getText().toString();
//                String srnm1 = et3.getText().toString();
//                String ad1 = et4.getText().toString();
//                String pst1 = et5.getText().toString();
//
//
//                String db1 = et6.getText().toString();
//                String rl1 = et7.getText().toString();
//
////                String uid1 = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id", uid);
//
//
//                params.put("name", fnm1);
//                params.put("mdname", mdnm1);
//                params.put("srname", srnm1);
//                params.put("adres", ad1);
//                params.put("pst-code", pst1);
//                params.put("dob", db1);
//                params.put("relation", rl1);
//                params.put("uid", "69");
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
            Intent in=new Intent(Executors.this,logina1.class);
            startActivity(in);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

//    private void Exec_intent_func(final String uid) {
//
//        Intent intent = new Intent(Executors.this, Guardians.class);
//        intent.putExtra("grdn_fn", et1.getText().toString());
//        intent.putExtra("grdn_md", et2.getText().toString());
//        intent.putExtra("grdn_sr", et3.getText().toString());
//        intent.putExtra("grdn_ad", et4.getText().toString());
//        intent.putExtra("grdn_ps", et5.getText().toString());
//        intent.putExtra("grdn_db", et6.getText().toString());
//        intent.putExtra("grdn_rel", et7.getText().toString());
//
//
//        //String uid = getIntent().getExtras().getString("u_id");
//        intent.putExtra("u_id", uid);
//        intent.putExtras(getIntent());
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
//
//
//        startActivity(intent);
//
//
//    }


    public void datepicker(View view) {

        DatePickerFregment fragment = new DatePickerFregment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.Ex_t6)).setText(dateFormat.format(calendar.getTime()));
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