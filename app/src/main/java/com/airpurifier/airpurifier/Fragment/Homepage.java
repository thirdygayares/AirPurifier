package com.airpurifier.airpurifier.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airpurifier.airpurifier.API.Api;
import com.airpurifier.airpurifier.API.EndPoint;
import com.airpurifier.airpurifier.API.POST.Post;
import com.airpurifier.airpurifier.API.Params;
import com.airpurifier.airpurifier.Bottomsheet.BottomSheetLevel;
import com.airpurifier.airpurifier.Bottomsheet.BottomSheetTh1;
import com.airpurifier.airpurifier.Dialog.SureDialogFragment;
import com.airpurifier.airpurifier.Model.AccountListModel;
import com.airpurifier.airpurifier.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Homepage extends Fragment {

    private View view;

    private TextView txtPowerStatus, txtLevelStatus, txtHumidityStatus;
    private CardView btnPower, cardLevel, cardHumidity;
    private String power, level, humidity;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        initViews();
        fetchData();
        setOnClickListeners();
        return view;
    }

    private void initViews() {
        txtPowerStatus = view.findViewById(R.id.txtPowerStatus);
        btnPower = view.findViewById(R.id.btnPower);
        cardLevel = view.findViewById(R.id.cardLevel);
        txtLevelStatus = view.findViewById(R.id.txtLevelStatus);
        cardHumidity = view.findViewById(R.id.cardHumidity);
        txtHumidityStatus = view.findViewById(R.id.txtHumidityStatus);
        progressBar = view.findViewById(R.id.progressBar);
    }
    private void fetchData() {
        Map<String, String> params = new HashMap<>();

        Api getAirPurifierData = new Api(getContext(), EndPoint.GET_AIR_PURIFIER_DATA, params, progressBar, new Api.ApiListener() {
            @Override
            public void onApiSuccess(JSONObject jsonResponse) {
                handleDataResponse(jsonResponse);
            }

            @Override
            public void onApiError(String errorMessage) {
                showErrorToast(errorMessage);
            }
        }, Api.RequestMethod.GET);

        getAirPurifierData.execute();
    }

    private void handleDataResponse(JSONObject jsonResponse) {
        try {
            boolean error = jsonResponse.getBoolean("error");

            if (!error) {

                    JSONObject userObject = jsonResponse.getJSONObject("purify");

                    power = userObject.getString("power");
                    level = userObject.getString("level");
                    humidity = userObject.getString("humidity");

                    //set Text
                    txtLevelStatus.setText(level);
                    txtHumidityStatus.setText(humidity);
                    txtPowerStatus.setText(power);
            } else {
                String errorMessage = jsonResponse.getString("message");
                showErrorToast(errorMessage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showErrorToast("Error parsing JSON");
        }
    }
    private void setOnClickListeners() {
        btnPower.setOnClickListener(v -> handlePowerButtonClick());
        cardLevel.setOnClickListener(v -> showLevelBottomSheet());
        cardHumidity.setOnClickListener(v -> showHumidityBottomSheet());
    }

    private void showErrorToast(String message) {
        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private void showHumidityBottomSheet() {
            BottomSheetTh1 bottomSheetTh1 = new BottomSheetTh1(txtHumidityStatus, progressBar);
            bottomSheetTh1.show(getParentFragmentManager(), bottomSheetTh1.getTag());
    }

    private void showLevelBottomSheet() {
            BottomSheetLevel bottomSheetLevel = new BottomSheetLevel(txtLevelStatus, progressBar);
            bottomSheetLevel.show(getParentFragmentManager(), bottomSheetLevel.getTag());
    }

    private void handlePowerButtonClick() {
        btnPower.setOnClickListener(view1 -> {


            if(txtPowerStatus.getText().toString().equalsIgnoreCase("off")){
                //Post(Context context, Map<String, String> params, ProgressBar progressBar, String endpoint)
                Map<String, String> params = new HashMap<>();
                params.put("power", "ON");

                Post post = new Post(getContext(), params, progressBar, EndPoint.UPDATE_AIR_PURIFIER_POWER);
                post.sample();

                if(post.status){
                    txtPowerStatus.setText("ON");
                }

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
                        Map<String, String> params = new HashMap<>();
                        params.put("power", "OFF");

                        Post post = new Post(getContext(), params, progressBar, EndPoint.UPDATE_AIR_PURIFIER_POWER);
                        post.sample();

                        if(post.status){
                            txtPowerStatus.setText("OFF");
                        }
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