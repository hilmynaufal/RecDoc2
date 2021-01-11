package com.team7.recdoc2.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.team7.recdoc2.R;
import com.team7.recdoc2.network.FirebaseClient;

public class CalculateFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        SharedPreferences localuser = view.getContext().getSharedPreferences("UserLocal", Context.MODE_PRIVATE);

        //firebase
        final FirebaseClient client = FirebaseClient.getInstance();

        final EditText edtTinggi = view.findViewById(R.id.edtTinggi);
        final EditText edtBerat = view.findViewById(R.id.edtBerat);
        final EditText edtUsia = view.findViewById(R.id.edtUsia);
        final RadioGroup btnGender = view.findViewById(R.id.btnGender);
        final RadioButton btnPria = view.findViewById(R.id.btnPria);
        final RadioButton btnWanita = view.findViewById(R.id.btnWanita);
        final TextView txtCalori = view.findViewById(R.id.txtCalori);

        Button btnHitung = view.findViewById(R.id.btnHitung);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtTinggi.getText().toString().equals("")) {
                    edtTinggi.setError("Mohon isi Tinggi Badan");
                } else if (edtBerat.getText().toString().equals("")) {
                    edtBerat.setError("Mohon isi Berat Badan");
                } else if (edtUsia.getText().toString().equals("")) {
                    edtUsia.setError("Mohon isi Usia anda");
                } else if (!((btnPria.isChecked()) || (btnWanita.isChecked()))) {
                    Snackbar.make(view, "Mohon Pilih Jenis Kelamin", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.d("valid", edtTinggi.getText().toString());
                    int tinggi = Integer.parseInt(edtTinggi.getText().toString());
                    int berat = Integer.parseInt(edtBerat.getText().toString());
                    int usia = Integer.parseInt(edtUsia.getText().toString());
                    int id = btnGender.getCheckedRadioButtonId();
                    String gender;
                    if (id == R.id.btnPria) gender = "Pria";
                    else gender = "Wanita";
                    double totalKalori = hitungKalori(tinggi, berat, usia, id);
                    txtCalori.setText("Total Kalori yang dibutuhkan per hari adalah: " + totalKalori);
                }
            }
        });
        return view;
    }

    double hitungKalori(int tinggi, int berat, int usia, int id) {
        double totalKalori;
        if (id == R.id.btnPria) {
            totalKalori = 13.397 * berat + 4.779 * tinggi - 5.677 * usia + 88.362;
        } else {
            totalKalori = 9.247 * berat + 3.098 * tinggi - 4.330 * usia + 447.593;
        }

        return Math.round(totalKalori);
    }
}
