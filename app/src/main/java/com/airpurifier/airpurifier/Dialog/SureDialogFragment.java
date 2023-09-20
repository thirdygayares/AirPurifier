package com.airpurifier.airpurifier.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.airpurifier.airpurifier.R;
import com.google.android.material.button.MaterialButton;

public class SureDialogFragment extends DialogFragment {

    TextView txtView;

    public SureDialogFragment(TextView txtView) {
        this.txtView = txtView;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sure, container, false);




        MaterialButton btnYes = view.findViewById(R.id.btnYes);
        MaterialButton btnNo = view.findViewById(R.id.btnNo);

        btnYes.setOnClickListener( view1 -> {
            String status = "ON";

            if(status.equalsIgnoreCase("ON")){
                txtView.setText("Off");
            }else {
                txtView.setText("On");
            }
            dismiss();
        });


        btnNo.setOnClickListener( view1 -> {
            dismiss();
        });

        return view;
    }
}
