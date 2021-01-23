package com.team7.recdoc2.view;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team7.recdoc2.R;
import com.team7.recdoc2.adapter.FoodDataAdapter;
import com.team7.recdoc2.network.FirebaseClient;
import com.team7.recdoc2.viewmodel.FoodListViewModel;

import java.util.ArrayList;

public class FoodFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FoodListViewModel foodListViewModel;
    private FoodDataAdapter foodDataAdapter;
    private String search;
    private LinearLayout FoodLayout;
    private double total = 0.0;
    private double ttl = 0;

    TextView textView;
    TextView totalcalories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_food, container, false);

        textView = view.findViewById(R.id.edtSearchFood);
        FoodLayout = view.findViewById(R.id.llCalories);
        totalcalories = view.findViewById(R.id.TotalCalories);

        final Button button = view.findViewById(R.id.btnSearchFood);
        final Button btnConsume = view.findViewById(R.id.btnConsume);

        button.setOnClickListener(this);

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    search = textView.getText().toString();
                    button.performClick();
                }
                return false;
            }
        });

//        final FirebaseClient client = FirebaseClient.getInstance();
//        client.setReference("stats");
//
//        btnConsume.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                client.updateCalories(total, 0, search, "");
//                total = 0.0;
//                totalcalories.setText("Your food has been consumed and were added to your profile!");
//                btnConsume.setVisibility(View.GONE);
//            }
//        });

        recyclerView = view.findViewById(R.id.viewFood);
        foodListViewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);

        return view;
    }

    @Override
    public void onClick(View view) {
        search = textView.getText().toString();
        foodListViewModel.getMutableLiveData(search)
                .observe(this, new Observer<ArrayList<FoodListViewModel>>() {
                    @Override
                    public void onChanged(ArrayList<FoodListViewModel> foodListViewModels) {
                        foodDataAdapter = new FoodDataAdapter(foodListViewModels, getContext());
//                        for (int i = 0; i < foodListViewModels.size(); i++) {
//                            ttl += foodListViewModels.get(i).nf_total_calories;
//                            total = ttl;
//                        }
//                        ttl = 0;
//                        String sTotal = "Total Calories = " + total + " kcal";
//                        totalcalories.setText(sTotal);
//                        FoodLayout.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(foodDataAdapter);
                    }
                });
    }
}
