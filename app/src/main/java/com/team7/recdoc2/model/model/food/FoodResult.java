package com.team7.recdoc2.model.model.food;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodResult {

    @SerializedName("foods")
    @Expose
    private ArrayList<Food> foods = null;

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

}
