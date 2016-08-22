package com.jeavio.gosnow.UI;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jeavio.gosnow.R;

public class ConnectActivity extends OptionsMenuBaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles,navMenuIcons);

    }

}