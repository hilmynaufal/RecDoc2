package com.team7.recdoc2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.team7.recdoc2.R;
import com.team7.recdoc2.adapter.FoodDataAdapter;
import com.team7.recdoc2.network.FirebaseClient;
import com.team7.recdoc2.viewmodel.FoodListViewModel;

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

        Button button = view.findViewById(R.id.btnSearchFood);
        final Button btnConsume = view.findViewById(R.id.btnConsume);


//        final FirebaseClient client = FirebaseClient.getInstance();

        recyclerView = view.findViewById(R.id.viewFood);
        foodListViewModel = ViewModelProviders.of(this).get(FoodListViewModel.class);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
