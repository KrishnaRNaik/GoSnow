package com.gosnowapp.UI;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.gosnowapp.Globals;
import com.gosnowapp.Model.User;
import com.gosnowapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RideNowActivity extends OptionsMenuBaseActivity {

    private ImageView profileImage;
    Bitmap bitmap;
    String faceBookID="";
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        Globals globals = Globals.getInstance();
        final User user = globals.getUser();

        profileImage= (ImageView) findViewById(R.id.profileImage);

        //faceBookID = (String)getIntent().getExtras().get("facebookId");


        // testing commit
        // fetching facebook's profile picture
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + user.getFacebookId() /*"135906693515634"*/+ "/picture?width=400&height=400");
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
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml

        set(navMenuTitles,navMenuIcons);


    }

    @Override
    protected   void onStop()
    {
        super.onStop();
      //  LoginManager.getInstance().logOut();
    }

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
