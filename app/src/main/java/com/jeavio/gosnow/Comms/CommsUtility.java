package com.jeavio.gosnow.Comms;

/**
 * Created by Krishna on 8/8/2016.
 */
public class CommsUtility {

    // URL to authenticate new user
     public static final String  BASE_URL = "http://192.168.0.71";

    //public static final String  BASE_URL = "http://diana-Latitude-E7450";

    // URL to authenticate new user
    public static final String  SIGNIN_USER_URL = "/gosnow/index.php/service/auth/signin";

    // URL to add photo
    public static final String  ADD_PHOTO_URL = "/gosnow/index.php/service/user/addPhoto";

    // constant error code for authentication error
    public static final int COMMS_ERROR = 1;

    // constant error code for authentication error
    public static final int COMMS_SUCCESS = 0;

    public static final int READ_TIMEOUT = 10000;

    // constant string for device inactive
    public static final int CONNECTION_TIMEOUT = 15000;

    public static final String CONTENT_TYPE = "application/json";

    public  static final String  STATUS_OK = "OK";

    public  static final String  STATUS_ERROR = "error";

    public  static final String  TITLE_STATUS = "status";

    public  static final String  TITLE_MESSAGE = "apiMessage";

}
