package com.jeavio.gosnow.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jeavio.gosnow.Business.GoSnowLoginManager;
import com.jeavio.gosnow.Model.User;
import com.jeavio.gosnow.R;
import com.jeavio.gosnow.Utility.CommonUtility;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButtonfb;
    private Button btnLogin;
    private TransparentProgressDialog progressDialog;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());

       setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar from the activity
        getSupportActionBar().hide();

        TextView tncText = (TextView)findViewById(R.id.terms3id);
        tncText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,TNCActivity.class);
                startActivity(intent);
                            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logout from facebook, if logged in
        // This is temporary, Once the flow is final this will be removed
        LoginManager.getInstance().logOut();

        callbackManager=CallbackManager.Factory.create();
        loginButtonfb= (LoginButton)findViewById(R.id.login_buttonfb);

        // Ask for user permission to access user profile, email,birthday
        loginButtonfb.setReadPermissions("public_profile", "email","user_friends","user_birthday");

        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new TransparentProgressDialog(LoginActivity.this,R.drawable.spinner_blue,"");
                progressDialog.show();

                loginButtonfb.performClick();
                loginButtonfb.setPressed(true);
                loginButtonfb.invalidate();
                loginButtonfb.registerCallback(callbackManager, mCallBack);
                loginButtonfb.setPressed(false);
                loginButtonfb.invalidate();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            try {

                               // user = new User();
                                CommonUtility.ParseFbResponse(object);
                               /* if(!object.isNull("first_name"))
                                    user.setFirstName(object.getString("first_name").toString());

                                if(!object.isNull("last_name"))
                                    user.setLastName(object.getString("last_name").toString());

                                if(!object.isNull("gender"))
                                    user.setGender( object.getString("gender").toString());

                                if(!object.isNull("birthday"))
                                {
                                    String birthday = object.getString("birthday").toString();
                                    int age = CommonUtility.GetAge(birthday);
                                    user.setAge(String.valueOf(age));
                                }
                                if(!object.isNull("id"))
                                    user.setFacebookId(object.getString("id").toString());

                                if(!object.isNull("email"))
                                    user.setEmail(object.getString("email").toString());*/

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            // If facebook login is successful, sign up with GoSnow server
                            SigninUser  signuser =  new SigninUser();
                            signuser.execute();

                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields","id,email,first_name,last_name,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();
        }

        @Override
        public void onError(FacebookException e) {
            progressDialog.dismiss();
        }
    };


    /**
     * An Asynctask, to sign up with Go Snow server.
     */
    private class SigninUser extends AsyncTask<String, Integer, String> {

        String error = null;
        public void onPreExecute() {

        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {

            // Perform sign in
            error = GoSnowLoginManager.getLoginManager().signin(LoginActivity.this);
            return error;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String errorMsg) {
            // cancel the progress dialog
            progressDialog.dismiss();
            String title = getString(R.string.title_error);

            // If sign in is successful then show RideNow screen
            if (errorMsg == null) {
               /* Intent intent = new Intent(LoginActivity.this, RideNowActivity.class);
                  startActivity(intent);*/
                LayoutInflater inflater = getLayoutInflater();

                final AlertDialog RiderTypedlg = CommonUtility.ShowSelectRiderTypeDlg(LoginActivity.this);
                // If login is successful, then set isFirstTime flag to false
                CommonUtility.SetIsOpened(LoginActivity.this);
                //finish();
            }
            else {
                // Show the error message
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }

            return;

        }
    }
}
