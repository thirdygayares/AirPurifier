package com.airpurifier.airpurifier.API.POST;

import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airpurifier.airpurifier.API.Api;
import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class Post {

    Context context;
    private final Map<String, String> params;
    ProgressBar progressBar;

    String endpoint;

    public static boolean status = false;

    public Post(Context context, Map<String, String> params, ProgressBar progressBar, String endpoint) {
        this.context = context;
        this.params = params;
        this.progressBar = progressBar;
        this.endpoint = endpoint;
    }


    public void sample(){
        Api addAccountTask = new Api(context, endpoint, params, progressBar, new Api.ApiListener() {
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

    private void handleAddAccount(JSONObject jsonResponse) {
        try {
            String successMessage = jsonResponse.getString("message");
            showToast(successMessage);

            if (!jsonResponse.getBoolean("error")) {
                status = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing success message");
        }
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorToast(String message) {
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
