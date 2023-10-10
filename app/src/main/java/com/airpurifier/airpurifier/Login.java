package com.airpurifier.airpurifier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.airpurifier.airpurifier.API.ApiTasks;
import com.airpurifier.airpurifier.Data.Data;
import com.airpurifier.airpurifier.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Login extends AppCompatActivity {


    MaterialButton btn_login;
    EditText editTextEmail,editTextPassword;

    Boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        btn_login = findViewById(R.id.btn_login);

        Data data = new Data();




            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String username = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();
                    if(username.isEmpty() || password.isEmpty()){
                       Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    }else{
                        ApiTasks apiTask = new ApiTasks(username, password);
                        apiTask.execute();
                    }


                }
            });

    }




}