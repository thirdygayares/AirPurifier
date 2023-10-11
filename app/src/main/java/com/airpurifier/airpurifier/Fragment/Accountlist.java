package com.airpurifier.airpurifier.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airpurifier.airpurifier.API.Api;
import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.Adapter.AccountListAdapter;
import com.airpurifier.airpurifier.Adapter.MyInterface;
import com.airpurifier.airpurifier.Model.AccountListModel;
import com.airpurifier.airpurifier.R;
import com.airpurifier.airpurifier.admin.AccountListSinglePage;
import com.airpurifier.airpurifier.admin.AddAccount;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Accountlist extends Fragment implements MyInterface {

    private View view;
    private RecyclerView recyclerView;
    private ImageView btnAdd;
    private ProgressBar progressBar;

    private ArrayList<AccountListModel> accountListModels = new ArrayList<>();
    private AccountListAdapter accountListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_account_list, container, false);

        initXml();

        accountListAdapter = new AccountListAdapter(getContext(), accountListModels, this);
        initRecyclerView();

        fetchAccountData();
        setAddButtonListener();

        return view;
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(accountListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAddButtonListener() {
        btnAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddAccount.class);
            startActivity(intent);
        });
    }

    private void fetchAccountData() {
        Map<String, String> params = new HashMap<>();

        Api getAccountDataTask = new Api(getContext(), EndPoint.GET_ALL_USERS, params, progressBar, new Api.ApiListener() {
            @Override
            public void onApiSuccess(JSONObject jsonResponse) {
                handleAccountDataResponse(jsonResponse);
            }

            @Override
            public void onApiError(String errorMessage) {
                showErrorToast(errorMessage);
            }
        }, Api.RequestMethod.GET);

        getAccountDataTask.execute();
    }

    private void handleAccountDataResponse(JSONObject jsonResponse) {
        try {
            boolean error = jsonResponse.getBoolean("error");

            if (!error) {
                JSONArray usersArray = jsonResponse.getJSONArray("users");

                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObject = usersArray.getJSONObject(i);

                    String username = userObject.getString("userName");
                    Integer isAdmin = userObject.getInt("isAdmin");
                    Integer isActive = userObject.getInt("isOnline");

                    accountListModels.add(new AccountListModel(username, Boolean.parseBoolean(isAdmin.toString()), Boolean.parseBoolean(isActive.toString())));
                }

                accountListAdapter.notifyDataSetChanged();

            } else {
                String errorMessage = jsonResponse.getString("message");
                showErrorToast(errorMessage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing JSON");
        }
    }

    private void showErrorToast(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private void initXml() {
        recyclerView = view.findViewById(R.id.recyclerView);
        btnAdd = view.findViewById(R.id.btnAdd);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onItemClick(int pos, String categories) {
        Intent intent = new Intent(getContext(), AccountListSinglePage.class);
        startActivity(intent);
    }
}
