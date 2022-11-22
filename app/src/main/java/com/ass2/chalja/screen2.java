package com.ass2.chalja;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class screen2 extends AppCompatActivity {

    TextView tv,signup;
    EditText etUsername, etEmail, etPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);
        tv=findViewById(R.id.registerUserTVSignin);
        signup=findViewById(R.id.signUp);
        etUsername=findViewById(R.id.registerUserETName);
        etEmail=findViewById(R.id.registerUserETEmail);
        etPassword=findViewById(R.id.registerUserETPassword);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(screen2.this, screen3.class);
                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username, email, password;
                username = etUsername.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if(!username.equals("") && !email.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
//                    progressDialog.show();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "username";
                            field[1] = "email";
                            field[2] = "password";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = username;
                            data[1] = email;
                            data[2] = password;
                            PutData putData = new PutData("https://172.16.44.56/smd3/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();

                                    if(result.equals("Sign Up Success")){
//                                        progressDialog.hide();
                                        Toast.makeText(screen2.this, result, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(screen2.this, screen3.class));
                                        finish();
                                    } else {
                                        Log.i("signUpFailed", result);
                                        Toast.makeText(screen2.this, result, Toast.LENGTH_SHORT).show();
                                    }

                                    Log.i("PutData", result);
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(screen2.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.url_registerUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

//                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Error Message", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };


//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

//    @Override
//    public void onClick(View view) {
//        if (view == signup)
//            registerUser();
//        if(view == tv)
//            startActivity(new Intent(this, screen3.class));
//    }

}