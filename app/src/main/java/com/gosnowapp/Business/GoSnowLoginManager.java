package com.gosnowapp.Business;

import android.content.Context;

import com.gosnowapp.Comms.CommsManager;
import com.gosnowapp.Comms.CommsResult;
import com.gosnowapp.Comms.CommsUtility;
import com.gosnowapp.Globals;
import com.gosnowapp.Model.Destination;
import com.gosnowapp.Model.User;
import com.gosnowapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Krishna on 8/8/2016.
 */
public class GoSnowLoginManager {

    private static GoSnowLoginManager   _loginMananger;
    int firstUser = 1;
    int subsequentUsers = 0;
    private GoSnowLoginManager()
    {

    }

    public static  GoSnowLoginManager getLoginManager() {
        if (_loginMananger == null) {
            _loginMananger = new GoSnowLoginManager();
        }
        return _loginMananger;
    }

    public String signin(Context context) {

        String error = null;
        RequestObjectManager reqManager = RequestObjectManager.getRequestManager();
        JSONObject singupRequest = reqManager.PrepareSignupRequest();

        //CommsResult result = CommsManager.getCommsManager().SigninUser(currentuser,context);
        CommsResult result = CommsManager.getCommsManager().SendPostRequest(singupRequest,CommsUtility.SIGNIN_USER_URL);

        if(result.getErrorCode() == CommsUtility.COMMS_ERROR)
            error = context.getResources().getString(R.string.error_connection);
        else
        {
            try {
                JSONObject responseObj = new JSONObject(result.getResponse());
                String status = (String) responseObj.get(CommsUtility.TITLE_STATUS);
                if(status.equals(CommsUtility.STATUS_ERROR))
                {
                    error = (String) responseObj.get(CommsUtility.TITLE_MESSAGE);
                }
                else
                {
                    ResponseObjectManager.getResponseManager().ParseSignupResponse(responseObj);

                    Globals globals = Globals.getInstance();
                    User currentuser = globals.getUser();

                    if(currentuser.getSignUpComplete() == 0)
                        AddPhoto(context);

                }

            }
            catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
            }

        }
       return error;
    }

    public String AddPhoto(Context context) {
        String error = null;
        CommsResult result = null;

        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        JSONObject addPhotoRequest = RequestObjectManager.getRequestManager().PrepareAddPhotoRequest();
        result = CommsManager.getCommsManager().SendPostRequest(addPhotoRequest,CommsUtility.ADD_PHOTO_URL);
        if(result.getErrorCode() == CommsUtility.COMMS_ERROR)
            error = context.getResources().getString(R.string.error_connection);
        else
        {
            try {
                JSONObject responseObj = new JSONObject(result.getResponse());
                String status = (String) responseObj.get(CommsUtility.TITLE_STATUS);
                if(status.equals(CommsUtility.STATUS_ERROR))
                {
                    error = (String) responseObj.get(CommsUtility.TITLE_MESSAGE);
                }
                else
                {
                    // Parse response
                    ResponseObjectManager.getResponseManager().ParseAddphotoResponse(responseObj);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
            }
        }

        return error;
    }


    public  String GetDestinationList(Context context,HashMap<String, ArrayList<Destination>> destinationMap) {

        String error = null;
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        JSONObject requestObj = new JSONObject();

        CommsResult result = CommsManager.getCommsManager().SendPostRequest(requestObj,CommsUtility.DESTINATIONLIST_URL);
       // destinationList = new ArrayList<Destination>();

        //HashMap<String, ArrayList<Destination>> destinationMap = new HashMap<String,ArrayList<Destination>>();

        if(result.getErrorCode() == CommsUtility.COMMS_ERROR)
            error = context.getResources().getString(R.string.error_connection);
        else
        {
            try {
                JSONObject responseObj = new JSONObject(result.getResponse());
                String status = (String) responseObj.get(CommsUtility.TITLE_STATUS);
                if(status.equals(CommsUtility.STATUS_ERROR))
                {
                    error = (String) responseObj.get(CommsUtility.TITLE_MESSAGE);
                }
                else
                {
                    // Parse response
                    JSONArray destinations = responseObj.getJSONArray(BusinessConsts.DESTINATIONS);

                    for(int index =0; index <destinations.length(); index++)
                    {
                        Destination destination = new Destination();
                        JSONObject destinationObj = (JSONObject) destinations.get(index);
                        destination.setDestinationId(destinationObj.getString(BusinessConsts.ID));
                        destination.setDestinationName(destinationObj.getString(BusinessConsts.DESTINATION_NAME));
                        destination.setDestinationCountry(destinationObj.getString(BusinessConsts.COUNTRY));
                        destination.setLatitude(destinationObj.getString(BusinessConsts.LATITUDE));
                        destination.setLongitude(destinationObj.getString(BusinessConsts.LONGITUDE));
                        destination.setImage(destinationObj.getString(BusinessConsts.ADDPHOTO_IMAGE));
                        destination.setThumb(destinationObj.getString(BusinessConsts.THUMB));

                        ArrayList<Destination> destList = destinationMap.get(destination.getDestinationCountry());
                        if(destList == null) {
                            destList = new ArrayList<Destination>();
                            destinationMap.put(destination.getDestinationCountry(),destList);
                        }
                        destList.add(destination);

                        //destinationList.add(destination);
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
            }
        }
        return error;
    }
}
