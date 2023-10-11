package com.airpurifier.airpurifier.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airpurifier.airpurifier.Bottomsheet.BottomSheetTh1;
import com.airpurifier.airpurifier.R;
import com.google.android.material.button.MaterialButton;

public class SprayCleaner extends Fragment {

    View view;

    TextView txtPowerStatus, txtAutomaticStatus;

    CardView cardPower, cardAutomatic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_spray_cleaner, container, false);

        cardPower = view.findViewById(R.id.cardPower);
        cardAutomatic = view.findViewById(R.id.cardAutomatic);
        txtPowerStatus = view.findViewById(R.id.txtPowerStatus);
        txtAutomaticStatus = view.findViewById(R.id.txtAutomaticStatus);


        methodAutomatic();

        methodPower();

        return view;
    }

    private void methodAutomatic() {
        cardAutomatic.setOnClickListener(v -> {
            BottomSheetTh1 bottomSheetTh1 = new BottomSheetTh1(txtAutomaticStatus, null);
            bottomSheetTh1.show(getParentFragmentManager(), bottomSheetTh1.getTag());
        });
    }

    private void methodPower() {
        cardPower.setOnClickListener(view1 -> {
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
    }

}