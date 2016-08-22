package com.jeavio.gosnow.Comms;

/**
 * Created by Krishna on 8/9/2016.
 */
public class CommsResult {

    int errorCode;

    String response;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
