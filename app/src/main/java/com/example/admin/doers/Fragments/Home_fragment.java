package com.example.admin.doers.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.doers.Activity.HomeActivity;
import com.example.admin.doers.Adapter.Gold_Adapter;
import com.example.admin.doers.Adapter.ManasVideo_Adapter;
import com.example.admin.doers.Adapter.ManasVideo_CustomAdapter;
import com.example.admin.doers.Adapter.PagerEvents;
import com.example.admin.doers.Adapter.PagerUpcoming;
import com.example.admin.doers.Adapter.Silver_Adapter;
import com.example.admin.doers.ItemClickSupport;
import com.example.admin.doers.Model.Events;
import com.example.admin.doers.Model.Gold_model;
import com.example.admin.doers.Model.Upcoming;
import com.example.admin.doers.Model.Latest_videos_model;
import com.example.admin.doers.R;
import com.example.admin.doers.Videos_fragment;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class Home_fragment extends Fragment implements ItemClickSupport.OnItemClickListener {
    int count = 0;
    int page=2;
    Activity act;
    ViewPager viewPager1;
    Toolbar tool;
    TextView manasvideos;
    static RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ManasVideo_Adapter manasVideo_adapter;
    LinearLayout upcomingDotpanel;
    private int dotscount;
    private ImageView[] dots;
    TabLayout tabLayout, eventtablayout;
    List<Upcoming> upcomings;
    List<Events> events;
    List<Latest_videos_model> latestVideosModels;
    PagerUpcoming pagerUpcoming;
    PagerEvents pagerEvents;
    ProgressDialog progressDialog;
    public static List<Latest_videos_model> videos = new ArrayList();
    public static final String upcomingUrl = "http://teamdoers.in/admin/webservices/upcoming_events.php";
    public static final String eventUrl = "http://teamdoers.in/admin/webservices/events.php";
    public static final String manasVideoUrl="http://teamdoers.in/admin/webservices/video.php";
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    int NUM_PAGES = 1;
    RelativeLayout dummyView;
    private AutoScrollViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        if(!isConnected(getContext()))buildDilaog(getContext()).show();

        else {



        }


        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabupcoming);
        eventtablayout = view.findViewById(R.id.tabevents);
        tabLayout.setupWithViewPager(viewPager);
        eventtablayout.setupWithViewPager(viewPager1);
        viewPager = view.findViewById(R.id.pager);



        viewPager1 = view.findViewById(R.id.pager1);


        recyclerView = view.findViewById(R.id.manasrecycler);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(new ManasVideo_Adapter(true));

       // new ManasVideo_Async(getActivity()).execute();
        upcomings = new ArrayList<>();
        events = new ArrayList<>();
        latestVideosModels=new ArrayList<>();
        UpcomingSlider();
        EventSlider();
        ManasVideos();

        manasvideos = view.findViewById(R.id.more);
        manasvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Videos_fragment.movies.clear();
                 Videos_fragment moreVideos = new Videos_fragment();
                 FragmentManager fragmentManager = getFragmentManager();
                 fragmentManager.beginTransaction().replace(R.id.container, moreVideos).addToBackStack("tag").commit();

            }
        });


        viewPager.setStopScrollWhenTouch(true);
        viewPager.startAutoScroll((int) PERIOD_MS);

        return view;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        act = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this);
    }

    void UpcomingSlider() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, upcomingUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("url", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    Upcoming sliderUtils = new Upcoming();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(jsonObject.getString("imagelocation"));

                        Log.d("mew", jsonObject.getString("imagelocation"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    upcomings.add(sliderUtils);


                }

                pagerUpcoming = new PagerUpcoming(upcomings, getContext());
                Log.d("tag", String.valueOf(pagerUpcoming));
                NUM_PAGES = (upcomings.size() + 1);
                viewPager.setAdapter(pagerUpcoming);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingelton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }


    void EventSlider() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, eventUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("url", String.valueOf(response));

                for (int i = 0; i < response.length(); i++) {

                    Events eventUtil = new Events();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        eventUtil.setEventsImageUrl(jsonObject.getString("imagelocation"));

                        Log.d("mew", jsonObject.getString("imagelocation"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    events.add(eventUtil);

                }

                pagerEvents = new PagerEvents(events, getContext());
                Log.d("tag", String.valueOf(pagerEvents));
                // NUM_PAGES=(events.size()+1);
                viewPager1.setAdapter(pagerEvents);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingelton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);


    }


    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

      /*  if (position >= 0) {
            Intent intent=new Intent(getActivity(),YoutubePlayActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("pos",position);
            bundle.putString("type","rvid");
            startActivity(intent);
*/
        }

        void ManasVideos(){
            StringRequest stringRequest=new StringRequest( Request.Method.GET,manasVideoUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.setMessage("Getting Videos....");
                    Log.d("video",response);
                    GsonBuilder builder=new GsonBuilder();
                    Gson gson=builder.create();
                    Latest_videos_model[]  users=gson.fromJson(response,Latest_videos_model[].class);
                    recyclerView.setAdapter(new ManasVideo_CustomAdapter(getContext(),users));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            });

            MySingelton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }



    void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    void  hideDialog(){

        if (progressDialog.isShowing())
            progressDialog.dismiss();

    }

    public boolean isConnected(Context context) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

            android.net.NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(mobile!=null && mobile.isConnectedOrConnecting()|| (wifi!=null && wifi.isConnectedOrConnecting()))

                return true;
            else return false;
        }
        return false;
    }

    public AlertDialog.Builder buildDilaog(Context context)

    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder .setTitle("Internet Problem!")
                .setMessage("Your Internet Connection has been Lost..Press ok to exit")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getActivity().finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();
            }
        }).show();


        return builder;

    }



}

