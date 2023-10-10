package com.airpurifier.airpurifier.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.airpurifier.airpurifier.Data.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ApiTasks  extends AsyncTask<Void, Void, String> {

    private final String apiUrl = "http://192.168.68.102/AirPurifierApi/User/api.php?apicall=login";
    private final Map<String, String> params;

    public ApiTasks(String userName, String password) {
        params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Build the POST parameters
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(param.getKey()).append('=').append(param.getValue());
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            urlConnection.getOutputStream().write(postDataBytes);

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Log.d("LOGIN", response.toString());

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the API response here
        // 'result' contains the response from the server

        try {
            JSONObject jsonResponse = new JSONObject(result);

            if (!jsonResponse.getBoolean("error")) {
                // Login successful
                JSONObject userObject = jsonResponse.getJSONObject("user");
                boolean isAdmin = userObject.getInt("isAdmin") == 1;

                if (isAdmin) {
                    // User is an admin, perform admin-specific actions
                   // performAdminActions();
                } else {
                    // User is not an admin, perform regular user actions
                    //performUserActions();
                    Data data = new Data();
                    data.isAdmin = false;
                }



            } else {
                // Login failed
                String errorMessage = jsonResponse.getString("message");
                showErrorToast(errorMessage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing JSON");
        }
    }

    private void performAdminActions() {
        // Implement actions for admin users
        // For example, redirect to admin dashboard or show admin-specific UI
        showToast("Admin logged in!");
    }

    private void performUserActions() {
        // Implement actions for regular users
        // For example, redirect to user dashboard or show user-specific UI
        showToast("User logged in!");
    }

    private void showErrorToast(String message) {
        // Show an error toast to the user
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private void showToast(String message) {
        // Show a toast to the user
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
