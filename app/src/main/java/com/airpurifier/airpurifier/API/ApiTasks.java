/*
 * ApiTasks class handles asynchronous API tasks, specifically a login request.
 * It extends AsyncTask to perform network operations on a background thread.
 * The goal is to make the code clean, reusable, and address deprecated methods.
 */

package com.airpurifier.airpurifier.API;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airpurifier.airpurifier.Data.Data;
import com.airpurifier.airpurifier.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// Make this code clean and reusable; address deprecated methods later
public class ApiTasks extends AsyncTask<Void, Void, String> {

    // API URL for login
    private final String apiUrl = "http://192.168.68.102/AirPurifierApi/User/api.php?apicall=login";

    // Parameters for the login request
    private final Map<String, String> params;
    private final Context context;
    private final ProgressBar progressBar;

    // Constructor to initialize context, login parameters, and progress bar
    public ApiTasks(Context context, String userName, String password, ProgressBar progressBar) {
        this.context = context;
        params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        this.progressBar = progressBar;
    }

    // Pre-execution tasks, such as displaying a progress bar
    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    // Background task to perform the login request
    @Override
    protected String doInBackground(Void... voids) {
        try {
            // Set up the HTTP connection
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

    // Handle the API response
    @Override
    protected void onPostExecute(String result) {
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
                    // performUserActions();
                    Data data = new Data();
                    data.isAdmin = false;
                }

                // Start the main activity
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else {
                // Login failed, show error message
                String errorMessage = jsonResponse.getString("message");
                showErrorToast(errorMessage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing JSON");
        }
    }

    // Admin-specific actions
    private void performAdminActions() {
        showToast("Admin logged in!");
    }

    // Regular user actions
    private void performUserActions() {
        showToast("User logged in!");
    }

    // Show an error toast to the user
    private void showErrorToast(String message) {
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    // Show a toast to the user
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
