package com.airpurifier.airpurifier.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airpurifier.airpurifier.Adapter.AccountListAdapter;
import com.airpurifier.airpurifier.Adapter.MyInterface;
import com.airpurifier.airpurifier.Model.AccountListModel;
import com.airpurifier.airpurifier.R;
import com.airpurifier.airpurifier.admin.AccountListSinglePage;
import com.airpurifier.airpurifier.admin.AddAccount;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Accountlist extends Fragment implements MyInterface {

    View view;

    RecyclerView recyclerView;

    ImageView btnAdd;

    //initiate model and adapter
    ArrayList<AccountListModel> accountListModels = new ArrayList<>();
    AccountListAdapter accountListAdapter ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_account_list, container, false);

        //initiate xml files
        initXml();

        //call adapter
        accountListAdapter = new AccountListAdapter(getContext(), accountListModels, this);

        //populate data
        accountData();

        //button add
        btnAdd();

        return view;
    }

    private void btnAdd() {
        btnAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddAccount.class);
            startActivity(intent);
        });
    }

    private void accountData() {
        accountListModels.add(new AccountListModel("Jopay", true, true));
        accountListModels.add(new AccountListModel("Bobby", true, false));
        accountListModels.add(new AccountListModel("Jeff", false, false));
        accountListModels.add(new AccountListModel("Elton", true, true));
        accountListModels.add(new AccountListModel("Rey", false, false));

        recyclerView.setAdapter(accountListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        accountListAdapter.notifyItemInserted(accountListModels.size());
    }

    private void initXml() {
        recyclerView = view.findViewById(R.id.recyclerView);
        btnAdd = view.findViewById(R.id.btnAdd);
    }

    @Override
    public void onItemClick(int pos, String categories) {
        Intent intent = new Intent(getContext(), AccountListSinglePage.class);
        startActivity(intent);
    }
}