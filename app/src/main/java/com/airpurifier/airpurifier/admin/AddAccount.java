package com.airpurifier.airpurifier.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airpurifier.airpurifier.API.Api;
import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.API.Params;
import com.airpurifier.airpurifier.Data.Data;
import com.airpurifier.airpurifier.MainActivity;
import com.airpurifier.airpurifier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddAccount extends AppCompatActivity {

    EditText inputEmail, inputPassword, inputFullName, inputUserName, inputBirthday, inputPosition ;
    Button btnAddEmployee;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        inputEmail = findViewById(R.id.inputEmail);
        inputFullName = findViewById(R.id.inputFullName);
        inputUserName = findViewById(R.id.inputUserName);
        inputBirthday = findViewById(R.id.inputBirthday);
        inputPosition = findViewById(R.id.inputPosition);
        inputPassword = findViewById(R.id.inputPassword);
        progressBar= findViewById(R.id.progressBar);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);



        btnAddEmployee.setOnClickListener(view -> {

            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            String fullName = inputFullName.getText().toString();
            String userName = inputUserName.getText().toString();
            String birthday = inputBirthday.getText().toString();
            String position = inputPosition.getText().toString();


            if(email.isEmpty() || password.isEmpty() || fullName.isEmpty() || userName.isEmpty() || birthday.isEmpty() || position.isEmpty()){
                showToast("All fields are required");
            }else{
                // Create parameters map
                Map<String, String> params = new HashMap<>();
                params.put(Params.email.toString(), email);
                params.put(Params.fullName.toString(), fullName);
                params.put(Params.userName.toString(), userName);
                params.put(Params.birthday.toString(), birthday);
                params.put(Params.position.toString(), position);
                params.put(Params.password.toString(), password);

                // Instantiate Api for login
                Api addAccountTask = new Api(getApplicationContext(), EndPoint.CREATE_USER, params, progressBar, new Api.ApiListener() {
                    @Override
                    public void onApiSuccess(JSONObject jsonResponse) {
                        handleAddAccount(jsonResponse);
                    }

                    @Override
                    public void onApiError(String errorMessage) {
                        showErrorToast(errorMessage);
                    }
                }, Api.RequestMethod.POST);

                // Execute the task
                addAccountTask.execute();
            }

         });
    }

    private void handleAddAccount(JSONObject jsonResponse) {
        try {
            String successMessage = jsonResponse.getString("message");
            showToast(successMessage);

            if (!jsonResponse.getBoolean("error")) {
                // Login successful
                //JSONObject userObject = jsonResponse.getJSONObject("user");

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