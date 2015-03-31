package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 2/4/15.
 */
public class TweetsCustomAdapter extends BaseAdapter {
    Context context;
    int []userImage={R.drawable.sample,R.drawable.sample1};
    String[]username={"Jack","Mike"};
    String[]tweetedat={"Jack","Mike"};
    String[]tweetcontent={"dummy text testing for the ui of android.dummy text testing for the ui of android.dummy text testing for the ui of android."," text testing for the ui of android.dummy text testing for the ui of android. text testing for the ui of android.dummy text testing for the ui of android."};
    String[]tweettime={"21 min ago","1 hour ago"};
    ArrayList<PinnedTweet>tweetList;
    String user;
    AQuery aQuery;
    DatabaseHelper databaseHelper;
    public TweetsCustomAdapter(Context context,String user,ArrayList<PinnedTweet>tweetList)
    {
        this.context=context;
        this.tweetList=tweetList;
        this.user=user;
        aQuery=new AQuery(context);
        databaseHelper=new DatabaseHelper(context);



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

        //aQuery.id(UserImage).height(48).width(48).image(tweetList.get(position).url);
        Picasso.with(context).load(databaseHelper.getImageUrlOfLatestTweet(tweetList.get(0).userHandle)).into(UserImage);
        userName.setText(tweetList.get(position).userName);
        tweetedAt.setText("@"+tweetList.get(position).userHandle);
        tweetContent.setText(Html.fromHtml(tweetList.get(position).text));
        tweetTime.setText(tweetList.get(position).date);

        return view;
    }
}
