package com.jeavio.gosnow.Business;

import android.content.Context;

import com.jeavio.gosnow.Comms.CommsManager;
import com.jeavio.gosnow.Comms.CommsResult;
import com.jeavio.gosnow.Comms.CommsUtility;
import com.jeavio.gosnow.Globals;
import com.jeavio.gosnow.Model.User;
import com.jeavio.gosnow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        CommsResult result = CommsManager.getCommsManager().SigninUser(currentuser,context);

        //User currentuser = new User();

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
                    currentuser.setSenderId((String) responseObj.get(BusinessConsts.SESSION_ID));
                    JSONObject profile = responseObj.getJSONObject(BusinessConsts.PROFILE);

                    currentuser.setAboutMe(profile.getString(BusinessConsts.ABOUT_ME));
                    currentuser.setDistance(profile.getString(BusinessConsts.DISTANCE));
                    currentuser.setInMyContact(Boolean.getBoolean(profile.getString(BusinessConsts.IS_INMYCONTACT)));
                    currentuser.setLatitude(profile.getString(BusinessConsts.LATITUDE));
                    currentuser.setLongitude(profile.getString(BusinessConsts.LONGITUDE));
                    currentuser.setAge(profile.getString(BusinessConsts.AGE));
                    currentuser.setGender(profile.getString(BusinessConsts.GENDER));
                    currentuser.setEmail(profile.getString(BusinessConsts.EMAIL));
                    currentuser.setFirstName(profile.getString(BusinessConsts.FIRST_NAME));
                    currentuser.setLastName(profile.getString(BusinessConsts.LAST_NAME));
                    currentuser.setModel(profile.getString(BusinessConsts.MODEL));
                    currentuser.setRiderType(profile.getString(BusinessConsts.RIDERTYPE_NAME));
                    currentuser.setRiderTypeId(profile.getString(BusinessConsts.RIDERTYPE_ID));
                    currentuser.setSkillLevelID(profile.getString(BusinessConsts.SKILLLEVEL_ID));
                    currentuser.setSkillLevel(profile.getString(BusinessConsts.SKILLLEVEL_NAME));
                   // currentuser.setOnlineTill(profile.getString(BusinessConsts.ONLINE_TILL));
                    String nextDestId =  profile.getString(BusinessConsts.NEXTDESTINATION_ID);
                    if(nextDestId != null && !nextDestId.isEmpty())
                        currentuser.setNextDestinationId(Integer.parseInt(nextDestId));
                    currentuser.setNextDestinationName(profile.getString(BusinessConsts.NEXTDESTINATION_NAME));
                    JSONArray interests,locations;
                    if(!responseObj.isNull(BusinessConsts.INTERESTS))
                        interests = responseObj.getJSONArray(BusinessConsts.INTERESTS);

                    if(!responseObj.isNull(BusinessConsts.LOCATIONS))
                        locations = responseObj.getJSONArray(BusinessConsts.LOCATIONS);


                }

            }
            catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage();
            }

        }
       // globals.setUser(currentuser);
        return error;

    }

}
