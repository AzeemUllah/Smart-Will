package com.example.smart_will;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utilities.NetworkUtilities;


public class viewSummary extends AppCompatActivity {
SessionManager session;
    ImageButton but;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,
            t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,
            t21,t22,t23,t24,t25,t26,t27,t28,t29, t30,
            t31,t32,t33,t34,t35,t36,t37,t38,t39,t40,
            t41,t42,t43,t44,t45,t46,t47,t48,t49,t50,t51;
    //
//        roe1=(EditText) findViewById(R.id.rfs1);
//        roe1.setText(getIntent().getExtras().getString("fname"));
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

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
            Intent in=new Intent(viewSummary.this,logina1.class);
            startActivity(in);
            return(true);
        case R.id.back:
            // session.logoutUser();
            Intent in1=new Intent(viewSummary.this,Submission.class);
            startActivity(in1);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        but = (ImageButton) findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewSummary.this, Submission.class));
            }
        });

        t1=(TextView)findViewById(R.id.T1);
        t2=(TextView)findViewById(R.id.T2);
        t3=(TextView)findViewById(R.id.T3);
        t4=(TextView)findViewById(R.id.T4);
        t5=(TextView)findViewById(R.id.T5);

        t6=(TextView)findViewById(R.id.T6);

        t7=(TextView)findViewById(R.id.T7);


        t8=(TextView)findViewById(R.id.T8);

        t9=(TextView)findViewById(R.id.T9);
        t10=(TextView)findViewById(R.id.T10);
        t11=(TextView)findViewById(R.id.T11);
        t12=(TextView)findViewById(R.id.T12);
        t13=(TextView)findViewById(R.id.T13);

        t14=(TextView)findViewById(R.id.T14);

        t15=(TextView)findViewById(R.id.T15);
        t16=(TextView)findViewById(R.id.T16);

        t17=(TextView)findViewById(R.id.T17);
        t18=(TextView)findViewById(R.id.T18);
        t19=(TextView)findViewById(R.id.T19);
        t20=(TextView)findViewById(R.id.T20);
        t21=(TextView)findViewById(R.id.T21);

        t22=(TextView)findViewById(R.id.T22);

        t23=(TextView)findViewById(R.id.T23);
        t24=(TextView)findViewById(R.id.T24);

        t25=(TextView)findViewById(R.id.T25);
        t26=(TextView)findViewById(R.id.T26);
        t27=(TextView)findViewById(R.id.T27);
        t28=(TextView)findViewById(R.id.T28);
        t29=(TextView)findViewById(R.id.T29);

        t30=(TextView)findViewById(R.id.T30);

        t31=(TextView)findViewById(R.id.T31);
        t32=(TextView)findViewById(R.id.T32);

        t33=(TextView)findViewById(R.id.T33);
        t34=(TextView)findViewById(R.id.T34);
        t35=(TextView)findViewById(R.id.T35);
        t36=(TextView)findViewById(R.id.T36);
        t37=(TextView)findViewById(R.id.T37);

        t38=(TextView)findViewById(R.id.T38);

        t39=(TextView)findViewById(R.id.T39);
        t40=(TextView)findViewById(R.id.T40);

        t41=(TextView)findViewById(R.id.T41);
        t42=(TextView)findViewById(R.id.T42);
        t43=(TextView)findViewById(R.id.T43);
        t44=(TextView)findViewById(R.id.T44);
        t45=(TextView)findViewById(R.id.T45);

