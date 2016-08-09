package com.jeavio.gosnow.Business;

import android.content.Context;

import com.jeavio.gosnow.Comms.CommsManager;
import com.jeavio.gosnow.Model.User;

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

    public String signin(Context context, User currentuser) {

        String error = null;
        CommsManager.getCommsManager().SigninUser(currentuser,context);

        return error;

    }

}
