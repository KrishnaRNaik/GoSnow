package com.jeavio.gosnow.Comms;

/**
 * Created by Krishna on 8/8/2016.
 */
public class CommsUtility {

    // URL to authenticate new user
    public static final String  BASE_URL = "http://192.168.0.115";

    // URL to authenticate new user
    public static final String  SIGNIN_USER_URL = "/gosnow/index.php/service/auth/signin";

    // constant error code for authentication error
    public static final int COMMS_ERROR = 1;

    public static final int READ_TIMEOUT = 10000;

    // constant string for device inactive
    public static final int CONNECTION_TIMEOUT = 15000;

    public static final String CONTENT_TYPE = "application/json";


    public  static final String FACEBOOK_ID = "facebookId";

    public  static final String FIRST_NAME = "firstName";

    public  static final String LAST_NAME = "lastName";

    public  static final String  AGE = "age";

    public  static final String  GENDER = "gender";


}
