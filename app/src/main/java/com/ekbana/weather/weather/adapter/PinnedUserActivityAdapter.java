package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.PinnedTweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 3/13/15.
 */
public class PinnedUserActivityAdapter extends BaseAdapter  {
    Context context;
    View view;
    ToggleButton toggleButton;
    ArrayList<PinnedTweet>list;
    TextView userName,handleName;

    ImageView porfilePic;
    AQuery aQuery;
    ImageOptions options;

    DatabaseHelper databaseHelper;


    public PinnedUserActivityAdapter(Context context,ArrayList<PinnedTweet>list)
    {
        this.context=context;
        this.list=list;
        databaseHelper=new DatabaseHelper(context);

    }
    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.pinned_user_custom_activity,null);
        userName= (TextView) view.findViewById(R.id.pinnedUserName);
        handleName= (TextView) view.findViewById(R.id.pinnedHandleName);
        toggleButton= (ToggleButton) view.findViewById(R.id.pinnedToggleButton);
        porfilePic= (ImageView) view.findViewById(R.id.profilePicAaaa);

        Log.d("url:pinned",""+list.get(position).imageUrl);
        //aQuery.id(porfilePic).height(48).width(48).image(list.get(position).imageUrl, options);
        Picasso.with(context).load(list.get(position).imageUrl).into(porfilePic);
        userName.setText(list.get(position).userName);
        handleName.setText("@" + list.get(position).userHandle);
        if(list.get(position).status==1)
        {
            toggleButton.setChecked(true);
        }
        else
        {
            toggleButton.setChecked(false);
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    databaseHelper.updatePinnedStatus(position + 1,1);

                }
                else
                {
                    databaseHelper.updatePinnedStatus(position + 1,0);
                }
            }
        });
        return view;
    }

}
