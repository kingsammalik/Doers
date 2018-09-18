package com.example.admin.doers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.example.admin.doers.Adapter.ManasVideo_Adapter;
import com.example.admin.doers.Constants.Constants;
import com.example.admin.doers.R;
import com.example.admin.doers.video_adapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class YoutubePlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    YouTubePlayerView playerView;
    YouTubePlayer player;
    //   ListView listView;
    GridView gridView;
    private static final String API_KEY = "AIzaSyDNb3-DRGbi6-6TEuymW6k5LQMrc_XgKH8";
    int pos = 0;
    private String type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yotube_play);
        gridView= (GridView) findViewById(R.id.grd1);
        playerView= (YouTubePlayerView) findViewById(R.id.youtube_view);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            pos = bundle.getInt("pos");
            type = bundle.getString("type");

        }

        if(type.equalsIgnoreCase("video")){
            gridView.setAdapter(new video_adapter(getApplicationContext(),Constants.videos));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), YoutubePlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", position);
                    bundle.putString("type","video");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        /*else if(type.equalsIgnoreCase("rvid")){
            gridView.setAdapter(new recylervid_ADApter(getApplicationContext(), Constants.recyclervideo));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),YoutubePlayActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("pos",position);
                    bundle.putString("type","rvid");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }
        */playerView.initialize(API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        player=youTubePlayer;
        if (!b) {

            if(type.equalsIgnoreCase("video")){
                player.cueVideo(Constants.videos.get(pos).getVideolocation());

            }

          /*  else if (type.equalsIgnoreCase("rvid")){
                player.cueVideo(Constants.recyclervideo.get(pos).getVideolocation());
            }
 */       }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


}
