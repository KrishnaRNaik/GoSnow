package com.gosnowapp.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gosnowapp.Business.BusinessConsts;
import com.gosnowapp.Globals;
import com.gosnowapp.Model.User;
import com.gosnowapp.R;

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

    public static void showSimpleAlert(String title, String message,Context context  ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View dlgview = inflater.inflate(R.layout.simple_alertlayout, null);
        builder.setView(dlgview);

        //builder.setTitle(title);
        TextView msgView = (TextView)dlgview.findViewById(R.id.msgid);
        msgView.setText(message);

        final AlertDialog simpleDlg = builder.create();
        simpleDlg.show();

        // click event for ok button.
        // cancel both the dialog (quick pay and the payment due)
        Button okbtn = (Button)dlgview.findViewById(R.id.okid);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleDlg.cancel();
            }
        });
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
