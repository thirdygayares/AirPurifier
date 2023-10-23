package com.airpurifier.airpurifier.Bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.API.METHOD.Post;
import com.airpurifier.airpurifier.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class BottomSheetLevel extends BottomSheetDialogFragment {



    View rootView;

    Button btnLow, btnMedium,btnHigh;


    TextView levelText;
    ProgressBar progressBar;

    public BottomSheetLevel() {
    }

    public BottomSheetLevel(TextView levelText, ProgressBar progressBar) {
        this.levelText = levelText;
        this.progressBar = progressBar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottomsheet_level, container, false);

        initXml();

       //set level when low, medium and high

        btnLow.setOnClickListener(v -> setLevel("Low"));
        btnMedium.setOnClickListener(v -> setLevel("Medium"));
        btnHigh.setOnClickListener(v -> setLevel("High"));
        return rootView;
    }

    private void setLevel(String level) {
        Map<String, String> params = new HashMap<>();
        params.put("level", level);

        Post post = new Post(getContext(), params, progressBar, EndPoint.UPDATE_AIR_PURIFIER_LEVEL);
        post.sample();

        if(post.status){
            levelText.setText(level);

        }
        dismiss();
    }


    private void initXml() {
        btnLow = rootView.findViewById(R.id.btnLow);
        btnMedium = rootView.findViewById(R.id.btnMedium);
        btnHigh = rootView.findViewById(R.id.btnHigh);


    }
}