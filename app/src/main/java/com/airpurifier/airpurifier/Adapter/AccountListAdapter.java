package com.airpurifier.airpurifier.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.airpurifier.airpurifier.Model.AccountListModel;
import com.airpurifier.airpurifier.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {

    public final MyInterface myInterfaces;


    Context context;
    ArrayList<AccountListModel> accountListModels;

    public AccountListAdapter(Context context, ArrayList<AccountListModel> accountListModels, MyInterface myInterfaces){
        this.context = context;
        this.accountListModels = accountListModels;
        this.myInterfaces = myInterfaces;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_list_account, parent, false);

        return new MyViewHolder(view, myInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (accountListModels.get(holder.getAdapterPosition()).isBlocked()) {
            int iconColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.white);

            Drawable iconDrawable = holder.btnBlocked.getIcon().mutate(); // Make a copy of the icon drawable
            iconDrawable.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
            holder.btnBlocked.setIcon(iconDrawable);
            holder.btnBlocked.setText("Unblock");
            holder.btnBlocked.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
        }else{
            int iconColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.black);

            Drawable iconDrawable = holder.btnBlocked.getIcon().mutate(); // Make a copy of the icon drawable
            iconDrawable.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
            holder.btnBlocked.setIcon(iconDrawable);
            holder.btnBlocked.setText("Block");
            holder.btnBlocked.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.surface)));
        }

        if (accountListModels.get(holder.getAdapterPosition()).isStatus()) {
            holder.txtStatus.setText("Online");
            holder.txtStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
        }else{
            holder.txtStatus.setText("Offline");
            holder.txtStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.gray2)));
        }


        holder.txtUserName.setText(accountListModels.get(position).getUsername());

        holder.btnBlocked.setOnClickListener(view -> {
            boolean isBlocked = accountListModels.get(position).isStatus();

            if (isBlocked) {
                accountListModels.get(position).setBlocked(false);
                setButtonProperties(holder.btnBlocked, R.color.black, "Block", R.color.surface);
            } else {
                accountListModels.get(position).setBlocked(true);
                setButtonProperties(holder.btnBlocked, R.color.white, "Unblock", R.color.red);
            }

            notifyDataSetChanged();
        });



    }


    // Helper method to set button properties
    private void setButtonProperties(MaterialButton button, int iconColorResource, String buttonText, int backgroundTintResource) {
        int iconColor = ContextCompat.getColor(button.getContext(), iconColorResource);

        Drawable iconDrawable = button.getIcon().mutate();
        iconDrawable.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
        button.setIcon(iconDrawable);

        button.setText(buttonText);
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(button.getContext(), backgroundTintResource)));
    }


    @Override
    public int getItemCount() {

        return accountListModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtUserName, txtStatus;
        MaterialButton btnBlocked;

        public MyViewHolder(@NonNull View itemView, MyInterface myInterfaces) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnBlocked = itemView.findViewById(R.id.btnBlocked);


            itemView.setOnClickListener(view -> {
                if(myInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        myInterfaces.onItemClick(pos, "expert");
                    }

                }
            });
        }
    }


}