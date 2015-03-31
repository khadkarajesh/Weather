package com.ekbana.weather.weather.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.PinnedTweetLongAdapter;
import com.ekbana.weather.weather.adapter.PinnedTweetsAdapter;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.Tweet;
import com.ekbana.weather.weather.parser.Parser;
import com.viewpagerindicator.LinePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinnedTweet extends android.support.v4.app.Fragment {

    ViewPager pinnedTweetViewPager;
    LinePageIndicator pinnedTweetLinePageIndicator;


    View view;
    AQuery aQuery;
    Parser parser;

    String URL_PINNED_TWEET="http://uat.ekbana.info/social/api_getpinned_tweet.php";
    HashMap<Integer,ArrayList<com.ekbana.weather.weather.model.PinnedTweet>>hashMapPinnedTweet;
    ArrayList<String>pinnedUserHandle;
    DatabaseHelper databaseHelper;

    public PinnedTweet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseHelper=new DatabaseHelper(getActivity());
        view=inflater.inflate(R.layout.fragment_pinned_tweet, container, false);
        getPinnedData();
        return view;
    }
    public void getPinnedData()
    {
        pinnedUserHandle=databaseHelper.getPinnedHandle();
        /*String handle="";
        for(int i=0; i<pinnedUserHandle.size(); i++)
        {
            if(i<=pinnedUserHandle.size()-2) {
                handle = handle + pinnedUserHandle.get(i) + ",";
            }
            else
            {
                handle=handle+pinnedUserHandle.get(i);
            }
        }
        Log.d("pinned:", "userHandle" + handle);
        HashMap<String,String>handleHash=new HashMap<>();
        handleHash.put("handle", handle);
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("loading data. please wait....");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        aQuery=new AQuery(getActivity());
        aQuery.progress(dialog).ajax(URL_PINNED_TWEET, handleHash, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if (object != null) {
                    Log.d("Pinned handle frag:>>>", "" + object);
                    try {
                        hashMapPinnedTweet = Parser.getPinnedTweet(object, pinnedUserHandle);
                        //Log.d("pinned size:",""+hashMapPinnedTweet.size());

                        callPinnedAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });*/
        hashMapPinnedTweet = databaseHelper.getDataSelectedHandle(pinnedUserHandle);
        callPinnedAdapter();

    }


    public void callPinnedAdapter()
    {
        pinnedTweetViewPager= (ViewPager) view.findViewById(R.id.pinnedTweetViewPager);
        pinnedTweetViewPager.setAdapter(new PinnedTweetLongAdapter(getActivity(),pinnedUserHandle,hashMapPinnedTweet));
    }
}
