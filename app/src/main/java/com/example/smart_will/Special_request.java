package com.example.smart_will;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import utilities.NetworkUtilities;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class Special_request extends AppCompatActivity {
    Button SpNext, vr,save,retake, captureVideo;
    TextView uploading, uploaded;
    ProgressBar uploadSpinner;
    RadioGroup rg1,rg2,rg3;
    RadioButton r1,r2,r3,r4,r5,r6;
    EditText ed,ed2,updateId;
    ImageButton but;
    final private int VIDEO_REQUEST_CODE=100;
    int amountSpecialReq = -1;
    //String videoUploadUrl = "0";
    String videoUploadUrl = "";

    private static final int  MY_PERMISSIONS_REQUEST_FOR_VIDEO = 0;

    String SERVER_URL = "http://128.199.50.69/api/api-uploadVideo.php";

    static final int REQUEST_VIDEO_CAPTURE = 1;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());




        if(getIntent().hasExtra("stop")) {
            if (getIntent().getStringExtra("stop").equals("yes")) {
                session.statusLogin("special_req");
            }
            else{
                session.setCurrentClass("special_req");
            }
        }


        uploading = (TextView) findViewById(R.id.uploading);
        uploaded = (TextView) findViewById(R.id.uploaded);


        retake = (Button) findViewById(R.id.retake);


        uploadSpinner = (ProgressBar) findViewById(R.id.uploadSpinner);

        captureVideo = (Button) findViewById(R.id.capturevideo1);

        captureVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(Special_request.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Special_request.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Special_request.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Special_request.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Special_request.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


                        ActivityCompat.requestPermissions(Special_request.this,
                                new String[]{android.Manifest.permission.RECORD_AUDIO,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.READ_PHONE_STATE,
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA
                                },
                                MY_PERMISSIONS_REQUEST_FOR_VIDEO);

                    }
                    else{
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE).putExtra(MediaStore.EXTRA_DURATION_LIMIT,180);
                        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                        }
                    }

                } else{
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE).putExtra(MediaStore.EXTRA_DURATION_LIMIT,180);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }
                }





            }
        });

        retake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                    ActivityCompat.requestPermissions(Special_request.this,
                            new String[]{android.Manifest.permission.RECORD_AUDIO,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_PHONE_STATE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            },
                            MY_PERMISSIONS_REQUEST_FOR_VIDEO);





                } else{
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE).putExtra(MediaStore.EXTRA_DURATION_LIMIT,180);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }
                }



            }
        });







        ed=(EditText)findViewById(R.id.music);
        ed2=(EditText)findViewById(R.id.sprq);
        updateId=(EditText)findViewById(R.id.updateSpecialRequestId);
        rg1 =(RadioGroup)findViewById(R.id.Rbg1);
        rg2 =(RadioGroup)findViewById(R.id.Rbg2);
        rg3 =(RadioGroup)findViewById(R.id.Rbg3);
        r1 = (RadioButton)findViewById(R.id.r1);
        r2 = (RadioButton)findViewById(R.id.r2);

        r3 = (RadioButton)findViewById(R.id.r3);
        r4 = (RadioButton)findViewById(R.id.r4);

        r5 = (RadioButton)findViewById(R.id.r5);
        r6 = (RadioButton)findViewById(R.id.r6);




        checkIfExists("check");
















            but = (ImageButton) findViewById(R.id.back);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Special_request.this, Info_block.class).putExtra("back",true).putExtra("backwards",true));
                }
            });

        SpNext = (Button)findViewById(R.id.spNext);
        SpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (rg1.getCheckedRadioButtonId() == -1)
                {
                    rg1.requestFocus();
                    Toast.makeText(Special_request.this, "Please select one process", Toast.LENGTH_SHORT).show();
                }
                else if (rg2.getCheckedRadioButtonId() == -1)
                {
                    rg2.requestFocus();
                    Toast.makeText(Special_request.this, "Please select owner status", Toast.LENGTH_SHORT).show();
                }
                else if (rg3.getCheckedRadioButtonId() == -1)
                {
                    rg2.requestFocus();
                    Toast.makeText(Special_request.this, "Please select funeral plan", Toast.LENGTH_SHORT).show();
                }
