package com.jeavio.gosnow.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.facebook.login.LoginManager;
import com.jeavio.gosnow.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RideNowActivity extends AppCompatActivity {

    private ImageView profileImage;
    Bitmap bitmap;
    String faceBookID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar from the activity
        getSupportActionBar().hide();

        profileImage= (ImageView) findViewById(R.id.profileImage);

        faceBookID = (String)getIntent().getExtras().get("facebookId");


        // fetching facebook's profile picture
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + faceBookID+ "/picture?type=large");
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
                profileImage.setImageBitmap(bitmap);
            }
        }.execute();


    }

    @Override
    protected   void onStop()
    {
        super.onStop();
        LoginManager.getInstance().logOut();
    }

}
