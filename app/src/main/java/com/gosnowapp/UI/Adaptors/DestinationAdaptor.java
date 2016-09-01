package com.gosnowapp.UI.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gosnowapp.Model.Destination;
import com.gosnowapp.R;
import com.gosnowapp.UI.SignupStepsActivity;

import java.util.ArrayList;

/**
 * Created by Krishna on 8/30/2016.
 */
public class DestinationAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<Destination> destinationList;
    SignupStepsActivity activity;

    public DestinationAdaptor(Context context, ArrayList<Destination> destinations) {
        this.context = context;
        this.destinationList = destinations;
        activity = (SignupStepsActivity) context;
    }

    @Override
    public int getCount() {
        return destinationList.size();
    }

    @Override
    public Object getItem(int position) {
        return destinationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.destination_item_layout, null);
        }

       TextView txtTitle = (TextView) convertView.findViewById(R.id.destnameid);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignupStepsActivity)activity).onDestinationSelected(position);
            }
        });

        // imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        String destName = destinationList.get(position).getDestinationName();
        String[] destNames = destName.split(",");
        txtTitle.setText(destNames[0]);

        return convertView;
    }
}