//        t47=(TextView)findViewById(R.id.T47);
//        t48=(TextView)findViewById(R.id.T48);
//
//        t49=(TextView)findViewById(R.id.T49);
//        t50=(TextView)findViewById(R.id.T50);
//        t51=(TextView)findViewById(R.id.T51);
//




        //For Testator

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
        String url = "http://128.199.50.69/api/api-getTestator.php";
        viewSummary.getTestator pe = new viewSummary.getTestator();
        pe.execute(url,message);




        //For Spouse
        String message1 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message1 = jsonBody.toString();
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        String url1 = "http://128.199.50.69/api/api-getSpouse.php";
        viewSummary.getSpouse pe1 = new viewSummary.getSpouse();
        pe1.execute(url1,message1);






        //For Executers

        String url2 = "http://128.199.50.69/api/api-getExecutor.php";
        String message2 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message2 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        viewSummary.getExecuters pe2 = new viewSummary.getExecuters();
        pe2.execute(url2, message2);





        String url3 = "http://128.199.50.69/api/api-getGuardians.php";
        String message3 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message3 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        viewSummary.getGuardian pe3 = new viewSummary.getGuardian();
        pe3.execute(url3, message3);







        //For gifts and legacy list

        String url4 = "http://128.199.50.69/api/api-getGiftsAndLegacy.php";
        String message4 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message4 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        viewSummary.getGiftsLegacy pe4 = new viewSummary.getGiftsLegacy();
        pe4.execute(url4, message4);









        //For gifts

        String url5 = "http://128.199.50.69/api/api-getGifts.php";
        String message5 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message5 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        viewSummary.getGifts pe5 = new viewSummary.getGifts();
        pe5.execute(url5, message5);







        //For Residue
        String url6 = "http://128.199.50.69/api/api-getResidue.php";
        String message6 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message6 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        viewSummary.getResidue pe6 = new viewSummary.getResidue();
        pe6.execute(url6, message6);

    }




    private class getTestator extends AsyncTask<String, Void, String> {
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
                    JSONObject dataJson = new JSONObject(data);

                    t1.setText(dataJson.getString("first_name"));
                    t2.setText(dataJson.getString("middle_name"));
                    t3.setText(dataJson.getString("last_name"));
                    t4.setText(dataJson.getString("address"));
                    t5.setText(dataJson.getString("post_code"));
                    t6.setText(dataJson.getString("telephone"));
                    t7.setText(dataJson.getString("email"));
                    t8.setText(dataJson.getString("dob"));
                    t9.setText(dataJson.getString("gender"));

                    Log.e("Data response: ",data);
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details No spouse in database.");

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





    private class getSpouse extends AsyncTask<String, Void, String> {

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
                    JSONObject dataJson = new JSONObject(data);

                    t10.setText(dataJson.getString("first_name"));
                    t11.setText(dataJson.getString("middle_name"));
                    t12.setText(dataJson.getString("last_name"));
                    t13.setText(dataJson.getString("address"));
                    t14.setText(dataJson.getString("post_code"));
                    t15.setText(dataJson.getString("telephone"));
                    t16.setText(dataJson.getString("email"));
                    t17.setText(dataJson.getString("dob"));
                    t18.setText(dataJson.getString("gender"));

                    Log.e("Data response: ",data);
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Spouse");
                    Log.e("Api error ", error);
                    if(!error.equals("No Data.")) {
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
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






    private class getExecuters extends AsyncTask<String, Void, String> {
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




                    Log.e("Data response Exec: ",partialData.toString());


//                    Log.e("Data response partial: ",lastElement.toString());



                    for(int i = 0; i <= partialData.length()-1; i++){

                        JSONObject lastElement = partialData.getJSONObject(i);
                        String comma = "";
                        if(i != 0 ){
                            comma = ",";
                        }

                        t19.setText(t19.getText() + comma + " " + lastElement.getString("first_name"));
                        if(lastElement.getString("mid_name").equals("")){
                            t20.setText(t20.getText() + lastElement.getString("mid_name"));
                        }else {
                            t20.setText(t20.getText() + comma + " " + lastElement.getString("mid_name"));
                        }
                        t21.setText(t21.getText() + comma + " " + lastElement.getString("sur_name"));
                        t22.setText(t22.getText() + comma + " " + lastElement.getString("address"));
                        t23.setText(t23.getText() + comma + " " + lastElement.getString("post_code"));
                        t24.setText(t24.getText() + comma + " " + lastElement.getString("relationship"));
                        t25.setText(t25.getText() + comma + " " + lastElement.getString("date_of_birth"));
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





    private class getGuardian extends AsyncTask<String, Void, String> {
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




                    Log.e("Data response Exec: ",partialData.toString());


//                    Log.e("Data response partial: ",lastElement.toString());



                    for(int i = 0; i <= partialData.length()-1; i++){

                        JSONObject lastElement = partialData.getJSONObject(i);
                        String comma = "";
                        if(i != 0 ){
                            comma = ",";
                        }

                        t26.setText(t26.getText() + comma + " " + lastElement.getString("first_name"));
                        if(lastElement.getString("mid_name").equals("")){
                            t27.setText(t27.getText() + lastElement.getString("mid_name"));
                        }else {
                            t27.setText(t27.getText() + comma + " " + lastElement.getString("mid_name"));
                        }
                        t28.setText(t28.getText() + comma + " " + lastElement.getString("sur_name"));
                        t29.setText(t29.getText() + comma + " " + lastElement.getString("address"));
                        t30.setText(t30.getText() + comma + " " + lastElement.getString("post_code"));
                        t31.setText(t31.getText() + comma + " " + lastElement.getString("relationship"));
                        t32.setText(t32.getText() + comma + " " + lastElement.getString("date_of_birth"));


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






    private class getGiftsLegacy extends AsyncTask<String, Void, String> {
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

                    t33.setText(lastElement.getString("foundation1"));
                    t34.setText(lastElement.getString("foundation2"));
                    t35.setText(lastElement.getString("foundation3"));
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






    private class getGifts extends AsyncTask<String, Void, String> {
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


                    if(partialData.length() > 0){

                        for(int i = 0; i <= partialData.length()-1; i++){

                            JSONObject lastElement = partialData.getJSONObject(i);
                            String comma = "";
                            if(i != 0 ){
                                comma = ",";
                            }

                            t36.setText(t36.getText() + comma + " " + lastElement.getString("first_name"));
                            if(lastElement.getString("mid_name").equals("")){
                                t37.setText(t37.getText() + lastElement.getString("mid_name"));
                            }else {
                                t37.setText(t37.getText() + comma + " " + lastElement.getString("mid_name"));
                            }
                            t37.setText(t37.getText() + comma + " " + lastElement.getString("sur_name"));
                            t37.setText(t37.getText() + comma + " " + lastElement.getString("relationship"));
                            t37.setText(t37.getText() + comma + " " + lastElement.getString("gifts"));



                        }

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




    private class getResidue extends AsyncTask<String, Void, String> {
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


                    if(partialData.length() > 0){

                        for(int i = 0; i <= partialData.length()-1; i++){

                            JSONObject lastElement = partialData.getJSONObject(i);
                            String comma = "";
                            if(i != 0 ){
                                comma = ",";
                            }

                            t41.setText(t41.getText() + comma + " " + lastElement.getString("first_name"));
                            if(lastElement.getString("mid_name").equals("")){
                                t42.setText(t42.getText() + lastElement.getString("mid_name"));
                            }else {
                                t42.setText(t42.getText() + comma + " " + lastElement.getString("mid_name"));
                            }
                            t43.setText(t43.getText() + comma + " " + lastElement.getString("sur_name"));
                            t44.setText(t44.getText() + comma + " " + lastElement.getString("relationship"));
                            t45.setText(t45.getText() + comma + " " + lastElement.getString("estate"));



                        }

                    }
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Residue");
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


}
