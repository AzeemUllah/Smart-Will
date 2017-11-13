package com.example.smart_will;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.zip.Inflater;

import utilities.NetworkUtilities;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static String percentageCal = "100";




    //public static String percentageCal = "100";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(percentageCal, "100");


        // commit changes
        editor.commit();
    }











    /* Azeem's Session management */

    private static final String USER_ID = "0";
    private static String currentClass = "login";
    public static JSONArray giftsList = null;
    public static int giftsCurrent = -1;

    public static JSONArray residueList = null;
    public static int currentResidue = -1;

    public static String emailAddress = "";


    public void setSession(String id){
        editor.putString(USER_ID,id);
        editor.commit();
        Log.e("SESSION INIT ",pref.getString(USER_ID,"0"));
    }

    public String getSession(){
        return pref.getString(USER_ID,"0");
    }







    public void statusLogin(String currentClass){
        Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
        this.currentClass = currentClass;

        if(pref.getString(USER_ID,"0").equals("0")){
            Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
            Intent i = new Intent(_context, logina1.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
        else{
            String url = "http://128.199.50.69/api/api-getCurrentWorkingClass.php";
            String message = "";
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("id",  pref.getString(USER_ID,"0"));
                message = jsonBody.toString();
                Log.e("Data response: ","SessionManager.java" + message);
            }
            catch (Exception e){
                Log.e("JSON error: " , e.toString());
            }
            SessionManager.ParallelExec pe = new SessionManager.ParallelExec();
            pe.execute(url,message);
        }
    }


        class ParallelExec extends AsyncTask<String, Void, String> {
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
                        String className = jsonResult.getString("Data");
                        shiftClass(className);
                    }
                    else if(status.equals("Error")) {
                        String error = jsonResult.getString("Error");
                        if (error.equals("Invalid Credentials")) {
                            Log.e("Failed ","Fetching current working class failed in SessionManager.java");
                        }
                        else if(error.equals("User Doesnot Exists")){
                            Intent i = new Intent(_context, logina1.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("stop", "yes");
                            _context.startActivity(i);
                        }
                        else{
                            Log.e("Failed ","An Unexpected Error Occured in SessionManager.java! Please Try again.");
                        }
                    }
                }
                catch(Exception e){
                    Log.e("JSON error: " , "In SessionManager.java" + e.toString());
                }
            }
            @Override
            protected void onPreExecute() {
                // TODO: Loader and stuff to add later here.
            }
            @Override
            protected void onProgressUpdate(Void... values) {}
        }

        public String getCurrentClass(){
            return currentClass;
        }

        public void shiftClass(String className){
            Log.e("CLASSNAME",className);
            if(className.equals("testator") && !this.currentClass.equals("testator")){
                Log.e("CLASSNAME","here");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, testator_details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("spouse") && !this.currentClass.equals("spouse")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Spouse.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("executer") && !this.currentClass.equals("executer")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Executors.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("guardian") && !this.currentClass.equals("guardian")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Guardians.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("giftsLegacy") && !this.currentClass.equals("giftsLegacy")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Gifts_and_legacy.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("giftsLegacy2") && !this.currentClass.equals("giftsLegacy2")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Gifts_and_Legacy2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("gifts") && !this.currentClass.equals("gifts")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Gifts.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("residueOption") && !this.currentClass.equals("residueOption")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Info_block.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("residue") && !this.currentClass.equals("residue")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Residue_of_estate.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("special_req") && !this.currentClass.equals("special_req")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Special_request.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
            else if(className.equals("submission") && !this.currentClass.equals("submission")){
                Log.e("CLASSNAME","there");
                Log.e("CHECKING ID: " , pref.getString(USER_ID,"0"));
                Intent i = new Intent(_context, Submission.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("stop", "yes");
                _context.startActivity(i);
            }
        }




    public void setPrecentageNewValue(String newValue){
        this.percentageCal = newValue;
    }




    public void setCurrentClass(String currentClass){


        String url = "http://128.199.50.69/api/api-setCurrentWorkingClass.php";
        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", pref.getString(USER_ID, "0")); //TODO: Session id validation (if/else)
            jsonBody.put("current_class", currentClass);
            message = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
        }
        SessionManager.setCurrentClass scc = new SessionManager.setCurrentClass();



        if(!this.currentClass.equals("submission")) {
            if(!this.currentClass.equals("special_req") && currentClass.equals("submission")) {
                scc.execute(url, message);
            }
            else{
                scc.execute(url, message);
            }
        }
    }

    private class setCurrentClass extends AsyncTask<String, Void, String> {
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
                    // Toast.makeText(getApplicationContext(), "Testator added!", Toast.LENGTH_SHORT).show();
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@SessionManager.java");
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

        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


















    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public String checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
            return "true";

        }
        else {
            return "false";
        }
    }




    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences

        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, logina1.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}