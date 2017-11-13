package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RedirectError;
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

public class Residue_of_estate extends AppCompatActivity {
    SessionManager session;
    ImageButton but;
    public void spouse(View view) {
        Intent intern = new Intent(this, Residue_of_estate2.class);
        startActivity(intern);
    }

    EditText roe1,roe2,roe3,roe4,roe5, updateId;
    Button roeNext,addbeneficary,addstep;
    int estateTotal = -1;
    int estateCurrentVal = -1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residue_of_estate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());

        roe1=(EditText)findViewById(R.id.rfs1);
        roe2=(EditText)findViewById(R.id.rfs2);
        roe3=(EditText)findViewById(R.id.rfs3);
        roe4=(EditText)findViewById(R.id.rfs4);
        roe5=(EditText)findViewById(R.id.rfs5);
        updateId=(EditText)findViewById(R.id.updateResidue);

        roeNext = (Button)findViewById(R.id.rfsNext);
        addbeneficary = (Button)findViewById(R.id.addspouse);
        addstep = (Button)findViewById(R.id.addspouse1);










        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("residue");
            }
            else{
                session.setCurrentClass("residue");
            }
        }






        String url5 = "http://128.199.50.69/api/api-cleanResidueChildren.php";
        String message5 = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message5 = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Residue_of_estate.cleanResidue pe5 = new Residue_of_estate.cleanResidue();
        pe5.execute(url5, message5);



        roe5.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0 && !getIntent().hasExtra("forwards") && !getIntent().hasExtra("forwardCont") && !getIntent().hasExtra("backwards") && !getIntent().hasExtra("back")) {
                    if (Integer.parseInt(roe5.getText().toString()) + estateTotal > 100) {
                        roe5.requestFocus();
                        roe5.setError("sum exceeds 100%. Only " + (100 - estateTotal) + "% is left");
                    } else if (Integer.parseInt(roe5.getText().toString()) + estateTotal < 100) {
                        roeNext.setVisibility(View.GONE);
                        addbeneficary.setVisibility(View.VISIBLE);
                        addstep.setVisibility(View.VISIBLE);
                    } else if (Integer.parseInt(roe5.getText().toString()) + estateTotal == 100) {
                        roeNext.setVisibility(View.GONE);
                        addbeneficary.setVisibility(View.VISIBLE);
                        addstep.setVisibility(View.VISIBLE);
                    }
                }
            }
        });




            but = (ImageButton) findViewById(R.id.back);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getIntent().hasExtra("back") && session.residueList != null) {

                        if (session.residueList.length() - session.currentResidue != session.residueList.length()) {
                            insert("same","");
                            startActivity(new Intent(Residue_of_estate.this, Residue_of_estate.class).putExtra("backwardsCont", true).putExtra("back", true));
                        } else if (session.residueList.length() - session.currentResidue == session.residueList.length()) {
                            insert("same", "");
                            startActivity(new Intent(Residue_of_estate.this, Info_block.class).putExtra("back", true));
                        }
                        else{
                            startActivity(new Intent(Residue_of_estate.this, Info_block.class).putExtra("back",true));
                        }
                    }
                    else{
                        startActivity(new Intent(Residue_of_estate.this, Info_block.class).putExtra("back",true));
                    }



                }
            });



            getEstateTotal("check","");










        roeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }
                else if(Integer.parseInt(roe5.getText().toString())>100 || Integer.parseInt(roe5.getText().toString())<=0)
                {
                    roe5.requestFocus();
                    roe5.setError("Invalid % value.");
                }
                if (Integer.parseInt(roe5.getText().toString()) + (estateTotal-estateCurrentVal) > 100) {
                    roe5.requestFocus();
                    roe5.setError("sum exceeds 100%. Only " + (100-estateTotal+estateCurrentVal) + "% is left" );
                }
                else
                {
                    Log.e("List length, current" ,session.residueList.length() + " " + session.currentResidue);
                    if(getIntent().hasExtra("back")) {
                        if (session.residueList.length() - session.currentResidue != 1) {
                            insert("same", "contNext");
                        } else if (session.residueList.length() - session.currentResidue == 1) {
                            insert("same", "killNext");
                        }
                        else{
                            getEstateTotal("check","");
                        }
                    }
                    else {
                        getEstateTotal("check","");
                    }



//                    Log.v("New Percentage Value: " , SessionManager.percentageCal);
//                    session.setPrecentageNewValue((Integer.parseInt(SessionManager.percentageCal) - Integer.parseInt(roe5.getText().toString()))+"");
////                  Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
//                    Insert_residue_of_estate(uid);
//                    Rfsfunc(uid);
                }

            }
        });

        addstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }
                else if(Integer.parseInt(roe5.getText().toString())>100 || Integer.parseInt(roe5.getText().toString())<=0)
                {
                    roe5.requestFocus();
                    roe5.setError("Invalid % value.");
                }

                else
                {
                    getEstateTotal("","step");
//                    Log.v("New Percentage Value: " , SessionManager.percentageCal);
//                    session.setPrecentageNewValue((Integer.parseInt(SessionManager.percentageCal) - Integer.parseInt(roe5.getText().toString()))+"");
//                    Insert_step_children(uid);
//                    Intent in=new Intent(Residue_of_estate.this, Residue_of_estate.class);
//                    startActivity(in);
                }
            }
        });
        addbeneficary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }
                else if(Integer.parseInt(roe5.getText().toString())>100 || Integer.parseInt(roe5.getText().toString())<=0)
                {
                    roe5.requestFocus();
                    roe5.setError("Invalid % value.");
                }

                else
                {
                    getEstateTotal("","beneficiary");
//                    Log.v("New Percentage Value: " , SessionManager.percentageCal);
//                    session.setPrecentageNewValue((Integer.parseInt(SessionManager.percentageCal) - Integer.parseInt(roe5.getText().toString()))+"");
//                    Insert_residue_of_estate(uid);
//                    Intent in=new Intent(Residue_of_estate.this, Residue_of_estate.class);
//                    startActivity(in);
//                    //Rfsfunc(uid);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        but.performClick();
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
            Intent in=new Intent(Residue_of_estate.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
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
                    estateTotal = 0;
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Residue Clean func");
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






    public void insert(String type, String procedure){
        String url = "http://128.199.50.69/api/api-insertResidue.php";
        if(getIntent().hasExtra("back") && updateId.getText().toString().trim().length()>0){
            url = "http://128.199.50.69/api/api-updateResidue.php";
        }
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("firstName", roe1.getText().toString());
            jsonBody.put("middleName", roe2.getText().toString());
            jsonBody.put("lastName", roe3.getText().toString());
            jsonBody.put("relationship", roe4.getText().toString());
            jsonBody.put("estate", roe5.getText().toString());

            if(!type.equals("same")){
                jsonBody.put("type", type);
            }


            if(getIntent().hasExtra("back")){
                jsonBody.put("updateId", updateId.getText().toString());
            }

            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Residue_of_estate.ConnectSave insertObject = new Residue_of_estate.ConnectSave();
        insertObject.execute(url,message, procedure);
    }



    private class ConnectSave extends AsyncTask<String, Void, String> {
        String procedure = "";
        @Override
        protected String doInBackground(String... params) {
            procedure = params[2];
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    if(procedure.equals("cont")){
                        startActivity(new Intent(Residue_of_estate.this, Residue_of_estate.class));
                    }else if(procedure.equals("kill")){
                        startActivity(new Intent(Residue_of_estate.this, Special_request.class).putExtra("stop","no"));
                    }
                    else if(procedure.equals("contNext") ){
                        startActivity(new Intent(Residue_of_estate.this, Residue_of_estate.class).putExtra("forwardCont", true).putExtra("back", true));
                    }
                    else if(procedure.equals("killNext") && estateTotal >= 100){
                        startActivity(new Intent(Residue_of_estate.this, Special_request.class).putExtra("stop","no"));
                    }
                    else if(procedure.equals("killNext") && estateTotal < 100){
                        session.currentResidue = -1;
                        startActivity(new Intent(Residue_of_estate.this, Residue_of_estate.class));
                    }

                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Residue");
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




    public void getEstateTotal(String protocol, String type){

            String url = "http://128.199.50.69/api/api-checkResidue.php";
            String message = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                message = jsonBody.toString();
            } catch (Exception e) {
                Log.e("JSON erro ", e.toString());
                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
            }
            Residue_of_estate.EstateTotal totalObject = new Residue_of_estate.EstateTotal();
            totalObject.execute(url, message, protocol, type);

    }








    private class EstateTotal extends AsyncTask<String, Void, String> {
        String protocol = "";
        String type = "";

        @Override
        protected String doInBackground(String... params) {
            protocol = params[2];
            type = params[3];
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                Log.e("ok", jsonResult.toString());

                if(status.equals("Ok")){
                    String data = jsonResult.getString("Data");
                    if(!jsonResult.isNull("Data")) {
                        estateTotal = Integer.parseInt(data);
                        Log.e("Estate Total", Integer.toString(estateTotal));
                    }
                    else{
                        estateTotal = 0;
                    }



                    if(getIntent().getBooleanExtra("back",false) && estateTotal != 0 && !(type.equals("beneficiary") || type.equals("step"))){
                        if(getIntent().hasExtra("backwards") || getIntent().hasExtra("forwardCont") ||getIntent().hasExtra("forwards") || getIntent().hasExtra("backwardsCont")){
                            String url = "http://128.199.50.69/api/api-getResidue.php";
                            String message = "";
                            Log.e("here","::");
                            try {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
                                message = jsonBody.toString();
                            } catch (Exception e) {
                                Log.e("JSON error: ", e.toString());
                                Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
                            }
                            Residue_of_estate.getResidueList pe = new Residue_of_estate.getResidueList();
                            pe.execute(url, message,"");
                        }
                    }
                    else {
                        if (protocol.equals("check")) {
                            if (estateTotal < 100) {
                                roeNext.setVisibility(View.GONE);
                                Log.e("Here","am i!");
                            }
                            else if(estateTotal == 100 && getIntent().hasExtra("back")){
                                startActivity(new Intent(Residue_of_estate.this, Special_request.class).putExtra("stop","no"));
                            }
                            else if (estateTotal >= 100) {
                                roeNext.setVisibility(View.VISIBLE);
                                addbeneficary.setVisibility(View.GONE);
                                addstep.setVisibility(View.GONE);
                            }
                        } else {
                            if (!getIntent().hasExtra("back")) {
                                if (estateTotal + Integer.parseInt(roe5.getText().toString()) < 100) {
                                    insert(type, "cont");
                                } else if (estateTotal + Integer.parseInt(roe5.getText().toString()) == 100) {
                                    insert(type, "kill");
                                }
                            } else if (getIntent().hasExtra("back")) {
                                if (estateTotal + Integer.parseInt(roe5.getText().toString()) < 100) {
                                    insert(type, "cont");
                                } else if (estateTotal + Integer.parseInt(roe5.getText().toString()) == 100) {
                                    insert(type, "kill");
                                }
                            }
                        }
                    }

                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Residue");
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

    private class getResidueList extends AsyncTask<String, Void, String> {
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
                    session.residueList = partialData;




                    if(session.currentResidue < 0 && partialData.length() > 0){


                        if(getIntent().hasExtra("forwards")){
                            if(getIntent().getBooleanExtra("forwards",true)){
                                session.currentResidue = 0;
                            }
                        }
                        else if(getIntent().hasExtra("backwards")){
                            if(getIntent().getBooleanExtra("backwards",true)){
                                session.currentResidue = partialData.length() - 1;
                            }
                        }
                    }
                    else{


                        if(getIntent().hasExtra("backwardsCont")){
                            if(!getIntent().getBooleanExtra("backwardsCont",false) ){
                                session.currentResidue = session.currentResidue+1;
                            }
                            else if(getIntent().getBooleanExtra("backwardsCont",true)){
                                session.currentResidue = session.currentResidue-1;

                            }
                        }
                        else if(getIntent().hasExtra("forwardCont")){
                            if(!getIntent().getBooleanExtra("forwardCont",false) ){
                                session.currentResidue = session.currentResidue-1;
                            }
                            else if(getIntent().getBooleanExtra("forwardCont",true)){
                                session.currentResidue = session.currentResidue+1;
                            }
                        }
                    }

                    if(partialData.length() > 0){

                        Log.e("here","too");





                        if(session.currentResidue >= 0){

                            JSONObject lastElement = partialData.getJSONObject(session.currentResidue);

                            roe1.setText(lastElement.getString("first_name"));
                            roe2.setText(lastElement.getString("mid_name"));
                            roe3.setText(lastElement.getString("sur_name"));
                            roe4.setText(lastElement.getString("relationship"));
                            roe5.setText(lastElement.getString("estate"));
                            Log.e("Data response: ", data);


                            estateCurrentVal = Integer.parseInt(roe5.getText().toString());
                            Log.e("hell1",estateCurrentVal+"");


                            updateId.setText(lastElement.getString("id"));
                            roeNext.setVisibility(View.VISIBLE);
                            addbeneficary.setVisibility(View.GONE);
                            addstep.setVisibility(View.GONE);
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
