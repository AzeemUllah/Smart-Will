package com.example.smart_will;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smart_will.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class video extends AppCompatActivity {
        private StorageReference mStorage;
        private ProgressDialog mProgress;
        private String mFilename=null;
    Button but;

    final private int VIDEO_REQUEST_CODE=100;
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main2);
        mStorage= FirebaseStorage.getInstance().getReference();
        mProgress=new ProgressDialog(this);
        but=(Button)findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureVideo(view);
            }
        });

    }


    public void captureVideo(View view)
    {
        Intent camera=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File video_path=getFilePath();
        Uri video_uri=Uri.fromFile(video_path);
        camera.putExtra(MediaStore.EXTRA_OUTPUT,video_uri);
        camera.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 180);
        camera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(camera,VIDEO_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==VIDEO_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                // Create a storage reference from our app
                StorageReference storage=FirebaseStorage.getInstance().getReference();

                Uri uri = data.getData();
                Log.d("uri",uri.toString());
                StorageReference riversRef = storage.child("Video/"+uri.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(uri);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(video.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                        Toast.makeText(video.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Video Capture Failed", Toast.LENGTH_SHORT).show();
        }


    }

    public File getFilePath()
    {
        File folder=new File("sdcard");
        if(folder.exists())
        {
            folder.mkdir();

        }
            File video_folder = new File(folder,"sample_video.mp4");

    return video_folder;
    }

}
