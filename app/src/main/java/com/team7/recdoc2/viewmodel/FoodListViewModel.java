package com.team7.recdoc2.viewmodel;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.team7.recdoc2.model.model.food.Food;

import java.util.ArrayList;

public class FoodListViewModel extends ViewModel {

    public String food_name = "";
    public String brand_name = "";
    public String serving_qty = "";
    public String nf_calories = "";
    public double nf_total_calories = 0.0;
    public String thumb = "";
    public MutableLiveData<ArrayList<FoodListViewModel>> mutableLiveData = new MutableLiveData<>();

    private ArrayList<FoodListViewModel> arrayList;
    private ArrayList<Food> foods;

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
        this.nf_calories = food.getNfCalories().toString() + " kcal";
        this.serving_qty = food.getServingQty().toString();
        this.thumb = food.getPhoto().getThumb();
        this.nf_total_calories = food.getNfCalories();
    }

}
