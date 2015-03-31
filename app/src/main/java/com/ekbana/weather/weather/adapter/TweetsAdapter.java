package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.ekbana.weather.weather.model.Tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 2/3/15.
 */
public class TweetsAdapter extends PagerAdapter implements AdapterView.OnItemClickListener {

    int []userImage={R.drawable.sample,R.drawable.sample1};

    Context context;
    ArrayList<String>userHandle;
    HashMap<Integer,ArrayList<PinnedTweet>>hashMap;
    AQuery aQuery;
    DatabaseHelper databaseHelper;
    int pos;
    public TweetsAdapter(Context context,ArrayList<String>userHandle,HashMap<Integer,ArrayList<PinnedTweet>>hashMap)
    {
        this.context=context;
        this.userHandle=userHandle;
        this.hashMap=hashMap;
        aQuery=new AQuery(context);
        databaseHelper=new DatabaseHelper(context);


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

        View view=inflater.inflate(R.layout.latest_tweet_new_short_layout,null);
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



        Log.d("adapterDate0",""+hashMap.get(0).get(0).date);
        Log.d("adapterDate0",""+hashMap.get(0).get(1).date);
        Log.d("adapterDate1",""+hashMap.get(1).get(0).date);
        Log.d("adapterDate1",""+hashMap.get(1).get(1).date);


        Picasso.with(context).load(databaseHelper.getImageUrlOfLatestTweet(hashMap.get(position).get(0).userHandle)).into(UserImage);
        userName.setText(hashMap.get(position).get(0).userName);
        tweetedAt.setText("@"+hashMap.get(position).get(0).userHandle);
        tweetContent.setText(Html.fromHtml(hashMap.get(position).get(0).text));
        tweetTime.setText(hashMap.get(position).get(0).date);


        Picasso.with(context).load(databaseHelper.getImageUrlOfLatestTweet(hashMap.get(position).get(0).userHandle)).into(userImageOne);
        userNameOne.setText(hashMap.get(position).get(0).userName);
        tweetContentOne.setText(Html.fromHtml(hashMap.get(position).get(1).text));
        tweetTimeOne.setText(hashMap.get(position).get(1).date);
        tweetAtOne.setText("@"+hashMap.get(position).get(1).userHandle);



        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
