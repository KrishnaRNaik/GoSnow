package com.jeavio.gosnow.Comms;

import android.content.Context;
import android.util.Base64;

import com.jeavio.gosnow.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Krishna on 8/8/2016.
 */
public class CommsManager {
    private static CommsManager   _commsManager;
    String res;
    private CommsManager()
    {

    }

    public static  CommsManager getCommsManager()
    {
        if (_commsManager == null)
        {
            _commsManager = new CommsManager();
        }
        return _commsManager;
    }

    public  int SigninUser(User user, Context context)
    {
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        int result = CommsUtility.COMMS_ERROR;


        try {

            URL url = new URL(CommsUtility.BASE_URL + CommsUtility.SIGNIN_USER_URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CommsUtility.READ_TIMEOUT /*milliseconds*/);
            conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", CommsUtility.CONTENT_TYPE);
            conn.setRequestProperty("Accept", CommsUtility.CONTENT_TYPE);

            JSONObject obj = new JSONObject();

            obj.put(CommsUtility.FACEBOOK_ID, /*user.getFacebookId()*/"12345431");

            byte[] data = user.getFirstName().getBytes("UTF-8");
            String encodedFirstName = Base64.encodeToString(data, Base64.DEFAULT);
            obj.put(CommsUtility.FIRST_NAME, encodedFirstName);

            byte[] dataLastName = user.getLastName().getBytes("UTF-8");
            String encodedLastName = Base64.encodeToString(data, Base64.DEFAULT);

            obj.put(CommsUtility.LAST_NAME, encodedLastName);
            obj.put(CommsUtility.GENDER, user.getGender());
            obj.put(CommsUtility.AGE,user.getAge());

            String message = obj.toString();

            //open
            conn.connect();

            //setup send
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //clean up
            os.flush();

            // read the response
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                res = response.toString();
                String resul = res;
               /* if(!res.equals(CommsUtility.LOGIN_FAIL))
                {
                    BusinessUtility.DEVICE_ID = res;
                    currentUser.setDeviceId(res);
                    result = CommsUtility.AUTHENTICATION_SUCCESS;
                }
                else
                    result = CommsUtility.AUTHENTICATION_ERROR;*/
            }
            else
                result = CommsUtility.COMMS_ERROR;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //clean up
            try {
                String resp = res;
                if(os!= null)
                    os.close();
                if(in != null)
                    in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.disconnect();
            return result;
        }

    }
}
