package com.airpurifier.airpurifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airpurifier.airpurifier.API.Api;
import com.airpurifier.airpurifier.API.ApiTasks;
import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.Data.Data;
import com.airpurifier.airpurifier.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login extends AppCompatActivity {

    private MaterialButton btn_login;
    private EditText emailEditText, passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    showToast("All fields are required");
                } else {
                    // Create parameters map
                    Map<String, String> params = new HashMap<>();
                    params.put("userName", username);
                    params.put("password", password);

                    // Instantiate Api for login
                    Api loginTask = new Api(getApplicationContext(), EndPoint.LOGIN_URL, params, progressBar, new Api.ApiListener() {
                        @Override
                        public void onApiSuccess(JSONObject jsonResponse) {
                            handleLoginResponse(jsonResponse);
                        }

                        @Override
                        public void onApiError(String errorMessage) {
                            showErrorToast(errorMessage);
                        }
                    }, Api.RequestMethod.POST);

                    // Execute the task
                    loginTask.execute();
                }
            }
        });
    }

    private void handleLoginResponse(JSONObject jsonResponse) {
        try {
            String successMessage = jsonResponse.getString("message");
            showToast(successMessage);

            if (!jsonResponse.getBoolean("error")) {
                // Login successful
                JSONObject userObject = jsonResponse.getJSONObject("user");
                boolean isAdmin = userObject.getInt("isAdmin") == 1;

                if (isAdmin) {
                    // User is an admin, perform admin-specific actions
                    // performAdminActions();
                } else {
                    // User is not an admin, perform regular user actions
                    // performUserActions();
                    Data data = new Data();
                    data.isAdmin = false;
                }

                // Start the main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing success message");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
