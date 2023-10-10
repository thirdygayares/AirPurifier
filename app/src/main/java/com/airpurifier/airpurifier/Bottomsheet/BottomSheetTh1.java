package com.airpurifier.airpurifier.Bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airpurifier.airpurifier.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetTh1 extends BottomSheetDialogFragment {



    View rootView;

    Button btn10, btn20,btn30,btn40, btn50,btn60 ;
    ImageButton imgSubtract, imgAdd;
    TextView txtNumberAdjusted;

    TextView levelText;

    public BottomSheetTh1() {
    }

    public BottomSheetTh1(TextView levelText) {
        this.levelText = levelText;
    }


    // Define an interface


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_th1, container, false);

        initXml();

       //set level when low, medium and high
        btn10.setOnClickListener(v -> setNumber("10"));
        btn20.setOnClickListener(v -> setNumber("20"));
        btn30.setOnClickListener(v -> setNumber("30"));
        btn40.setOnClickListener(v -> setNumber("40"));
        btn50.setOnClickListener(v -> setNumber("50"));
        btn60.setOnClickListener(v -> setNumber("60"));


        //add minus
        imgAdd.setOnClickListener(v -> addNumber(Integer.parseInt(txtNumberAdjusted.getText().toString())));
        imgSubtract.setOnClickListener(v -> subtractNumber(Integer.parseInt(txtNumberAdjusted.getText().toString())));

        txtNumberAdjusted.setText(levelText.getText());

        return rootView;
    }

    private void setNumber(String level) {
        txtNumberAdjusted.setText(level);
        levelText.setText(txtNumberAdjusted.getText().toString());
        updateLabel();
    }

    private void updateLabel(){
        levelText.setText(txtNumberAdjusted.getText().toString());
    }

    private void addNumber(int level){
        txtNumberAdjusted.setText(String.valueOf(level + 1));
        updateLabel();

    }
    private void subtractNumber(int level){
        txtNumberAdjusted.setText(String.valueOf(level - 1));
        updateLabel();

    }

    private void initXml() {
        btn10 = rootView.findViewById(R.id.btn10);
        btn20 = rootView.findViewById(R.id.btn20);
        btn30 = rootView.findViewById(R.id.btn30);
        btn40 = rootView.findViewById(R.id.btn40);
        btn50 = rootView.findViewById(R.id.btn50);
        btn60 = rootView.findViewById(R.id.btn60);

        imgSubtract = rootView.findViewById(R.id.imgSubtract);
        imgAdd = rootView.findViewById(R.id.imgAdd);

        txtNumberAdjusted = rootView.findViewById(R.id.txtNumberAdjusted);

    }




}