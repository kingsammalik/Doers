package com.example.admin.doers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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



/**
 * Created by Ashwani on 7/2/2016.
 */
public class Videos_Async extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    Context context;
    String response = " ";

    public Videos_Async(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Team Doers");
        progressDialog.setMessage("Loading wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String inputdata;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL("http://teamdoers.in/admin/webservices/video.php ");
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

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
          //  JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonarray = new JSONArray(response);
            for (int i = 0; i <jsonarray.length(); i++) {
                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                String id = jsonObject1.getString("id");
                String thumbnail = jsonObject1.getString("thumbnail");

                String videolocation = jsonObject1.getString("videolocation");
                 Log.e("------>",jsonarray.toString());

                GetSet_Videos getSet_videos = new GetSet_Videos();
                getSet_videos.setId(id);
                getSet_videos.setThumbnail(thumbnail);
                getSet_videos.setVideolocation(jsonObject1.getString("videolocation"));

                Videos_fragment.movies.add(getSet_videos);


                //for youtube work

                Constants.videos.add(getSet_videos);

            }
            Log.e("size of list== ", String.valueOf(Videos_fragment.movies.size()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //for pagination work
    Videos_fragment.video_adapter = new video_adapter(context, Videos_fragment.movies);
    Videos_fragment.gridView.setAdapter(Videos_fragment.video_adapter);
    progressDialog.dismiss();

    }
}
