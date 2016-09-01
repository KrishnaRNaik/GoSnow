package com.gosnowapp.UI.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gosnowapp.R;
import com.gosnowapp.UI.SignupStepsActivity;

import java.util.ArrayList;

/**
 * Created by Krishna on 8/31/2016.
 */
public class DestCountryAdaptor extends BaseAdapter{

    private Context context;
    SignupStepsActivity activity;
    ArrayList<String>countries = new ArrayList<String>();

    public DestCountryAdaptor(Context context,ArrayList<String>countryList) {
        this.context = context;
        activity = (SignupStepsActivity) context;
        countries = countryList;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
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
                ((SignupStepsActivity)activity).onCountrySelected(position);
            }
        });

        txtTitle.setText(countries.get(position));

        return convertView;
    }
}
