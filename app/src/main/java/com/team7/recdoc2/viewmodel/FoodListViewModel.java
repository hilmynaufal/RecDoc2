package com.team7.recdoc2.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.team7.recdoc2.model.model.food.Food;
import com.team7.recdoc2.model.model.food.FoodResult;
import com.team7.recdoc2.network.APIService;
import com.team7.recdoc2.network.ApiClient;
import com.team7.recdoc2.network.FirebaseClient;
import com.team7.recdoc2.network.FoodRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListViewModel extends ViewModel {

    public String food_name = "";
    public String brand_name = "";
    public String serving_qty = "";
    public double nf_calories = 0;
    public double nf_total_calories = 0.0;
    public String thumb = "";
    public MutableLiveData<ArrayList<FoodListViewModel>> mutableLiveData = new MutableLiveData<>();

    private ArrayList<FoodListViewModel> arrayList;
    private ArrayList<Food> foods;

    FirebaseClient client = FirebaseClient.getInstance();

    public String imgUrl() {
        return thumb;
    }

    @BindingAdapter({"imgUrl"})
    public static void loadimage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.overrideOf(250))
                .into(imageView);
    }

    public FoodListViewModel() {

    }

    public FoodListViewModel(Food food) {
        this.food_name = food.getFoodName();
        this.nf_calories = food.getNfCalories();
        this.serving_qty = food.getServingQty().toString();
        this.thumb = food.getPhoto().getThumb();
        this.nf_total_calories = food.getNfCalories();
        client.setReference("stats");
    }

    public MutableLiveData<ArrayList<FoodListViewModel>> getMutableLiveData(String s) {
        arrayList = new ArrayList<>();

        APIService service = ApiClient.getNutritionInstance().getAPIService();
        Call<FoodResult> result = service.getFoodResult(new FoodRequest(s));

        result.enqueue(new Callback<FoodResult>() {
            @Override
            public void onResponse(Call<FoodResult> call, Response<FoodResult> response) {
                try {
                    foods = new ArrayList<>();
                    foods = response.body().getFoods();

                    Log.d("cekcek", "berhasil");

                    for (int i = 0; i < foods.size(); i++) {
                        Food food = foods.get(i);
                        FoodListViewModel foodListViewModel = new FoodListViewModel(food);
                        arrayList.add(foodListViewModel);
                        mutableLiveData.setValue(arrayList);
                        Log.d("cekcek", "value of nf_total in local = " + food.getNfCalories());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FoodResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
        if (mutableLiveData != null) Log.d("cekcek", "tdk error");
        return mutableLiveData;
    }

    public void consumeFood(View view) {
        SharedPreferences localstat = view.getContext().getSharedPreferences("LocalStat", Context.MODE_PRIVATE);

        long consumed = (long) (localstat.getFloat("consumed", 0) + nf_calories);

        //foodtolacaldatabase
//        Set<String> stringSet= new HashSet<>(localstat.getStringSet("last_food_consumed", new HashSet<String>()));
//        List<String> list = new ArrayList<>(stringSet);
//        for (int i = 0; i < list.size(); i++){
//            if (String.valueOf(i+1).equals(list.get(i))){
//                list.add(i, this.food_name);
//                list.remove(String.valueOf(i));
//                Log.d("cek", "i+1: " + i+1 + ", list: " + list.get(i));
//            }
//        }
//        Log.d("cek", "list: " + list.toString());
//        stringSet.addAll(list);
//        Log.d("cek", "Set: " + stringSet.toString());
        //end

//        Log.d("local", String.valueOf(consumed));
        String food = this.food_name.toUpperCase() + " (" + this.nf_calories + " kcal)";
        client.updateConsumedAndTarget(consumed, food, localstat.getFloat("goal", 0));
        Snackbar.make(view, this.food_name.toUpperCase() + " (" + this.nf_calories + " kcal) Consumed!", Snackbar.LENGTH_LONG).show();

        //addToLocal
        SharedPreferences.Editor editor = localstat.edit();
        editor.putFloat("consumed", consumed);
//        editor.putStringSet("last_food_consumed", stringSet);
        editor.apply();
        //end
    }
}
