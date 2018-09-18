package com.example.admin.doers.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.admin.doers.Activity.YoutubePlayActivity;
import com.example.admin.doers.Constants.Constants;
import com.example.admin.doers.Model.GetSet_Videos;
import com.example.admin.doers.R;
import com.example.admin.doers.Videos_Async;
import com.example.admin.doers.Videos_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MoreVideos extends Fragment {

    public static GridView gridView;
    ProgressBar progressBar;
    public static com.example.admin.doers.video_adapter video_adapter;
    String response;
    Boolean fetchCheck = true;
    int pageCount=0;
    public  static List<GetSet_Videos> movies=new ArrayList<>();



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_more_videos, container, false);

        gridView= (GridView) view.findViewById(R.id.grd);
        progressBar = (ProgressBar) view.findViewById(R.id.video_fragment_progress_bar);
        Videos_fragment.movies.clear();
        new Videos_Async(getActivity()).execute();
        Videos_fragment.movies.clear();

        Log.e("----status-----", String.valueOf(movies.size()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), YoutubePlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos",position);
                bundle.putString("type","video");
//                bundle.putSerializable("vid",  (ArrayList<GetSet_Videos>)movies);
                Log.e("-----------hjk-------", String.valueOf(movies.size()));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    }



