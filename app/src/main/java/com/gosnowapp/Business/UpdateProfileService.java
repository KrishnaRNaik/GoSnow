package com.gosnowapp.Business;

import android.content.Context;

import com.gosnowapp.Comms.CommsManager;
import com.gosnowapp.Comms.CommsResult;
import com.gosnowapp.Comms.CommsUtility;
import com.gosnowapp.Globals;
import com.gosnowapp.Model.User;
import com.gosnowapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Krishna on 8/30/2016.
 */
public class UpdateProfileService {
    private static UpdateProfileService _updateProfile;
    int firstUser = 1;
    int subsequentUsers = 0;
    private UpdateProfileService()
    {

    }

    public static UpdateProfileService getUpdateProfileService() {
        if (_updateProfile == null) {
            _updateProfile = new UpdateProfileService();
        }
        return _updateProfile;
    }

    public String UpdateProfile(Context context)
    {
        String error = null;
        CommsResult result = null;

        Globals globals = Globals.getInstance();
        User currentuser = globals.getUser();

        JSONObject updateProfileRequest = RequestObjectManager.getRequestManager().PrepareUpdateProfileRequest();
        result = CommsManager.getCommsManager().SendPostRequest(updateProfileRequest, CommsUtility.UPDATEPROFILE_URL);
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
                    ResponseObjectManager.getResponseManager().ParseUpdateProfileResponse(responseObj);

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
