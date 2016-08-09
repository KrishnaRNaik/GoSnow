package com.jeavio.gosnow.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
    String userName,firstname,lastname,gender,birthday,facebookid;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hide the action bar from the activity
        getSupportActionBar().hide();

    }

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager=CallbackManager.Factory.create();

        loginButtonfb= (LoginButton)findViewById(R.id.login_buttonfb);

        loginButtonfb.setReadPermissions("public_profile", "email","user_friends","user_birthday");

        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new TransparentProgressDialog(LoginActivity.this,R.drawable.spinner_blue,"");
                //progressDialog.setMessage("Loading...");
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

           //progressDialog.dismiss();

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

                                //userName =  object.getString("name").toString();
                                firstname = object.getString("first_name").toString();
                                lastname = object.getString("last_name").toString();
                                gender = object.getString("gender").toString();
                                //userName =  object.getString("name").toString();
                                birthday = object.getString("birthday").toString();
                                facebookid = object.getString("id").toString();

                                user = new User();
                                user.setFirstName(object.getString("first_name").toString());
                                user.setLastName(object.getString("last_name").toString());
                                user.setFacebookId(facebookid);
                                user.setGender(gender);
                                user.setEmail(object.getString("email").toString());
                                int age = CommonUtility.GetAge(birthday);
                                user.setAge(String.valueOf(age));


                               /* user = new User();


                                user.facebookID = object.getString("id").toString();
                                user.email = object.getString("email").toString();
                                //user.name = object.getString("name").toString();
                                user.gender = object.getString("gender").toString();
                                String birthday = object.getString("birthday").toString();
                                user.name = birthday;
                                PrefUtils.setCurrentUser(user,MainActivity.this);*/



                            }catch (Exception e){
                                e.printStackTrace();
                            }



                           /* Toast.makeText(LoginActivity.this,"welcome "+user.getFirstName() + user.getLastName(),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginActivity.this,RideNowActivity.class);
                            intent.putExtra("facebookId",user.getFacebookId());
                            startActivity(intent);*/
                            //finish();

                            SigninUser  signuser =  new SigninUser();
                            signuser.execute();

                        }

                    });

            Bundle parameters = new Bundle();
            //parameters.putString("fields", "id,email,gender,birthday,");
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
     * An Asynctask, to download database from Azure mobile service
     */
    private class SigninUser extends AsyncTask<String, Integer, String> {

        String error = null;

     /*   TransparentProgressDialog pd = new TransparentProgressDialog(SalesActivity.this,R.drawable.spinner_blue,
                getString(R.string.title_downloaddb));*/

        public void onPreExecute() {

            // pd.setTitle(R.string.title_wait);
            // pd.setMessage(getString(R.string.title_downloaddb));
            // ((TransparentProgressDialog) pd).show();
        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {

            error = GoSnowLoginManager.getLoginManager().signin(LoginActivity.this, user);
            return error;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String errorMsg) {
            // cancel the progress dialog
            progressDialog.dismiss();
            String title = "error";//getString(R.string.title_error);

            if (errorMsg == null) {
                Intent intent = new Intent(LoginActivity.this, RideNowActivity.class);
                intent.putExtra("facebookId", user.getFacebookId());
                startActivity(intent);
                finish();
            }

            // Show the message
            //UIUtility.showSimpleAlert(title, errorMsg, SalesActivity.this);

            return;

        }
    }
}
