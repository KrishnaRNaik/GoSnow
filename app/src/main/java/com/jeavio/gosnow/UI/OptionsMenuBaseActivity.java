package com.jeavio.gosnow.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeavio.gosnow.Globals;
import com.jeavio.gosnow.Model.User;
import com.jeavio.gosnow.R;
import com.jeavio.gosnow.UI.Adapators.NavDrawerItem;
import com.jeavio.gosnow.UI.Adapators.NavDrawerListAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

// Base activity for all activity with side option menu
public class OptionsMenuBaseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerLinearLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    protected RelativeLayout _completeLayout, _activityLayout;
    // nav drawer title
    private CharSequence mDrawerTitle;
    private ImageView profileImage;
    Bitmap bitmap;

    // used to store app title
    private CharSequence mTitle;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void set(String[] navMenuTitles,TypedArray navMenuIcons) {
        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_view_drawer);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        profileImage = (ImageView)findViewById(R.id.prImage);
        TextView userName = (TextView)findViewById(R.id.usernameid);
        TextView userTitle = (TextView)findViewById(R.id.usertitleid);
        ImageView logout = (ImageView)findViewById(R.id.logoutid);


        // adding nav drawer items
        if(navMenuIcons==null){
            for(int i=0;i<navMenuTitles.length;i++){
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i]));
            }}else{
            for(int i=0;i<navMenuTitles.length;i++){
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i],navMenuIcons.getResourceId(i, -1)));
            }
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);


       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setIcon(R.drawable.ic_drawer);*/


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for
                // accessibility
                R.string.app_name // nav drawer close - description for
                // accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);

                // calling onPrepareOptionsMenu() to show action bar icons
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().hide();
                // calling onPrepareOptionsMenu() to hide action bar icons
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

        Globals globals = Globals.getInstance();
        final User user = globals.getUser();

        //userName.setText(user.getFirstName() + " " + user.getLastName());
        String title = "";// = user.getRiderType();
        if(title.isEmpty())
            title = "SnowBorder";
        userTitle.setText(title);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + /*user.getFacebookId()*/ "135906693515634"+ "/picture?type=large");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    bitmap  = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //profileImage.setImageBitmap(bitmap);
                SetRoundedImage(bitmap);
            }
        }.execute();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(OptionsMenuBaseActivity.this,LoginActivity.class);
               startActivity(intent);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLinearLayout)) {
                mDrawerLayout.closeDrawer(mDrawerLinearLayout);
            } else {
                mDrawerLayout.openDrawer(mDrawerLinearLayout);
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }




    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                Intent intent = new Intent(this, RideNowActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                Intent intent1 = new Intent(this, ConnectActivity.class);
                startActivity(intent1);
                finish();
                break;
            case 2:
                Intent intent2 = new Intent(this, EventsActivity.class);
                startActivity(intent2);
                finish();
                break;
            /*case 3:
                Intent intent3 = new Intent(this, fourth.class);
                startActivity(intent3);
                finish();
                break;
            case 4:
                Intent intent4 = new Intent(this, fifth.class);
                startActivity(intent4);
                finish();
                break;
            case 5:
                Intent intent5 = new Intent(this, sixth.class);
                startActivity(intent5);
                finish();*/
                //break;
            default:
                break;
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerLinearLayout);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    private void SetRoundedImage(Bitmap original) {
        // TODO Auto-generated method stub
        Bitmap mask = BitmapFactory.decodeResource(getResources(),
                R.drawable.circle_full);
        original = Bitmap.createScaledBitmap(original, mask.getWidth(),
                mask.getHeight(), true);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        profileImage.setImageBitmap(result);
        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        profileImage.setBackgroundResource(R.drawable.circle_outline);

    }

}
