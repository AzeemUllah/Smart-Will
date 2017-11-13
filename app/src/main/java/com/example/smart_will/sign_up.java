package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import org.json.JSONObject;
import utilities.NetworkUtilities;

public class sign_up extends AppCompatActivity {
    EditText usr_nm, mail, pass;
    Button btn;
    ImageButton but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usr_nm=(EditText) findViewById(R.id.usr_nme);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        btn=(Button)findViewById(R.id.btn_reg);

        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up.this,logina1.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(usr_nm.length()==0)
                {
                    usr_nm.requestFocus();
                    usr_nm.setError("Please Enter User Name");
                }
                else if(mail.length()==0)
                {
                    mail.requestFocus();
                    mail.setError("Please Enter Your Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches())
                {
                    mail.setError("Invalid Email");
                }
                else if(pass.length()==0)
                {
                    pass.requestFocus();
                    pass.setError("Please Enter Password");
                }
                else if(pass.length()!=4)
                {
                    pass.requestFocus();
                    pass.setError("Password must be of 4 digits");
                }
                else
                {
                    String url = "http://128.199.50.69/api/api-signup.php";
                    String message = "";
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("username", usr_nm.getText().toString());
                        jsonBody.put("email", mail.getText().toString());
                        jsonBody.put("password", pass.getText().toString());
                        message = jsonBody.toString();
                    }
                    catch (Exception e){
                        Log.e("JSON error: " , e.toString());
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                    ParallelExec pe = new ParallelExec();
                    pe.execute(url,message);
                }
            }
        });
    }

    private class ParallelExec extends AsyncTask<String, Void, String> {

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
                    Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(sign_up.this, logina1.class));
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    if (error.equals("Email address already exists.")) {
                        Toast.makeText(getApplicationContext(), "Email already Exists!", Toast.LENGTH_SHORT).show();
                    }
                    else{
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


 }