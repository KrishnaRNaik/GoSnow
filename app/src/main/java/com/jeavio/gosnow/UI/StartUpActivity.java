package com.jeavio.gosnow.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jeavio.gosnow.R;
import com.jeavio.gosnow.Utility.CommonUtility;

public class StartUpActivity extends AppCompatActivity {

    Handler handel=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar from the activity
        getSupportActionBar().hide();

        //redirect the user to the login activity or splash activity
        handel.postDelayed(new Runnable() {

            @Override
            public void run() {
                boolean isFirstTime = CommonUtility.IsFirstTime(StartUpActivity.this);
               // If the user has opened the application for the first time (login is not
                // completed) redirect user to splash screen else redirect user to login
                // screen
                if(isFirstTime == true)
                {
                    startActivity(new Intent(StartUpActivity.this, SplashActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 5);

    }

}
