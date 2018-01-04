package com.example.smart_will;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import utilities.NetworkUtilities;


public class Spouse extends AppCompatActivity  {
    private TextView mDisplayDate,showdate1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SessionManager session;
        EditText spous1,spous2,spous3,spous4,spous5,spous6,spous7;
        TextView spous8;
    ImageButton but;
        RadioGroup spouse9;
        Button spnext;
        Button btn_spouse;
    RadioButton radioMale, radioFemale;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_spouse);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            spous1 =(EditText)findViewById(R.id.sp1);
            spous2 =(EditText)findViewById(R.id.sp2);
            spous3 =(EditText)findViewById(R.id.sp3);
            spous4 =(EditText)findViewById(R.id.sp4);
            spous5 =(EditText)findViewById(R.id.sp5);
            spous6 =(EditText)findViewById(R.id.sp6);
            spous7 =(EditText)findViewById(R.id.sp7);

            radioMale = (RadioButton) findViewById(R.id.radioButton4);
            radioFemale = (RadioButton) findViewById(R.id.radioButton5);

            session = new SessionManager(getApplicationContext());

            if(getIntent().hasExtra("stop")) {
                if (getIntent().getStringExtra("stop").equals("yes")) {
                    session.statusLogin("spouse");
                }
                else{
                    session.setCurrentClass("spouse");
                }
            }
            else{
                Log.e("I AM","Called");
            }



            if(getIntent().hasExtra("back")) {
                if (getIntent().getExtras().getBoolean("back")) {
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
                    String url = "http://128.199.50.69/api/api-getSpouse.php";
                    Spouse.autoFill pe = new Spouse.autoFill();
                    pe.execute(url,message);
                }
            }



            HashMap<String, String> user = session.getUserDetails();
            spouse9 =(RadioGroup)findViewById(R.id.sp9);
            mDisplayDate = (TextView) findViewById(R.id.picdate);
            showdate1 = (TextView) findViewById(R.id.showdate_spouse);

            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Spouse.this,
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
                    String date = year+"-"+ month+"-"+day ;
                    showdate1.setText(date);
                    mDisplayDate.setText(date);

                }
            };
            but=(ImageButton)findViewById(R.id.back);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Spouse.this,testator_details.class).putExtra("back",true));
                }
            });
            spnext = (Button)findViewById(R.id.spouseNext);
            spnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("hi spouse", "Hi thete");


                    if(spous1.length()==0)
                    {
                        spous1.requestFocus();
                        spous1.setError("Please enter first name");
                    }
                    else if(spous3.length()==0)
                    {
                        spous3.requestFocus();
                        spous3.setError("Please enter surname");
                    }
                    else if(spous4.length()==0)
                    {
                        spous4.requestFocus();
                        spous4.setError("Please enter address");
                    }
                    else if(spous5.length()==0)
                    {
                        spous5.requestFocus();
                        spous5.setError("Please enter Postal Code");
                    }

                    else if(spous6.length()==0)
                    {
                        spous6.requestFocus();
                        spous6.setError("Please enter telephone number");
                    }
                    else if(spous7.length()==0)
                    {
                        spous7.requestFocus();
                        spous7.setError("Please enter email");
                    }
                    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(spous7.getText().toString()).matches())
                    {
                        spous7.setError("Invalid Email");
                    }
                    else if(showdate1.length()==0)
                    {
                        showdate1.requestFocus();
                        showdate1.setError("Please select date");
                    }
                    else if (spouse9.getCheckedRadioButtonId() == -1)
                    {
                        spouse9.requestFocus();
                        Toast.makeText(Spouse.this, "Please enter Gender", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {



                        String url = "http://128.199.50.69/api/api-insertSpouse.php";
                        if(getIntent().hasExtra("back")) {
                            if (getIntent().getExtras().getBoolean("back")) {
                                url = "http://128.199.50.69/api/api-updateSpouse.php";
                            }
                        }
                        String message = "";
                        try {
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("firstName", spous1.getText().toString());
                            jsonBody.put("middleName", spous2.getText().toString());
                            jsonBody.put("lastName", spous3.getText().toString());
                            jsonBody.put("address", spous4.getText().toString());
                            jsonBody.put("postalCode", spous5.getText().toString());
                            jsonBody.put("telephone", spous6.getText().toString());
                            jsonBody.put("email", spous7.getText().toString());
                            jsonBody.put("date", showdate1.getText().toString());
                            jsonBody.put("gender", ((RadioButton)findViewById(spouse9.getCheckedRadioButtonId())).getText().toString());
                            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                            message = jsonBody.toString();
                        }
                        catch (Exception e){
                            Log.e("JSON error: " , e.toString());
                            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                        }
                        Spouse.ParallelExec pe = new Spouse.ParallelExec();
                        pe.execute(url,message);
                    }
                }
            });
        }

    @Override
    public void onResume(){
        super.onResume();
        session.statusLogin("spouse");
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
            Intent in=new Intent(Spouse.this,logina1.class);
            startActivity(in);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    private class ParallelExec extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Log.e("Spouse url" , params[0]);
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data Souse: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");
                Log.e("in spouse", result);
                if(status.equals("Ok")){

                   //Toast.makeText(getApplicationContext(), "Spouse added!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Spouse.this,testator_details.class).putExtra("back",true));
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Spouse Details");
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




    @Override
    public void onBackPressed() {
        but.performClick();
    }

//    public void Insert_spouse(final String uid) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_spouse, new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//                Intent intent = new Intent(Spouse.this, Executors.class);
//                startActivity(intent);
//                //Toast.makeText(getApplication(), "2222", Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(Spouse.this, error + "", Toast.LENGTH_SHORT).show();
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
//                String fnm2 = spous1.getText().toString();
//                String mdnm2 = spous2.getText().toString();
//                String srnm2 = spous3.getText().toString();
//                String ad2 =spous4.getText().toString();
//                String pst2 =spous5.getText().toString();
//
//
//                String tl2 = spous6.getText().toString();
//                String em2 =spous7.getText().toString();
//                String dt2 =showdate1.getText().toString();
//
//                String rv = ((RadioButton)findViewById(spouse9.getCheckedRadioButtonId())).getText().toString();
//
//
//                params.put("name", fnm2);
//                params.put("mdname", mdnm2);
//                params.put("srname", srnm2);
//                params.put("adres", ad2);
//                params.put("pst-code", pst2);
//                params.put("telephone", tl2);
//                params.put("email", em2);
//                params.put("dob", dt2);
//                params.put("gndr", rv);
//                params.put("uid",uid);
//
//
//                return params;
//            }
//
//
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//






    private class autoFill extends AsyncTask<String, Void, String> {

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

                    spous1.setText(dataJson.getString("first_name"));
                    spous2.setText(dataJson.getString("middle_name"));
                    spous3.setText(dataJson.getString("last_name"));
                    spous4.setText(dataJson.getString("address"));
                    spous5.setText(dataJson.getString("post_code"));
                    spous6.setText(dataJson.getString("telephone"));
                    spous7.setText(dataJson.getString("email"));
                    showdate1.setText(dataJson.getString("dob"));
                    mDisplayDate.setText(dataJson.getString("dob"));
                    //--Radiogroup--//
                    // radioSexGroup = (RadioGroup) findViewById(R.id.Rbg);
                    if(dataJson.getString("gender").equals("Male")){
                        radioMale.setChecked(true);
                        radioFemale.setChecked(false);
                    }
                    else if(dataJson.getString("gender").equals("Female")){
                        radioMale.setChecked(false);
                        radioFemale.setChecked(true);
                    }



                    Log.e("Data response: ",data);
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Testator_details");
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





    public void datepicker(View view) {

        testator_details.DatePickerFregment fragment = new testator_details.DatePickerFregment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.sp8)).setText(dateFormat.format(calendar.getTime()));
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
