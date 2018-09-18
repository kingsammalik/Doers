package com.example.admin.doers;

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



/**
 * Created by Ashwani on 5/24/2016.
 */
public class MoreVideos extends Fragment {
   public static GridView gridView;
  ProgressBar progressBar;
    public static video_adapter  video_adapter;
    String response;
    Boolean fetchCheck = true;
    int pageCount=1;
    public  static List<GetSet_Videos> movies=new ArrayList<>();



    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_more_videos, container, false);

        gridView= view.findViewById(R.id.grd);

        progressBar =view.findViewById(R.id.video_fragment_progress_bar);
        new Videos_Async(getActivity()).execute();
      // gridView.setAdapter(new video_adapter(getActivity()));

        gridView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    // End has been reached
                    if (fetchCheck){
                        fetchCheck = false;
                        pageCount++;
                        //new FetchMoreVideoData().execute();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });

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


    class FetchMoreVideoData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar.setVisibility(View.VISIBLE);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            String inputdata;
             response ="";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL("http://teamdoers.in/admin/webservices/video.php?page="+pageCount);
                Log.e("pagehit",""+url);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((inputdata = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputdata);
                }
                // bufferedReader.close();
                response = stringBuilder.toString();
                Log.e("response-->", response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        fetchCheck = true;

                        //  JSONObject jsonObject=new JSONObject(response);
                        JSONArray jsonarray = new JSONArray(response);
                        for (int i = 0; i <jsonarray.length(); i++) {
                            JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                            String id = jsonObject1.getString("id");

                            String thumbnail = jsonObject1.getString("thumbnail");

                            String videolocation = jsonObject1.getString("videolocation");


                            GetSet_Videos getSet_videos = new GetSet_Videos();
                            getSet_videos.setId(id);
                            getSet_videos.setThumbnail(thumbnail);

                            getSet_videos.setVideolocation(jsonObject1.getString("videolocation"));
                            Videos_fragment.movies.add(getSet_videos);

                            Constants.videos.add(getSet_videos);
                        }

                        Log.e("size of list== ", String.valueOf(Videos_fragment.movies.size()));

                        if (video_adapter!=null)
                        video_adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
