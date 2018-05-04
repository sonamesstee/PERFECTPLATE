package com.example.sonamessteetenzin.perfectplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class Registration extends AppCompatActivity implements View.OnClickListener{

    EditText Name, Email, Uname, Pasw;
    Button Reg;
    String URL = "http://10.0.2.2/register.php";
    String name,email,uname,pasw;
    AlertDialog.Builder builder;
    Intent i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //initialise variables
        Name = (EditText) findViewById(R.id.edtname);
        Email = (EditText) findViewById(R.id.edtemail);
        Uname = (EditText) findViewById(R.id.edtuname);
        Pasw = (EditText) findViewById(R.id.edtpasw);
        Reg = (Button) findViewById(R.id.btnReg);
        i=new Intent(Registration.this,Login.class);

        //initialize the builder
        builder = new AlertDialog.Builder(Registration.this);

        //setting on click listener
        Reg.setOnClickListener(this);

    }
    @Override
    public void onClick (View v)
    {
        name = Name.getText().toString();
        email = Email.getText().toString();
        uname = Uname.getText().toString();
        pasw = Pasw.getText().toString();

        //Checking condition before sending the information to the server
        if (name.equals("") || email.equals("") || uname.equals("") || pasw.equals(""))
        {
            builder.setTitle("Something went wrong");
            builder.setMessage("Please Fill all the Fields");
            displayAlert("Input Error");
        }

        //if password is matching we have to register with new user
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                //fetch data from server
                                String code = jsonObject.getString("code");
                                String message = jsonObject.getString("message");
                                builder.setTitle("Server Response");
                                builder.setMessage(message); //messgae from server
                                displayAlert(code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("Alert Dialog",e.getMessage().toString());
                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(Registration.this, error.toString(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("username", uname);
                    params.put("password", pasw);

                    return params;
                }
            };
            MySingleton.getmInstance(Registration.this).addtoRequestqueue(stringRequest);
        }
    }
    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(code.equals("Reg Success"))
                {
                    finish();
                    startActivity(i);


                }
                else if(code.equals("Reg failed"))
                {
                    // reset field
                    Name.setText("");
                    Email.setText("");
                    Uname.setText("");
                    Pasw.setText("");
                }
            }
        });
        //to display AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

