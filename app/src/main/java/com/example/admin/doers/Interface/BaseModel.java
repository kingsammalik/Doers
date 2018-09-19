package com.example.admin.doers.Interface;

public class BaseModel {

    private static FavListener favListener;

    public static FavListener getFavListener() {
        return favListener;
    }

    public static void setFavListener(FavListener favListener) {
        BaseModel.favListener = favListener;
    }

    public interface FavListener{
        void favClicked();
    }
}
