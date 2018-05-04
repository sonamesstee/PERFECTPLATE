package com.example.sonamessteetenzin.perfectplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class  Login extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    Button login;
    EditText edtuname, edtpasw;
    TextView gotoreg;
    String uname, pasw;
    String login_url = "http://10.2.8.139/login.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtuname = (EditText) findViewById(R.id.edtuname);
        edtpasw = (EditText) findViewById(R.id.edtpasw);
        login = (Button) findViewById(R.id.btnlogin);
        gotoreg = (TextView) findViewById(R.id.btnreg);

        //initialize the builder
        builder = new AlertDialog.Builder(Login.this);

        //setting on click listener
        login.setOnClickListener(this);

        //setting on click listener
        gotoreg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == login)
        {
            flogin();
        }
        if(v == gotoreg)
        {
            Intent intent = new Intent(Login.this,Registration.class );
            startActivity(intent);
        }
    }
    public void flogin()
    {
        uname = edtuname.getText().toString();
        pasw = edtpasw.getText().toString();
        String user = uname;
        String pass = pasw;

        if(uname.isEmpty() || pasw.isEmpty())
        {
            builder.setTitle("Something Went Wrong");
            builder.setMessage("Fill empty fields");
            displayAlert("input error");
        }
        else if(!(uname.matches(user)))
        {
            builder.setTitle("Invalid User");
            builder.setMessage("Username is incorrect");
            displayAlert("input error");
        }
        else if(!(pasw.matches(pass)))
        {
            builder.setTitle("Invalid Password");
            builder.setMessage("your password is incorrect");
            displayAlert("input error");
        }
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {

                            try
                            {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                //fetch data from server
                                String code = jsonObject.getString("code");
                                if(code.equals("Login Failed"))
                                {
                                    builder.setTitle("Login Error");
                                    displayAlert(jsonObject.getString("message"));
                                }
                                else{
                                    Intent intent = new Intent(Login.this,ProfileCustomer.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", uname);
                    params.put("password", pasw);

                    return params;
                }
            };
            MySingleton.getmInstance(Login.this).addtoRequestqueue(stringRequest);
        }
    }
    public void displayAlert(String message) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtuname.setText("");
                edtpasw.setText("");
            }
        });

        builder.setNegativeButton("cancel", null);
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Login.super.onBackPressed();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}

