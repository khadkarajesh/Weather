package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 2/3/15.
 */
public class PinnedTweetsAdapter extends PagerAdapter{


    Context context;
    HashMap<Integer,ArrayList<PinnedTweet>> hashMap;
    AQuery aQuery;
    DatabaseHelper databaseHelper;
    String imageUrl;

    public PinnedTweetsAdapter(Context context,HashMap<Integer,ArrayList<PinnedTweet>>hashMap)
    {
        this.context=context;
        this.hashMap=hashMap;
        aQuery=new AQuery(context);
        databaseHelper=new DatabaseHelper(context);



    }
    @Override
    public int getCount() {
        return hashMap.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.latest_tweet_new_short_layout,null);

        Log.d("xxxx",""+hashMap.size());
        Log.d("xcon",""+hashMap.get(1).get(0).userHandle);
        LinearLayout layoutSecond= (LinearLayout) view.findViewById(R.id.secondTweet);
        ImageView UserImage= (ImageView) view.findViewById(R.id.UserImage);
        ImageView userImageOne= (ImageView) view.findViewById(R.id.UserImageOne);
        TextView userName= (TextView) view.findViewById(R.id.userName);
        TextView userNameOne= (TextView) view.findViewById(R.id.userNameOne);
        TextView tweetedAt= (TextView) view.findViewById(R.id.tweetedAt);
        TextView tweetContent= (TextView) view.findViewById(R.id.tweetContent);
        TextView tweetContentOne= (TextView) view.findViewById(R.id.tweetContentOne);
        TextView tweetTime= (TextView) view.findViewById(R.id.tweetTime);
        TextView tweetTimeOne= (TextView) view.findViewById(R.id.tweetTimeOne);
        TextView tweetAtOne= (TextView) view.findViewById(R.id.tweetedAtOne);

        imageUrl=databaseHelper.getImageUrlOfPinnedTweet(hashMap.get(position).get(0).userHandle);
        Picasso.with(context).load(databaseHelper.getImageUrlOfPinnedTweet(hashMap.get(position).get(0).userHandle)).into(UserImage);
        userName.setText(hashMap.get(position).get(0).userName);
        tweetedAt.setText("@"+hashMap.get(position).get(0).userHandle);
        tweetContent.setText(Html.fromHtml(hashMap.get(position).get(0).text));
        tweetTime.setText(hashMap.get(position).get(0).date);



        if(hashMap.get(position).size()>1) {
            Picasso.with(context).load(databaseHelper.getImageUrlOfPinnedTweet(hashMap.get(position).get(0).userHandle)).into(userImageOne);
            userNameOne.setText(hashMap.get(position).get(0).userName);
            tweetContentOne.setText(Html.fromHtml(hashMap.get(position).get(1).text));
            tweetTimeOne.setText(hashMap.get(position).get(1).date);
            tweetAtOne.setText("@"+hashMap.get(position).get(0).userHandle);
            Log.d("pinned text", "" + hashMap.get(position).get(1).text);
        }

        else {
           layoutSecond.setVisibility(View.INVISIBLE);
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
