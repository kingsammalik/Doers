package com.example.admin.doers.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Silver_model {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imagelocation")
    @Expose
    private String imagelocation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagelocation() {
        return imagelocation;
    }

    public void setImagelocation(String imagelocation) {
        this.imagelocation = imagelocation;
    }

}
