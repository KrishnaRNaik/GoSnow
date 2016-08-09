package com.jeavio.gosnow.UI.Adapators;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeavio.gosnow.R;
import com.jeavio.gosnow.UI.SplashActivity;

/**
 * Created by Krishna on 8/5/2016.
 */
public class SplashScreenAdaptor extends  PagerAdapter  {
    Context context;
    int[] splashImageIds;
    LayoutInflater inflater;
    SplashActivity caller;

    public SplashScreenAdaptor(Context context, SplashActivity caller,
                            int[] imageIds) {
        this.context = context;
        this.splashImageIds = imageIds;
        this.caller =  caller;

    }

    @Override
    public int getCount() {
        return splashImageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }


    ///@Override public float getPageWidth(int position) { return(0.9f); }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        // Declare Variables
        TextView optionView = null;
        ImageView splashImage=null;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;

        itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextView in viewpager_item.xml
        optionView = (TextView)itemView.findViewById(R.id.optionid);
        optionView.setText(caller.getResources().getText(R.string.title_skip));

        // For the last splash image set the options as "FINISH"
        if(position == splashImageIds.length -1)
            optionView.setText(caller.getResources().getText(R.string.title_finish));

        // Set the onclick event for the option
        optionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              caller.ShowLoginScreen();
            }
        });

        // Locate the ImageView in viewpager_item.xml
        splashImage = (ImageView) itemView.findViewById(R.id.splashid);
        // Capture position and set to the ImageView
        splashImage.setImageResource(splashImageIds[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
        return	itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((FrameLayout) object);

    }

}
