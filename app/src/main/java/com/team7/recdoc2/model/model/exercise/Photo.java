package com.team7.recdoc2.model.model.exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("highres")
    @Expose
    private Object highres;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Object getHighres() {
        return highres;
    }

    public void setHighres(Object highres) {
        this.highres = highres;
    }
}
