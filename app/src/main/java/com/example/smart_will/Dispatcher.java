package com.example.smart_will;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Dispatcher extends Activity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
       // Toast.makeText(this, session.checkLogin(), Toast.LENGTH_SHORT).show();
        if(session.checkLogin().equals("true")) {
            startActivity(new Intent(Dispatcher.this, MainActivity.class));
        }
        else
            dispatch();

    }

    private void dispatch() {
        Class<?> activityClass;

        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", testator_details.class.getName()));

        } catch(ClassNotFoundException ex) {
            activityClass = testator_details.class;
        }

        startActivity(new Intent(this, activityClass));
        finish();

    }
}