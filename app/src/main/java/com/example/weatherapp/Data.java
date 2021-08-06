package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
