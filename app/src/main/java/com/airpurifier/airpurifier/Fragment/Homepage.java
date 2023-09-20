package com.airpurifier.airpurifier.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airpurifier.airpurifier.Dialog.SureDialogFragment;
import com.airpurifier.airpurifier.R;
import com.google.android.material.button.MaterialButton;

public class Homepage extends Fragment {

    View view;

    TextView txtPowerStatus;

    CardView btnPower;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        //initialize xml
        initXml();



        btnPower.setOnClickListener(view1 -> {
            if(txtPowerStatus.getText().toString().equalsIgnoreCase("off")){
                txtPowerStatus.setText("ON");
            }else{
                Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);

                dialog.setContentView(R.layout.dialog_sure);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


                MaterialButton btnYes = dialog.findViewById(R.id.btnYes);
                MaterialButton btnNo = dialog.findViewById(R.id.btnNo);

                btnYes.setOnClickListener( view -> {
                    String status = "ON";

                    if(status.equalsIgnoreCase("ON")){
                        txtPowerStatus.setText("Off");
                    }else {
                        txtPowerStatus.setText("On");
                    }
                    dialog.dismiss();
                });


                btnNo.setOnClickListener( view -> {
                    dialog.dismiss();
                });



                dialog.show();

            }
        });

        return view;
    }

    private void initXml() {
        txtPowerStatus = view.findViewById(R.id.txtPowerStatus);
        btnPower = view.findViewById(R.id.btnPower);
    }
}