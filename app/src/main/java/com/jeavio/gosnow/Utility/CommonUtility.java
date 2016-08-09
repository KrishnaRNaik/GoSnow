package com.jeavio.gosnow.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Krishna on 8/5/2016.
 */
public  class CommonUtility {

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
}
