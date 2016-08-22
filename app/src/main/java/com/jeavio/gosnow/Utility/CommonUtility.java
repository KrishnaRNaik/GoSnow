package com.jeavio.gosnow.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeavio.gosnow.Business.BusinessConsts;
import com.jeavio.gosnow.Globals;
import com.jeavio.gosnow.Model.User;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Krishna on 8/5/2016.
 */
public  class CommonUtility {

    // Returns Age from Birth date
    public static int GetAge(String birthDate)
    {
        SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = curFormater.parse(birthDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return  age;

    }

    // Sets IsFirstTime flag
    public static void SetIsOpened(Context ctx)
    {
        final String PREFS_NAME = "UserProfile";

        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(BusinessConsts.IS_FIRSTTIME, false);

        // Commit the edits!
        editor.commit();

    }

    // Returns isFirstTime flag
    public static boolean IsFirstTime(Context ctx)
    {
        final String PREFS_NAME = "UserProfile";
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        boolean isFirstTime = settings.getBoolean(BusinessConsts.IS_FIRSTTIME,true);

        return isFirstTime;
    }

    // Parse the json response from facebook
    public static void ParseFbResponse(JSONObject responseObj)
    {
        Globals globals = Globals.getInstance();
        User user = globals.getUser();
        try {
            if (!responseObj.isNull("first_name"))
                user.setFirstName(responseObj.getString("first_name").toString());

            if (!responseObj.isNull("last_name"))
                user.setLastName(responseObj.getString("last_name").toString());

            if (!responseObj.isNull("gender"))
                user.setGender(responseObj.getString("gender").toString());

            if (!responseObj.isNull("birthday")) {
                String birthday = responseObj.getString("birthday").toString();
                int age = CommonUtility.GetAge(birthday);
                user.setAge(String.valueOf(age));
            }
            if (!responseObj.isNull("id"))
                user.setFacebookId(responseObj.getString("id").toString());

            if (!responseObj.isNull("email"))
                user.setEmail(responseObj.getString("email").toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
