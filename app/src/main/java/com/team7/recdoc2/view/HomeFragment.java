package com.team7.recdoc2.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.team7.recdoc2.LoginActivity;
import com.team7.recdoc2.R;
import com.team7.recdoc2.network.FirebaseClient;
import com.team7.recdoc2.tools.Delay;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment {

    private ProgressDialog progressDialog;
    private SharedPreferences localuser, localstat;
    private TextView tvConsumed;
    private TextView tvTarget;
    private TextView tvTotal;
    private TextView tvUsername;
    private TextView tvConsumed1, tvConsumed2, tvConsumed3, tvConsumed4, tvConsumed5;
    private DecimalFormat df;
//    private FirebaseClient profileClient;
    private FirebaseClient statsClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_profile, container, false);

        progressDialog = ProgressDialog.show(getContext(), "Updating Profile", "Please wait...", true, true);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        tvUsername = view.findViewById(R.id.tvUsername);


        tvConsumed = view.findViewById(R.id.tvConsumed);
        tvTarget = view.findViewById(R.id.tvTarget);
        tvTotal = view.findViewById(R.id.tvTotal);

        tvConsumed1 = view.findViewById(R.id.tvConsumed1);
        tvConsumed2 = view.findViewById(R.id.tvConsumed2);
        tvConsumed3 = view.findViewById(R.id.tvConsumed3);
        tvConsumed4 = view.findViewById(R.id.tvConsumed4);
        tvConsumed5 = view.findViewById(R.id.tvConsumed5);


//        profileClient = FirebaseClient.getInstance();
//        profileClient.setReference("profile");
//
        statsClient = FirebaseClient.getInstance();
        statsClient.setReference("stats");

        df = new DecimalFormat("#.##");

//        Button btnReset = view.findViewById(R.id.btnReset);
//
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog = ProgressDialog.show(getContext(), "Updating Profile", "Please wait...", true, true);
//                loadProfile();
//            }
//        });

        localuser = this.getActivity().getSharedPreferences("UserLocal", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Logging out");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                //delete user local
                SharedPreferences.Editor editor = localuser.edit();
                editor.clear();
                editor.apply();
                //end

                //delete local stat
                SharedPreferences.Editor editor2 = localstat.edit();
                editor2.clear();
                editor2.apply();
                //end

                progressDialog.dismiss();

                startToLoginAvtivity();
            }
        });

        Delay.delay(3, new Delay.DelayCallback() {
            @Override
            public void afterDelay() {
                loadProfile();
            }
        });


        return view;
    }

    void startToLoginAvtivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    void loadProfile() {
        localstat = this.getActivity().getSharedPreferences("LocalStat", Context.MODE_PRIVATE);

        double goal = statsClient.getTarget();
        double consumed = statsClient.getConsumed();
        double leftToGoal = goal-consumed;
//        Set<String> stringSet = localstat.getStringSet("last_food_consumed", new HashSet<String>());
        List<String> last_food_consumed = new ArrayList<>(statsClient.getLastConsumed());

        tvConsumed.setText(df.format(consumed));
        tvTarget.setText(df.format(goal));
        tvTotal.setText(df.format(leftToGoal));

        tvConsumed1.setText(last_food_consumed.get(0));
        tvConsumed2.setText(last_food_consumed.get(1));
        tvConsumed3.setText(last_food_consumed.get(2));
        tvConsumed4.setText(last_food_consumed.get(3));
        tvConsumed5.setText(last_food_consumed.get(4));
    }


}
