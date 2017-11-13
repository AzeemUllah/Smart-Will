package com.example.smart_will;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {
    SessionManager session;
    ImageButton back;
    RadioButton button_spouse,button_test;
    RadioGroup test_group, spouse_group;
    EditText t1,t2,t3,t4,t5,t6,t7;
    EditText s1,s2,s3,s4,s5,s6,s7;
    EditText ex1,ex2,ex3,ex4,ex5,ex7;
    EditText g1,g2,g3,g4,g5,g7;
    EditText gf1,gf2,gf3,gf4,gf5;
    EditText rf1,rf2,rf3,rf4,rf5;
    TextView s8,t8,ex6,g6;
    Button testator,executor,guardian,gift,residue,spouse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);

        t1=(EditText) findViewById(R.id.t1);
        t2=(EditText) findViewById(R.id.t2);
        t3=(EditText) findViewById(R.id.t3);
        t4=(EditText) findViewById(R.id.t4);
        t5=(EditText) findViewById(R.id.t5);
        t6=(EditText) findViewById(R.id.t6);
        t7=(EditText) findViewById(R.id.t7);
        t8=(TextView) findViewById(R.id.t8);
       test_group = (RadioGroup) findViewById(R.id.Rbg);

        s1=(EditText)findViewById(R.id.sp1);
        s2=(EditText)findViewById(R.id.sp2);
        s3=(EditText)findViewById(R.id.sp3);
        s4=(EditText)findViewById(R.id.sp4);
        s5=(EditText)findViewById(R.id.sp5);
        s6=(EditText)findViewById(R.id.sp6);
        s7=(EditText)findViewById(R.id.sp7);
        s8=(TextView) findViewById(R.id.sp8);
        spouse_group = (RadioGroup) findViewById(R.id.sp9);

        ex1=(EditText)findViewById(R.id.Ex_t1);
        ex2=(EditText)findViewById(R.id.Ex_t2);
        ex3=(EditText)findViewById(R.id.Ex_t3);
        ex4=(EditText)findViewById(R.id.Ex_t4);
        ex5=(EditText)findViewById(R.id.Ex_t5);
        ex6=(TextView) findViewById(R.id.Ex_t6);
        ex7=(EditText)findViewById(R.id.Ex_t7);

        g1=(EditText)findViewById(R.id.Grd1);
        g2=(EditText)findViewById(R.id.Grd2);
        g3=(EditText)findViewById(R.id.Grd3);
        g4=(EditText)findViewById(R.id.Grd4);
        g5=(EditText)findViewById(R.id.Grd5);
        g6=(TextView) findViewById(R.id.Grd6);
        g7=(EditText)findViewById(R.id.Grd7);

        gf1=(EditText)findViewById(R.id.gf1);
        gf2=(EditText)findViewById(R.id.gf2);
        gf3=(EditText)findViewById(R.id.gf3);
        gf4=(EditText)findViewById(R.id.gf4);
        gf5=(EditText)findViewById(R.id.gf5);

        rf1=(EditText)findViewById(R.id.rfs1);
        rf2=(EditText)findViewById(R.id.rfs2);
        rf3=(EditText)findViewById(R.id.rfs3);
        rf4=(EditText)findViewById(R.id.rfs4);
        rf5=(EditText)findViewById(R.id.rfs5);

        executor=(Button)findViewById(R.id.next_executor);
        spouse=(Button)findViewById(R.id.next_spouse);
        testator=(Button)findViewById(R.id.next_testator);
        gift=(Button)findViewById(R.id.next_gift);
        guardian=(Button)findViewById(R.id.next_guardian);
        residue=(Button)findViewById(R.id.next_res);
        back=(ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Edit.this,Submission.class);
                startActivity(in);
            }
        });



        get_testator_Data(uid);
        get_spouse_Data(uid);
        get_Executor_Data(uid);
        get_Guardian_Data(uid);
        get_Gift_Data(uid);
        get_residue_Data(uid);

        executor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_up_executor_Data(uid);
            }
        });
        spouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_up_spouse_Data(uid);
            }
        });
        testator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_up_test_Data(uid);
            }
        });

        residue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_up_res_Data(uid);
            }


        });
        guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_up_guar_Data(uid);
            }
        });

        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_up_gift_Data(uid);
            }
        });


    }




    private void get_Executor_Data(final String uid) {

        String url12 = "http://110.37.231.10:8080/projects/Test_laravel/public/executor_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url12,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_Executor_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void get_residue_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/residue_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_residue_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void get_Gift_Data(final String uid) {

        String url12 = "http://110.37.231.10:8080/projects/Test_laravel/public/gift_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url12,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_Gift_JSON(response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void get_Guardian_Data(final String uid) {

        String url13 = "http://150.129.5.74:8080/projects/Test_laravel/public/guardians_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url13,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_Guardian_JSON(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void get_spouse_Data(final String uid) {

        String url1 = "http://110.37.231.10:8080/projects/Test_laravel/public/spouse_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_spouse_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void get_testator_Data(final String uid) {

        String url = "http://110.37.231.10:8080/projects/Test_laravel/public/testator_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_test_JSON(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
               map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void show_spouse_JSON(String response){
        String name="N/A";
        String mname="N/A";
        String sname = "N/A";
        String addres="N/A";
        String post="N/A";
        String tele = "N/A";
        String email="N/A";
        String dob="N/A";
        String gender="";
        try {
            if(response.equals("{\"result\":[]}")) {

                s1.setText(name, EditText.BufferType.EDITABLE);
                s2.setText(mname, EditText.BufferType.EDITABLE);
                s3.setText(sname, EditText.BufferType.EDITABLE);
                s4.setText(addres, EditText.BufferType.EDITABLE);
                s5.setText(post, EditText.BufferType.EDITABLE);
                s6.setText(tele, EditText.BufferType.EDITABLE);

                s7.setText(email, EditText.BufferType.EDITABLE);
                s8.setText(dob, TextView.BufferType.EDITABLE);
            }
            else {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(0);
                name = collegeData.getString(Config.KEY_NAME);
                mname = collegeData.getString(Config.KEY_Mname);
                sname = collegeData.getString(Config.KEY_Sname);
                addres = collegeData.getString(Config.KEY_Address);
                post = collegeData.getString(Config.KEY_Postal);
                tele = collegeData.getString(Config.KEY_Telephone);
                email = collegeData.getString(Config.KEY_Email);
                dob = collegeData.getString(Config.KEY_DOB);
                gender = collegeData.getString("gender");
                s1.setText(name, EditText.BufferType.EDITABLE);
                s2.setText(mname, EditText.BufferType.EDITABLE);
                s3.setText(sname, EditText.BufferType.EDITABLE);
                s4.setText(addres, EditText.BufferType.EDITABLE);
                s5.setText(post, EditText.BufferType.EDITABLE);
                s6.setText(tele, EditText.BufferType.EDITABLE);

                s7.setText(email, EditText.BufferType.EDITABLE);
                s8.setText(dob, TextView.BufferType.EDITABLE);
                //fn.setText(name, TextView.BufferType.EDITABLE);
                if(gender.equals("Male"))
                {
                    ((RadioButton)spouse_group.getChildAt(0)).setChecked(true);
                }
                else if(gender.equals("Female"))
                {
                    ((RadioButton)spouse_group.getChildAt(1)).setChecked(true);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);


    }
    private void show_residue_JSON(String response){
        String name_res="";
        String mname_res="";
        String sname_res = "";
        String rel13_res="";
        String estate_res="";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name_res = collegeData.getString(Config.KEY_NAME);
            mname_res = collegeData.getString(Config.KEY_Mname);
            sname_res = collegeData.getString(Config.KEY_Sname);
            rel13_res = collegeData.getString(Config.KEY_REL);
            estate_res = collegeData.getString(Config.KEY_estate);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        rf1.setText(name_res, EditText.BufferType.EDITABLE);
        rf2.setText(mname_res, EditText.BufferType.EDITABLE);
        rf3.setText(sname_res, EditText.BufferType.EDITABLE);
        rf4.setText(rel13_res, EditText.BufferType.EDITABLE);
        rf5.setText(estate_res, EditText.BufferType.EDITABLE);




    }
    private void show_Gift_JSON(String response){
        String name="";
        String mname="";
        String sname = "";
        String rel1="";
        String gift="";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            rel1 = collegeData.getString(Config.KEY_REL);
            gift = collegeData.getString(Config.KEY_gifts);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        gf1.setText(name, EditText.BufferType.EDITABLE);
        gf2.setText(mname, EditText.BufferType.EDITABLE);
        gf3.setText(sname, EditText.BufferType.EDITABLE);
        gf4.setText(rel1, EditText.BufferType.EDITABLE);
        gf5.setText(gift, EditText.BufferType.EDITABLE);


    }
    private void show_Executor_JSON(String response){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String dob = "";
        String rel="";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            dob = collegeData.getString(Config.KEY_DOB);
            rel=collegeData.getString(Config.KEY_REL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        ex1.setText(name, EditText.BufferType.EDITABLE);
        ex2.setText(mname, EditText.BufferType.EDITABLE);
        ex3.setText(sname, EditText.BufferType.EDITABLE);
        ex4.setText(addres, EditText.BufferType.EDITABLE);
        ex5.setText(post, EditText.BufferType.EDITABLE);
        ex6.setText(dob, TextView.BufferType.EDITABLE);
        ex7.setText(rel, EditText.BufferType.EDITABLE);
  //fn.setText(name, TextView.BufferType.EDITABLE);

    }
    private void show_test_JSON(String response){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String tele = "";
        String email="";
        String dob="";
        String gender="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            tele = collegeData.getString(Config.KEY_Telephone);
            email = collegeData.getString(Config.KEY_Email);
            dob = collegeData.getString(Config.KEY_DOB);
            gender=collegeData.getString("gender");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        t1.setText(name, EditText.BufferType.EDITABLE);
        t2.setText(mname, EditText.BufferType.EDITABLE);
        t3.setText(sname, EditText.BufferType.EDITABLE);
        t4.setText(addres, EditText.BufferType.EDITABLE);
        t5.setText(post, EditText.BufferType.EDITABLE);
        t6.setText(tele, EditText.BufferType.EDITABLE);

        t7.setText(email, EditText.BufferType.EDITABLE);
        t8.setText(dob, TextView.BufferType.EDITABLE);
        if(gender.equals("Male"))
        {
            ((RadioButton)test_group.getChildAt(0)).setChecked(true);
        }
        else if(gender.equals("Female"))
        {
            ((RadioButton)test_group.getChildAt(1)).setChecked(true);
        }
        //fn.setText(name, TextView.BufferType.EDITABLE);

    }
    private void show_Guardian_JSON(String response){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String dob = "";
        String rel="";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            dob = collegeData.getString(Config.KEY_DOB);
            rel=collegeData.getString(Config.KEY_REL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
        g1.setText(name, EditText.BufferType.EDITABLE);
        g2.setText(mname, EditText.BufferType.EDITABLE);
        g3.setText(sname, EditText.BufferType.EDITABLE);
        g4.setText(addres, EditText.BufferType.EDITABLE);
        g5.setText(post, EditText.BufferType.EDITABLE);
        g6.setText(dob, TextView.BufferType.EDITABLE);
        g7.setText(rel, EditText.BufferType.EDITABLE);
        //fn.setText(name, TextView.BufferType.EDITABLE);

    }


    private void get_up_res_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/residue_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "hi", Toast.LENGTH_SHORT).show();


                        show__up_residue_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show__up_residue_JSON(String response,String uid){
        String name_res="";
        String mname_res="";
        String sname_res = "";
        String rel13_res="";
        String estate_res="";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name_res = collegeData.getString(Config.KEY_NAME);
            mname_res = collegeData.getString(Config.KEY_Mname);
            sname_res = collegeData.getString(Config.KEY_Sname);
            rel13_res = collegeData.getString(Config.KEY_REL);
            estate_res = collegeData.getString(Config.KEY_estate);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        update_residue(uid,name_res,mname_res,sname_res,rel13_res,estate_res);



    }
    private void update_residue(final String uid, final String name_res, final String mname_res, final String sname_res, final String rel13_res, final String estate_res) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/residue_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "Sucess", Toast.LENGTH_SHORT).show();


                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("name",rf1.getText().toString());
                map.put("old_name",name_res);
                map.put("mdname",rf2.getText().toString());
                map.put("old_mname",mname_res);
                map.put("srname",rf3.getText().toString());
                map.put("old_srname",sname_res);
                map.put("relation",rf4.getText().toString());
                map.put("old_relation",rel13_res);
                map.put("estate",rf5.getText().toString());
                map.put("old_estate",estate_res);
                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void get_up_test_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/testator_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show__up_test_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show__up_test_JSON(String response,String uid){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String tele = "";
        String email="";
        String dob="";
        String gender="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            tele = collegeData.getString(Config.KEY_Telephone);
            email = collegeData.getString(Config.KEY_Email);
            dob = collegeData.getString(Config.KEY_DOB);
            gender=collegeData.getString("gender");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, t1.getText().toString(), Toast.LENGTH_SHORT).show();
        update_test(uid,name,mname,sname,addres,post,tele,email,dob,gender);



    }
    private void update_test(final String uid, final String name, final String mname,
                             final String sname, final String addres,
                             final String post, final String tele, final String email,
                             final String dob,final String gender) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/testator_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "Sucess", Toast.LENGTH_SHORT).show();


                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int selectedId = test_group.getCheckedRadioButtonId();

                button_test = (RadioButton) findViewById(selectedId);
                Map<String,String> map = new HashMap<String,String>();

                map.put("name",t1.getText().toString());
                map.put("old_name",name);
                map.put("mdname",t2.getText().toString());
                map.put("old_mname",mname);
                map.put("srname",t3.getText().toString());
                map.put("old_srname",sname);
                map.put("adres",t4.getText().toString());
                map.put("old_adres",addres);
                map.put("pst-code",t5.getText().toString());
                map.put("old_pst-code",post);
                map.put("telephone",t6.getText().toString());
                map.put("old_telephone",tele);
                map.put("email",t7.getText().toString());
                map.put("old_email",email);
                map.put("dob",t8.getText().toString());
                map.put("old_dob",dob);
                map.put("gender",button_test.getText().toString());
                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void get_up_spouse_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/spouse_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_up_spouse_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show_up_spouse_JSON(String response,String uid){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String tele = "";
        String email="";
        String dob="";
        String gender="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            tele = collegeData.getString(Config.KEY_Telephone);
            email = collegeData.getString(Config.KEY_Email);
            dob = collegeData.getString(Config.KEY_DOB);
            gender=collegeData.getString("gender");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, s1.getText().toString(), Toast.LENGTH_SHORT).show();
        update_spouse(uid,name,mname,sname,addres,post,tele,email,dob,gender);



    }
    private void update_spouse(final String uid, final String name, final String mname,
                             final String sname, final String addres,
                             final String post, final String tele, final String email,
                             final String dob,final String gender) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/spouse_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "Sucess", Toast.LENGTH_SHORT).show();


                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int selectedId = spouse_group.getCheckedRadioButtonId();
                button_spouse = (RadioButton) findViewById(selectedId);
                Map<String,String> map = new HashMap<String,String>();

                map.put("name",s1.getText().toString());
                map.put("old_name",name);
                map.put("mdname",s2.getText().toString());
                map.put("old_mname",mname);
                map.put("srname",s3.getText().toString());
                map.put("old_srname",sname);
                map.put("adres",s4.getText().toString());
                map.put("old_adres",addres);
                map.put("pst-code",s5.getText().toString());
                map.put("old_pst-code",post);
                map.put("telephone",s6.getText().toString());
                map.put("old_telephone",tele);
                map.put("email",s7.getText().toString());
                map.put("old_email",email);
                map.put("dob",s8.getText().toString());
                map.put("old_dob",dob);
                map.put("gender",button_spouse.getText().toString());
                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void get_up_executor_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/executor_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_up_executor_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show_up_executor_JSON(String response,String uid){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String dob="";
        String rel = "";
        String gender="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            dob = collegeData.getString(Config.KEY_DOB);
            rel = collegeData.getString(Config.KEY_REL);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, s1.getText().toString(), Toast.LENGTH_SHORT).show();
        update_executor(uid,name,mname,sname,addres,post,dob,rel);



    }
    private void update_executor(final String uid, final String name, final String mname,
                               final String sname, final String addres,
                               final String post, final String dob,final String rel) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/executor_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();

                map.put("name",ex1.getText().toString());
                map.put("old_name",name);
                map.put("mdname",ex2.getText().toString());
                map.put("old_mname",mname);
                map.put("srname",ex3.getText().toString());
                map.put("old_srname",sname);
                map.put("adres",ex4.getText().toString());
                map.put("old_adres",addres);
                map.put("pst-code",ex5.getText().toString());
                map.put("old_pst-code",post);
                map.put("dob",ex6.getText().toString());
                map.put("old_dob",dob);
                map.put("relation",ex7.getText().toString());
                map.put("old_relation",rel);
                //map.put("gender",gender);

                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void get_up_guar_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/guardians_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_up_guar_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show_up_guar_JSON(String response,String uid){
        String name="";
        String mname="";
        String sname = "";
        String addres="";
        String post="";
        String dob="";
        String rel = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            addres = collegeData.getString(Config.KEY_Address);
            post = collegeData.getString(Config.KEY_Postal);
            dob = collegeData.getString(Config.KEY_DOB);
            rel = collegeData.getString(Config.KEY_REL);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, s1.getText().toString(), Toast.LENGTH_SHORT).show();
        update_guar(uid,name,mname,sname,addres,post,dob,rel);



    }
    private void update_guar(final String uid, final String name, final String mname,
                                 final String sname, final String addres,
                                 final String post, final String dob,final String rel) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/guardians_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "Sucess", Toast.LENGTH_SHORT).show();


                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("name",g1.getText().toString());
                map.put("old_name",name);
                map.put("mdname",g2.getText().toString());
                map.put("old_mname",mname);
                map.put("srname",g3.getText().toString());
                map.put("old_srname",sname);
                map.put("adres",g4.getText().toString());
                map.put("old_adres",addres);
                map.put("pst-code",g5.getText().toString());
                map.put("old_pst-code",post);
                map.put("dob",g6.getText().toString());
                map.put("old_dob",dob);
                map.put("relation",g7.getText().toString());
                map.put("old_relation",rel);

                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void get_up_gift_Data(final String uid) {

        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/gift_fatch";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();


                        show_up_gift_JSON(response,uid);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("u_id",uid);
//                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void show_up_gift_JSON(String response,String uid){
        String name="";
        String mname="";
        String sname = "";
        String rel = "";
        String gifts = "";



        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(Config.KEY_NAME);
            mname = collegeData.getString(Config.KEY_Mname);
            sname = collegeData.getString(Config.KEY_Sname);
            rel = collegeData.getString(Config.KEY_REL);
            gifts=collegeData.getString("gifts");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, s1.getText().toString(), Toast.LENGTH_SHORT).show();
        update_gift(uid,name,mname,sname,rel,gifts);



    }
    private void update_gift(final String uid, final String name, final String mname,
                             final String sname,final String rel,final String gifts) {
        String url123 = "http://110.37.231.10:8080/projects/Test_laravel/public/gift_update";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url123,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Edit.this, "Sucess", Toast.LENGTH_SHORT).show();


                        // show_residue_JSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edit.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("name",gf1.getText().toString());
                map.put("old_name",name);
                map.put("mdname",gf2.getText().toString());
                map.put("old_mname",mname);
                map.put("srname",gf3.getText().toString());
                map.put("old_srname",sname);

                map.put("relation",gf4.getText().toString());
                map.put("old_relation",rel);

                map.put("gift",gf5.getText().toString());
                map.put("old_gift",gifts);



                map.put("uid",uid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
