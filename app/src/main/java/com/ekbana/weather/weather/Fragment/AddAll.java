package com.ekbana.weather.weather.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import com.ekbana.weather.weather.Activity.AddHandle;
import com.ekbana.weather.weather.Activity.PinnedUser;
import com.ekbana.weather.weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAll extends android.support.v4.app.Fragment implements View.OnClickListener {
    LinearLayout weather,tweet,pinnedTweet;

    public AddAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_all, container, false);
        weather= (LinearLayout) view.findViewById(R.id.addWeatherDialog);
        tweet= (LinearLayout) view.findViewById(R.id.addTweetsDialog);
        pinnedTweet= (LinearLayout) view.findViewById(R.id.addPinnedTweetsDialog);
        weather.setOnClickListener(this);
        tweet.setOnClickListener(this);
        pinnedTweet.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addWeatherDialog:
                new WeatherDialog().show(getActivity().getFragmentManager(),"weather");
                break;
            case R.id.addTweetsDialog:
                startActivity(new Intent(getActivity(), AddHandle.class));
                break;
            case R.id.addPinnedTweetsDialog:
                //new PinnedDialog().show(getActivity().getFragmentManager(),"pinned");
                startActivity(new Intent(getActivity(), PinnedUser.class));
                break;
        }
    }
    public interface OnBackClickInFragment
    {
        public void  callOnBackPressActivity();
    }

}
