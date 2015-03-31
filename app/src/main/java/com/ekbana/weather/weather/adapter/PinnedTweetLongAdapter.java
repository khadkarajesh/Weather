package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 3/12/15.
 */
public class PinnedTweetLongAdapter extends PagerAdapter {
    int []userImage={R.drawable.sample,R.drawable.sample1};

    Context context;
    ArrayList<String> userHandle;
    HashMap<Integer,ArrayList<PinnedTweet>> hashMap;
    int pos;
    public PinnedTweetLongAdapter(Context context,ArrayList<String>userHandle,HashMap<Integer,ArrayList<PinnedTweet>>hashMap)
    {
        this.context=context;
        this.userHandle=userHandle;
        this.hashMap=hashMap;

    }
    @Override
    public int getCount() {
        return userHandle.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.latest_tweet_short_layout,null);

        ListView lv= (ListView) view.findViewById(R.id.latestTweetListView);
        Log.d("UserName:at", ":tweetsAdapter:" + userHandle.get(position).toString());
        PinnedTweetsCustomAdapter tweetsCustomAdapter=new PinnedTweetsCustomAdapter(context,userHandle.get(position).toString(),hashMap.get(position));
        lv.setAdapter(tweetsCustomAdapter);
        container.addView(view);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("log","Test changed");

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                Log.d("log","Test");
            }
        });
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
