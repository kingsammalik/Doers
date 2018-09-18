package com.example.admin.doers.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Latest_videos_model {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("videolocation")
    @Expose
    private String videolocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideolocation() {
        return videolocation;
    }

    public void setVideolocation(String videolocation) {
        this.videolocation = videolocation;
    }

}
