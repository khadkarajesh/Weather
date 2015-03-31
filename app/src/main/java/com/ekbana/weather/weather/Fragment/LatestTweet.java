package com.ekbana.weather.weather.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.adapter.TweetLongAdapter;
import com.ekbana.weather.weather.adapter.TweetsAdapter;
import com.ekbana.weather.weather.common.Communicator;
import com.ekbana.weather.weather.common.CustomScrollView;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.*;
import com.ekbana.weather.weather.parser.Parser;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestTweet extends android.support.v4.app.Fragment implements CustomScrollView.ScrollViewListener {

    View view;
    AQuery aQuery;
    Parser parser;

    String URL_TWEET="http://uat.ekbana.info/social/api_gettweets.php";
    HashMap<Integer,ArrayList<com.ekbana.weather.weather.model.PinnedTweet>>hashMapTweet;
    ArrayList<String>userHandle;
    DatabaseHelper databaseHelper;

    ViewPager latestTweetViewPager;
    LinePageIndicator latestTweetLinePagerIndicator;

    Communicator communicator;
    CustomScrollView scrollView;


    ListView listView;

    public LatestTweet() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator= (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        databaseHelper = new DatabaseHelper(getActivity());
        view=inflater.inflate(R.layout.fragment_latest_tweet, container, false);

        getTweetData();
        listView= (ListView) view.findViewById(R.id.latestTweetListView);


        return view;
    }
    public void getTweetData()
    {
        parser=new Parser();
        userHandle=databaseHelper.getAllHandle();
        /*String handle="";
        for(int i=0; i<userHandle.size(); i++)
        {
            if(i<=userHandle.size()-2) {
                handle = handle + userHandle.get(i) + ",";
            }
            else
            {
                handle=handle+userHandle.get(i);
            }
        }


        HashMap<String,String> handleHash=new HashMap<>();
        handleHash.put("handle",handle);
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("loading data. please wait....");
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        aQuery=new AQuery(getActivity());
        aQuery.progress(dialog).ajax(URL_TWEET,handleHash, JSONObject.class,new AjaxCallback<JSONObject>()
        {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                if(object!=null)
                {
                    Log.d("handle:>>>", "" + object);
                    try {
                        hashMapTweet=parser.getTweet(object,userHandle);
                        Log.d("hashMapTweet size:",""+hashMapTweet.size());

                        for(int i=0; i<hashMapTweet.size(); i++)
                        {
                            hashMapTweet.get(i);
                            for(int j=0; j<hashMapTweet.get(i).size(); j++)
                            {
                                Log.d("text:",""+hashMapTweet.get(i).get(j).text);
                            }
                        }
                        callTweetAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });*/
        hashMapTweet=databaseHelper.getDataSelectedHandle(userHandle);
        callTweetAdapter();

    }

    public void callTweetAdapter()
    {
        latestTweetViewPager= (ViewPager) view.findViewById(R.id.tweetFragmentViewPager);
        latestTweetLinePagerIndicator= (LinePageIndicator) view.findViewById(R.id.tweetLinePageIndicator);

        latestTweetLinePagerIndicator.setClickable(false);
        latestTweetViewPager.setAdapter(new TweetLongAdapter(getActivity(), userHandle, hashMapTweet));
        latestTweetLinePagerIndicator.setViewPager(latestTweetViewPager);
    }


    @Override
    public void onScrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy) {
        communicator.listenScroll(scrollView, x,y,oldx,oldy);

    }
}
