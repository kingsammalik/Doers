package com.example.admin.doers.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gallery_model {
    @SerializedName("gallery")
    @Expose
    private String gallery;

    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
