package com.ekbana.weather.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.model.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 2/19/15.
 */
public class CustomHandleAdapter extends BaseAdapter {
    ArrayList<Tweet> list= new ArrayList<>();
    Context context;
    View view;
    TextView userHandleName,userName;
    ImageView porfilePic;
    AQuery aQuery;
    ImageOptions options;


    public CustomHandleAdapter(Context context, ArrayList<Tweet> list) {
        this.context = context;
        this.list = list;
        aQuery=new AQuery(context);
        options=new ImageOptions();
        options.round=5;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.custom_add_handle_layout, null);

        userHandleName = (TextView) view.findViewById(R.id.userHandleName);
        userName= (TextView) view.findViewById(R.id.userName);
        porfilePic= (ImageView) view.findViewById(R.id.profilePicture);
        userName.setText(list.get(position).userName);
        userHandleName.setText("@"+list.get(position).userHandle);

        Picasso.with(context).load(list.get(position).url).into(porfilePic);
        return view;
    }
}
