package com.example.streetfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class registerOper extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EMPTY = "";
    private static final String KEY_USERNAME = "reso_username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FULL_NAME = "reso__name";
    private static final String KEY_IDCARD = "idcard";
    private static final String KEY_ADDRESS = "reso_address";
    private static final String KEY_COUNTRY = "reso_count";
    private static final String KEY_TEL = "reso_tel";
    private static final String KEY_EMAIL = "reso_email";


    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private EditText etIDcard;
    private EditText etAddress;
    private EditText etcountry;
    private EditText etPhone;
    private EditText etEmail;


    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String idCard;
    private String address;
    private String country;
    private String phone;
    private String email;


    private ProgressDialog pDialog;
    private String register_url = "http://192.168.1.6/streetfood/registeroper.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register_oper);

        etFullName = findViewById(R.id.etFullName);
        etIDcard = findViewById(R.id.etIDcard);
        etAddress = findViewById(R.id.etAddress);
        etcountry = findViewById(R.id.etcountry);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);


        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(registerOper.this, loginOper.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();
                idCard = etIDcard.getText().toString().trim();
                address= etAddress.getText().toString().trim();
                country = etcountry.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                email = etEmail.getText().toString().trim();

                if (validateInputs()) {
                    registerOperater();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
        private void displayLoader() {
        pDialog = new ProgressDialog(registerOper.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
        private void loadDashboardLogout() {
        Intent i = new Intent(getApplicationContext(), DashboardLogoutOper.class);
        startActivity(i);
        finish();

    }

    private void registerOperater() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_IDCARD, idCard);
            request.put(KEY_ADDRESS, address);
            request.put(KEY_COUNTRY, country);
            request.put(KEY_TEL, phone);
            request.put(KEY_EMAIL, email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginOper(username,fullName);
                                loadDashboardLogout();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etUsername.setError("Username already taken!");
                                etUsername.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
        private boolean validateInputs() {
        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(idCard)) {
            etIDcard.setError("ID Card cannot be empty");
            etIDcard.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(address)) {
            etAddress.setError("Address cannot be empty");
            etAddress.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(country)) {
            etcountry.setError("Country cannot be empty");
            etcountry.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(phone)) {
            etPhone.setError("Phone cannot be empty");
            etPhone.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("E-mail cannot be empty");
            etEmail.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}