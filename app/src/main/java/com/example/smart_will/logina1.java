package com.example.smart_will;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import org.json.JSONObject;

        import utilities.NetworkUtilities;

public class logina1 extends AppCompatActivity {
    SessionManager session;
    EditText mail,pass;
    Button btn;

    public void signup(View view) {
        Intent intern = new Intent(this, sign_up.class);
        startActivity(intern);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logina1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        mail=(EditText)findViewById(R.id.mail);
        final String Name=mail.getText().toString();
        pass=(EditText)findViewById(R.id.pas);
        btn=(Button)findViewById(R.id.login);

        mail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches())
                    {
                        mail.setError("Invalid Email Format");
                    }
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mail.length()==0)
                {
                    mail.requestFocus();
                    mail.setError("Please Enter Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches())
                {
                    mail.requestFocus();
                    mail.setError("Invalid Email Format");
                }
                else if(pass.length()==0)
                {
                    pass.requestFocus();
                    pass.setError("Please Enter Password");
                }
                else if(pass.length()!=4)
                {
                    pass.requestFocus();
                    pass.setError("Invalid Password Length");
                }
                else
                {
                    String url = "http://128.199.50.69/api/api-login.php";
                    String message = "";
                    try {
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("email",  mail.getText().toString().trim());
                        jsonBody.put("password", pass.getText().toString().trim());
                        message = jsonBody.toString();
                        Log.e("Data response: ",message);
                    }
                    catch (Exception e){
                        Log.e("JSON error: " , e.toString());
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                    logina1.ParallelExec pe = new logina1.ParallelExec();
                    pe.execute(url,message);
                }
            }
        });
    }




    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
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
                    String id = jsonResult.getString("Data");
                    session.setSession(id);
                    session.emailAddress = mail.getText().toString();
                    Toast.makeText(getApplicationContext(), "Sucessfully Logged in!", Toast.LENGTH_SHORT).show();
                    session.statusLogin("login");

                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    if (error.equals("Invalid Credentials")) {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials.\n Login Denied.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch(Exception e){
                Log.e("JSON error: " , e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again. 1"+ e.toString(), Toast.LENGTH_SHORT).show();
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



