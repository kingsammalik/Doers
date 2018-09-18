package com.example.admin.doers.Constants;

import com.example.admin.doers.Model.GetSet_Videos;
import com.example.admin.doers.Model.Latest_videos_model;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ashwani on 8/4/2016.
 */
public interface Constants {

    public static final String UPLOAD_URL = "http://www.teamdoers.in/admin/webservices/upload_profile.php";

    ArrayList<GetSet_Videos> videos = new ArrayList<>();
    /*List<GetSet_RecyclerVideo> recyclervideo=new ArrayList<>();*/

}
