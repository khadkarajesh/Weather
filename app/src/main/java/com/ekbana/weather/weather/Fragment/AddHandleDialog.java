package com.ekbana.weather.weather.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.ekbana.weather.weather.Activity.AddHandle;
import com.ekbana.weather.weather.R;
import com.ekbana.weather.weather.database.DatabaseHelper;
import com.ekbana.weather.weather.model.Tweet;
import com.ekbana.weather.weather.parser.Parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by root on 2/23/15.
 */
public class AddHandleDialog extends DialogFragment{
    EditText userName;
    View view;
    AQuery aQuery;
    HashMap<String,String>hashMap;
    Tweet tweet;
    String URL_TWEET="http://uat.ekbana.info/social/api_gettweets.php";
    DatabaseHelper databaseHelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_fragment_dialog_addhandle,null);
        builder.setView(view);
        builder.setPositiveButton(R.string.addHandle,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName= (EditText) view.findViewById(R.id.textView);
                fetchData(userName.getText().toString());

                /*new DatabaseHelper(getActivity()).addUserHandle(userName.getText().toString());
                Toast.makeText(getActivity(),"successfully added",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(),AddHandle.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

            }
        });
        builder.setTitle("add User");
        Dialog dialog=builder.create();
        return dialog;
    }

    public void fetchData(final String userHandle)
    {
        databaseHelper=new DatabaseHelper(getActivity());
        hashMap=new HashMap<>();
        hashMap.put("handle",userHandle);
        aQuery=new AQuery(getActivity());
        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setIndeterminate(false);
        dialog.setMessage("please wait.adding handle");
        aQuery.progress(dialog).ajax(URL_TWEET,hashMap, JSONObject.class,new AjaxCallback<JSONObject>()
        {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                try {
                    //tweet= Parser.parseSingleHandle(userHandle,object);

                    if(Parser.parseLatestTweetData(userHandle,object).size()>0)
                    {
                        Log.d("<test>",""+Parser.parseLatestTweetData(userHandle,object).size());
                        databaseHelper.insertLatestTweet(Parser.parseLatestTweetData(userHandle,object));
                        databaseHelper.addUserHandleObject(Parser.parseSingleHandle(userHandle, object));
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Invalid Twitter Handle",Toast.LENGTH_LONG).show();
                    }
                    Intent intent=new Intent(getActivity(),AddHandle.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