//                else if(videoUploadUrl.equals("0") || videoUploadUrl.equals("-1") || videoUploadUrl.equals("-2")){
//                    Toast.makeText(Special_request.this, "Please record a video", Toast.LENGTH_SHORT).show();
//                }
                else {
                    checkIfExists("next");
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
            Intent in=new Intent(Special_request.this,logina1.class);
            startActivity(in);
            return(true);



    }
        return(super.onOptionsItemSelected(item));
    }


    public void checkIfExists(String protocol){

        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user_id", session.getSession()); //TODO: Session id validation (if/else)
            message = jsonBody.toString();
        } catch (Exception e) {
            Log.e("JSON error: ", e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        String url = "http://128.199.50.69/api/api-specialRequestAmount.php";
        Special_request.CheckIfAlready checkObject = new Special_request.CheckIfAlready();
        checkObject.execute(url, message, protocol);
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
                    amountSpecialReq = Integer.parseInt(data);
                    Log.e("Count is: ", amountSpecialReq + "");

                    if(protocol.equals("check")){
                        if(amountSpecialReq > 0){
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
                            String url = "http://128.199.50.69/api/api-getSpecialRequest.php";
                            Special_request.autoFill pe = new Special_request.autoFill();
                            pe.execute(url,message);
                        }
                    }
                    else if(protocol.equals("next")){
                        if(amountSpecialReq < 1){
                            insert("next");
                        }
                        else{
                            insert("update");
                        }
                    }



                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Special Request");
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





    public void insert(String protocol){
        String url = "http://128.199.50.69/api/api-insertSpecialRequest.php";

        if(protocol.equals("update")){
            url = "http://128.199.50.69/api/api-updateSpecialRequest.php";
        }

        String message = "";
        try {
            JSONObject jsonBody = new JSONObject();

            ed=(EditText)findViewById(R.id.music);
            ed2=(EditText)findViewById(R.id.sprq);
            rg1 =(RadioGroup)findViewById(R.id.Rbg1);
            rg2 =(RadioGroup)findViewById(R.id.Rbg2);
            rg3 =(RadioGroup)findViewById(R.id.Rbg3);

            jsonBody.put("buried_cremated", ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString());
            jsonBody.put("home_owner", ((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString());
            jsonBody.put("music", ed.getText().toString());
            jsonBody.put("prepaid_plan", ((RadioButton)findViewById(rg3.getCheckedRadioButtonId())).getText().toString());
            jsonBody.put("request", ed2.getText().toString());
            jsonBody.put("video_name", videoUploadUrl);

            if(protocol.equals("update")){
                jsonBody.put("updateId", updateId.getText().toString());
            }

            jsonBody.put("user_id", session.getSession());
            message = jsonBody.toString();
        }
        catch (Exception e){
            Log.e("JSON error: " , e.toString());
            Toast.makeText(getApplicationContext(), "An Unexpected Error Occured! Please Try again.", Toast.LENGTH_SHORT).show();
        }
        Special_request.ConnectSave insertObject = new Special_request.ConnectSave();
        insertObject.execute(url,message);
    }

    private class ConnectSave extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            return NetworkUtilities.postData(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Data response: ",result);
            try{
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("Status");

                if(status.equals("Ok")){
                    startActivity(new Intent(Special_request.this, Submission.class).putExtra("stop","no"));
                }
                else if(status.equals("Error")) {
                    String error = jsonResult.getString("Error");
                    Log.e("Api error ", "@Special Request");
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





                    ed.setText(dataJson.getString("music"));
                    ed2.setText(dataJson.getString("request"));
                    updateId.setText(dataJson.getString("id"));

                    if(dataJson.getString("buried_cremated").equals("Buried")){
                        r1.setChecked(true);
                        r2.setChecked(false);
                    }
                    else if(dataJson.getString("buried_cremated").equals("Cremated")){
                        r1.setChecked(false);
                        r2.setChecked(true);
                    }

                    if(dataJson.getString("home_owner").equals("Yes")){
                        r3.setChecked(true);
                        r4.setChecked(false);
                    }
                    else if(dataJson.getString("home_owner").equals("No")){
                        r3.setChecked(false);
                        r4.setChecked(true);
                    }

                    if(dataJson.getString("prepaid_plan").equals("Yes")){
                        r5.setChecked(true);
                        r6.setChecked(false);
                    }
                    else if(dataJson.getString("prepaid_plan").equals("No")){
                        r5.setChecked(false);
                        r6.setChecked(true);
                    }

                    videoUploadUrl = dataJson.getString("video_storage");

                    if(!dataJson.getString("video_storage").equals("")){
                        captureVideo.setVisibility(View.GONE);
                        retake.setVisibility(View.VISIBLE);
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














    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            try {

                if (requestCode == REQUEST_VIDEO_CAPTURE) {
                    if (resultCode == RESULT_OK) {
                        Uri videoUri = intent.getData();

                        String url = getRealPathFromUri(this,videoUri);

                        //videoUploadUrl = videoUri.toString();
                        Log.e("path", url);

                        Special_request.ParallelExec pe = new Special_request.ParallelExec();
                        pe.execute(url);
                    } else if (resultCode == RESULT_CANCELED) {
                        videoUploadUrl = "-1";
                    } else {
                        videoUploadUrl = "-2";
                    }
                }
            }
            catch(Exception e){
                Log.e("Unexpected error","@video recording " + e.toString());
            }


        }
//        if (requestCode == REQUEST_VIDEO_CAPTURE) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Video saved to:\n" +
//                        intent.getData(), Toast.LENGTH_LONG).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Video recording cancelled.",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(this, "Failed to record video",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
    }



    private class ParallelExec extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Log.e("Started","abc");
            return Integer.toString(uploadFile(params[0]));

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Ended","abc");
            uploadSpinner.setVisibility(View.GONE);
            uploading.setVisibility(View.GONE);
            retake.setVisibility(View.VISIBLE);
            uploaded.setVisibility(View.VISIBLE);
            SpNext.setVisibility(View.VISIBLE);
            Log.e("Results", result);
        }

        @Override
        protected void onPreExecute() {
            uploaded.setVisibility(View.GONE);
            retake.setVisibility(View.GONE);
            captureVideo.setVisibility(View.GONE);
            uploading.setVisibility(View.VISIBLE);
            uploadSpinner.setVisibility(View.VISIBLE);
            SpNext.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }






    public int uploadFile(final String selectedFilePath) {

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("Doesn't Exist: " , selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);
                connection.setRequestProperty("user_id", session.getSession());


                //creating new dataoutputstream
                try {
                    dataOutputStream = new DataOutputStream(connection.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.e("Server Response", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoUploadUrl = fileName;
                            Log.e("Success", "Path is: " + fileName);
                            //tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Special_request.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(Special_request.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Special_request.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            //dialog.dismiss();
            return serverResponseCode;
        }
    }





    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FOR_VIDEO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE).putExtra(MediaStore.EXTRA_DURATION_LIMIT,180);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }

                } else {

                    Toast.makeText(Special_request.this, "Permissions Declined! Can't record video withon permissions. ", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}
