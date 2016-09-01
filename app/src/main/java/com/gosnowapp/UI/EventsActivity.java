package com.gosnowapp.UI;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gosnowapp.R;

public class EventsActivity extends OptionsMenuBaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles,navMenuIcons);
    }

}
