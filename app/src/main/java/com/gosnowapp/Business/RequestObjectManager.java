package com.gosnowapp.Business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.gosnowapp.Globals;
import com.gosnowapp.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Krishna on 8/29/2016.
 */
public class RequestObjectManager {
    private static RequestObjectManager   _reqObjManager;

    private RequestObjectManager()
    {

    }

    public static  RequestObjectManager getRequestManager() {
        if (_reqObjManager == null) {
            _reqObjManager = new RequestObjectManager();
        }
        return _reqObjManager;
    }

    public JSONObject PrepareSignupRequest()
    {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        JSONObject signupRequest = new JSONObject();
        try {

            signupRequest.put(BusinessConsts.FACEBOOK_ID, currentuser.getFacebookId());

            if (currentuser.getFirstName() != null) {
                byte[] dataFirstName = currentuser.getFirstName().getBytes("UTF-8");
                String encodedFirstName = Base64.encodeToString(dataFirstName, Base64.DEFAULT);
                signupRequest.put(BusinessConsts.FIRST_NAME, encodedFirstName);
            }

            if (currentuser.getLastName() != null) {
                byte[] dataLastName = currentuser.getLastName().getBytes("UTF-8");
                String encodedLastName = Base64.encodeToString(dataLastName, Base64.DEFAULT);
                signupRequest.put(BusinessConsts.LAST_NAME, encodedLastName);
            }
            signupRequest.put(BusinessConsts.GENDER, currentuser.getGender());
            signupRequest.put(BusinessConsts.AGE, currentuser.getAge());
        }catch (JSONException e)
        {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return signupRequest;
    }

    public JSONObject PrepareAddPhotoRequest()
    {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        JSONObject addPhotoRequest = new JSONObject();
        try {

            URL profileImageUrl = null;
            Bitmap bitmap = null;
            try {
                profileImageUrl = new URL("https://graph.facebook.com/" + currentuser.getFacebookId() + "/picture?width=400&height=400");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                bitmap  = BitmapFactory.decodeStream(profileImageUrl.openConnection().getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }


            if(bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                addPhotoRequest.put(BusinessConsts.ADDPHOTO_IMAGE, encodedImage);
            }


            addPhotoRequest.put(BusinessConsts.ADDPHOTO_IMAGEURL,"");
            addPhotoRequest.put(BusinessConsts.ADDPHOTO_ISPROFILEIMAGE,1);
            addPhotoRequest.put(BusinessConsts.SESSION_ID, currentuser.getSessionId());

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return addPhotoRequest;
    }


    public JSONObject PrepareUpdateProfileRequest()
    {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        JSONObject updateProfileRequest = new JSONObject();
        try {

            updateProfileRequest.put(BusinessConsts.SESSION_ID, currentuser.getSessionId());

            if (currentuser.getFirstName() != null) {
                byte[] dataFirstName = currentuser.getFirstName().getBytes("UTF-8");
                String encodedFirstName = Base64.encodeToString(dataFirstName, Base64.DEFAULT);
                updateProfileRequest.put(BusinessConsts.FIRST_NAME, encodedFirstName);
            }

            if (currentuser.getLastName() != null) {
                byte[] dataLastName = currentuser.getLastName().getBytes("UTF-8");
                String encodedLastName = Base64.encodeToString(dataLastName, Base64.DEFAULT);
                updateProfileRequest.put(BusinessConsts.LAST_NAME, encodedLastName);
            }
            updateProfileRequest.put(BusinessConsts.GENDER, currentuser.getGender());
            updateProfileRequest.put(BusinessConsts.AGE, currentuser.getAge());

            updateProfileRequest.put(BusinessConsts.RIDERTYPE_ID,currentuser.getRiderTypeId());
            updateProfileRequest.put(BusinessConsts.SKILLLEVEL_ID,currentuser.getSkillLevelID());
            updateProfileRequest.put(BusinessConsts.NEXTDESTINATION_ID,currentuser.getNextDestinationId());
            updateProfileRequest.put(BusinessConsts.INTEREST_ID,currentuser.getInterestId());

            updateProfileRequest.put(BusinessConsts.LATITUDE,currentuser.getLatitude());
            updateProfileRequest.put(BusinessConsts.LONGITUDE,currentuser.getLongitude());
            updateProfileRequest.put(BusinessConsts.ABOUT_ME,currentuser.getAboutMe());

        }catch (JSONException e)
        {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return updateProfileRequest;
    }


}
