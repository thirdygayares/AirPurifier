package com.airpurifier.airpurifier.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Api extends AsyncTask<Void, Void, String> {

    private final String apiUrl;
    private final Map<String, String> params;
    private final Context context;
    private final ProgressBar progressBar;
    private final ApiListener apiListener;
    private final RequestMethod requestMethod; // Enum indicating request type

    // Enum to represent request methods
    public enum RequestMethod {
        GET,
        POST
    }

    public Api(Context context, String apiUrl, Map<String, String> params, ProgressBar progressBar, ApiListener apiListener, RequestMethod requestMethod) {
        this.context = context;
        this.apiUrl = apiUrl;
        this.params = params;
        this.progressBar = progressBar;
        this.apiListener = apiListener;
        this.requestMethod = requestMethod;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // Adjust the URL based on the request type
            String fullUrl = (requestMethod == RequestMethod.GET) ? buildGetUrl(apiUrl, params) : apiUrl;

            // Set up the HTTP connection
            URL url = new URL(fullUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set request method based on enum
            if (requestMethod == RequestMethod.GET) {
                urlConnection.setRequestMethod("GET");
            } else {
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
            }

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Log.d("API_RESPONSE", response.toString());

            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonResponse = new JSONObject(result);

            if (!jsonResponse.getBoolean("error")) {
                apiListener.onApiSuccess(jsonResponse);
                progressBar.setVisibility(ProgressBar.GONE);
            } else {
                String errorMessage = jsonResponse.getString("message");
                apiListener.onApiError(errorMessage);
                progressBar.setVisibility(ProgressBar.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            apiListener.onApiError("Error parsing JSON");
        }
    }

    private String buildGetUrl(String baseUrl, Map<String, String> params) {
        StringBuilder fullUrl = new StringBuilder(baseUrl);
        if (!params.isEmpty()) {
            fullUrl.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                fullUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            // Remove the trailing "&"
            fullUrl.deleteCharAt(fullUrl.length() - 1);
        }
        return fullUrl.toString();
    }

    public interface ApiListener {
        void onApiSuccess(JSONObject jsonResponse);
        void onApiError(String errorMessage);
    }
}



