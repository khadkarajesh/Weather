package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.model.Tweet;

import java.util.ArrayList;

/**
 * Created by root on 3/12/15.
 */
public class PinnedCustomAdapter extends BaseAdapter {

    Context context;
    int []userImage={R.drawable.sample,R.drawable.sample1};
    String[]username={"Jack","Mike"};
    String[]tweetedat={"Jack","Mike"};
    String[]tweetcontent={"dummy text testing for the ui of android.dummy text testing for the ui of android.dummy text testing for the ui of android."," text testing for the ui of android.dummy text testing for the ui of android. text testing for the ui of android.dummy text testing for the ui of android."};
    String[]tweettime={"21 min ago","1 hour ago"};
    ArrayList<Tweet> tweetList;
    String user;
    AQuery aQuery;
    public PinnedCustomAdapter(Context context,String user,ArrayList<Tweet>tweetList)
    {
        this.context=context;
        this.tweetList=tweetList;
        this.user=user;
        aQuery=new AQuery(context);



    }
    @Override
    public int getCount() {
        return tweetList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.custom_latest_tweet_layout,null);
        ImageView UserImage= (ImageView) view.findViewById(R.id.UserImage);
        TextView userName= (TextView) view.findViewById(R.id.userName);
        TextView tweetedAt= (TextView) view.findViewById(R.id.tweetedAt);
        TextView tweetContent= (TextView) view.findViewById(R.id.tweetContent);
        TextView tweetTime= (TextView) view.findViewById(R.id.tweetTime);

        aQuery.id(UserImage).height(48).width(48).image(tweetList.get(position).url);
        userName.setText(tweetList.get(position).userName);
        tweetedAt.setText("@"+user);
        tweetContent.setText(Html.fromHtml(tweetList.get(position).text));
        tweetTime.setText(tweetList.get(position).date);

        return view;
    }
}
