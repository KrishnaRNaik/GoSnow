package com.gosnowapp.Business;

import com.gosnowapp.Globals;
import com.gosnowapp.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Krishna on 8/29/2016.
 */
public class ResponseObjectManager {
    private static ResponseObjectManager   _resObjManager;

    private ResponseObjectManager()
    {

    }

    public static  ResponseObjectManager getResponseManager() {
        if (_resObjManager == null) {
            _resObjManager = new ResponseObjectManager();
        }
        return _resObjManager;
    }

    public void ParseSignupResponse(JSONObject signupResponse) {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        try {
            // Parse response
            currentuser.setSessionId((String) signupResponse.get(BusinessConsts.SESSION_ID));
            JSONObject profile = signupResponse.getJSONObject(BusinessConsts.PROFILE);

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
            currentuser.setSignUpComplete(profile.getInt(BusinessConsts.ISSIGNUP_COMPLETE));
            // currentuser.setOnlineTill(profile.getString(BusinessConsts.ONLINE_TILL));
            String nextDestId = profile.getString(BusinessConsts.NEXTDESTINATION_ID);
            if (nextDestId != null && !nextDestId.isEmpty())
                currentuser.setNextDestinationId(Integer.parseInt(nextDestId));
            currentuser.setNextDestinationName(profile.getString(BusinessConsts.NEXTDESTINATION_NAME));
            JSONArray interests, locations;
            if (!signupResponse.isNull(BusinessConsts.INTERESTS))
                interests = signupResponse.getJSONArray(BusinessConsts.INTERESTS);

            if (!signupResponse.isNull(BusinessConsts.LOCATIONS))
                locations = signupResponse.getJSONArray(BusinessConsts.LOCATIONS);

                       /* if(currentuser.getSignUpComplete() == 0)
                            AddPhoto(context);*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void ParseAddphotoResponse(JSONObject addPhotoResponse)
    {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        try {
            // Parse response
            currentuser.setSessionId((String) addPhotoResponse.get(BusinessConsts.SESSION_ID));
            JSONObject profile = addPhotoResponse.getJSONObject(BusinessConsts.PROFILE);

            if(!profile.isNull(BusinessConsts.PROFILE_IMAGE))
                currentuser.setProfileImage(profile.getString(BusinessConsts.PROFILE_IMAGE));
            if(!profile.isNull(BusinessConsts.PROFILEIMAGE_250))
                currentuser.setProfileImage250(profile.getString(BusinessConsts.PROFILEIMAGE_250));
            if(!profile.isNull(BusinessConsts.PROFILE_THUMB))
                currentuser.setProfileThumb(profile.getString(BusinessConsts.PROFILE_THUMB));
            if(!profile.isNull(BusinessConsts.USER_PHOTOS))
                currentuser.setUserPhotos(profile.getString(BusinessConsts.USER_PHOTOS));
            if(!profile.isNull(BusinessConsts.USER_PHOTOS250))
                currentuser.setUserPhotos250(profile.getString(BusinessConsts.USER_PHOTOS250));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void ParseUpdateProfileResponse(JSONObject signupResponse) {
        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();
        try {
            // Parse response
            currentuser.setSessionId((String) signupResponse.get(BusinessConsts.SESSION_ID));
            JSONObject profile = signupResponse.getJSONObject(BusinessConsts.PROFILE);

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
            currentuser.setSignUpComplete(profile.getInt(BusinessConsts.ISSIGNUP_COMPLETE));
            // currentuser.setOnlineTill(profile.getString(BusinessConsts.ONLINE_TILL));
            String nextDestId = profile.getString(BusinessConsts.NEXTDESTINATION_ID);
            if (nextDestId != null && !nextDestId.isEmpty())
                currentuser.setNextDestinationId(Integer.parseInt(nextDestId));
            currentuser.setNextDestinationName(profile.getString(BusinessConsts.NEXTDESTINATION_NAME));
            JSONArray interests, locations;
            if (!signupResponse.isNull(BusinessConsts.INTERESTS))
                interests = signupResponse.getJSONArray(BusinessConsts.INTERESTS);

            if (!signupResponse.isNull(BusinessConsts.LOCATIONS))
                locations = signupResponse.getJSONArray(BusinessConsts.LOCATIONS);

                       /* if(currentuser.getSignUpComplete() == 0)
                            AddPhoto(context);*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
