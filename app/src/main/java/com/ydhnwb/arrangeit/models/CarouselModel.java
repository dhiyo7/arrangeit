package com.ydhnwb.arrangeit.models;

import android.content.Context;

public class CarouselModel{
    private String title;
    private int target;

    public CarouselModel(String title, int target) {
        this.title = title;
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
