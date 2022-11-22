package com.ass2.chalja;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

        progressDialog = new ProgressDialog(this);

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
                registerUser();

                Intent i = new Intent(screen2.this, screen3.class);
                startActivity(i);

            }
        });
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest request=new StringRequest(
                Request.Method.POST,
                "http://172.16.34.212/smd2/register2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            if(obj.getInt("resultcode")==1)
                            {
                                Toast.makeText(
                                        screen2.this,
                                        obj.get("msg").toString()
                                        ,Toast.LENGTH_LONG
                                ).show();
                            }
                            else{
                                Toast.makeText(
                                        screen2.this,
                                        obj.get("msg").toString()
                                        ,Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(
                                    screen2.this,
                                    "Incorrect JSON"
                                    ,Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                screen2.this,
                                error.getMessage()
                                ,Toast.LENGTH_LONG
                        ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}