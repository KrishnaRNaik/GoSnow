package com.gosnowapp.Comms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.gosnowapp.Business.BusinessConsts;
import com.gosnowapp.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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


    public CommsResult AddPhoto(User user, Context context)
    {
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        // String error = null;
        CommsResult result = new CommsResult();
        result.errorCode = CommsUtility.COMMS_ERROR;
        try {

            URL url = new URL(CommsUtility.BASE_URL + CommsUtility.ADD_PHOTO_URL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CommsUtility.READ_TIMEOUT /*milliseconds*/);
            conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", CommsUtility.CONTENT_TYPE);
            conn.setRequestProperty("Accept", CommsUtility.CONTENT_TYPE);

            JSONObject obj = new JSONObject();

            URL profileImageUrl = null;
            Bitmap bitmap = null;
            try {
                profileImageUrl = new URL("https://graph.facebook.com/" + user.getFacebookId() + "/picture?width=400&height=400");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                bitmap  = BitmapFactory.decodeStream(profileImageUrl.openConnection().getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }


            if(bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                obj.put(BusinessConsts.ADDPHOTO_IMAGE, encodedImage);
            }


            obj.put(BusinessConsts.ADDPHOTO_IMAGEURL,"");
            obj.put(BusinessConsts.ADDPHOTO_ISPROFILEIMAGE,1);
            obj.put(BusinessConsts.SESSION_ID, user.getSessionId());


            //open
            conn.connect();
            String message = obj.toString();
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
                result.setResponse(response.toString());
                result.setErrorCode(CommsUtility.COMMS_SUCCESS);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            //  error = e.getMessage();
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

            if(conn != null)
                conn.disconnect();

            return result;
        }

    }


    public CommsResult SigninUser(User user, Context context)
    {
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        // String error = null;
        CommsResult result = new CommsResult();
        result.errorCode = CommsUtility.COMMS_ERROR;
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

            obj.put(BusinessConsts.FACEBOOK_ID, user.getFacebookId());

            if(user.getFirstName() != null) {
                byte[] dataFirstName = user.getFirstName().getBytes("UTF-8");
                String encodedFirstName = Base64.encodeToString(dataFirstName, Base64.DEFAULT);
                obj.put(BusinessConsts.FIRST_NAME, encodedFirstName);
            }

            if(user.getLastName() != null) {
                byte[] dataLastName = user.getLastName().getBytes("UTF-8");
                String encodedLastName = Base64.encodeToString(dataLastName, Base64.DEFAULT);
                obj.put(BusinessConsts.LAST_NAME, encodedLastName);
            }
            obj.put(BusinessConsts.GENDER, user.getGender());
            obj.put(BusinessConsts.AGE,user.getAge());

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
                result.setResponse(response.toString());
                result.setErrorCode(CommsUtility.COMMS_SUCCESS);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            //  error = e.getMessage();
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

            if(conn != null)
                conn.disconnect();

            return result;
        }

    }

    public CommsResult DestinationList(User user, Context context)
    {
       /*         OutputStream os = null;
                BufferedReader in = null;
                HttpURLConnection conn = null;
                // String error = null;
                CommsResult result = new CommsResult();
                result.errorCode = CommsUtility.COMMS_ERROR;
                try {

                    URL url = new URL(CommsUtility.BASE_URL + CommsUtility.DESTINATIONLIST_URL);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(CommsUtility.READ_TIMEOUT *//*milliseconds*//*);
                    conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT *//* milliseconds *//*);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", CommsUtility.CONTENT_TYPE);
                    conn.setRequestProperty("Accept", CommsUtility.CONTENT_TYPE);

                    JSONObject obj = new JSONObject();

                    obj.put(BusinessConsts.FACEBOOK_ID, user.getFacebookId());

                    if(user.getFirstName() != null) {
                        byte[] dataFirstName = user.getFirstName().getBytes("UTF-8");
                        String encodedFirstName = Base64.encodeToString(dataFirstName, Base64.DEFAULT);
                        obj.put(BusinessConsts.FIRST_NAME, encodedFirstName);
                    }

                    if(user.getLastName() != null) {
                        byte[] dataLastName = user.getLastName().getBytes("UTF-8");
                        String encodedLastName = Base64.encodeToString(dataLastName, Base64.DEFAULT);
                        obj.put(BusinessConsts.LAST_NAME, encodedLastName);
                    }
                    obj.put(BusinessConsts.GENDER, user.getGender());
                    obj.put(BusinessConsts.AGE,user.getAge());

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
                        result.setResponse(response.toString());
                        result.setErrorCode(CommsUtility.COMMS_SUCCESS);
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (ProtocolException e) {
                    e.printStackTrace();
                    // error = e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    // error = e.getMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  error = e.getMessage();
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

                    if(conn != null)
                        conn.disconnect();

                    return result;
                }

*/
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        // String error = null;
        CommsResult result = new CommsResult();
        result.errorCode = CommsUtility.COMMS_ERROR;
        try {

            URL url = new URL(CommsUtility.BASE_URL + CommsUtility.DESTINATIONLIST_URL);

            conn = (HttpURLConnection) url.openConnection();
           // conn.setReadTimeout(CommsUtility.READ_TIMEOUT /*milliseconds*/);
           // conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT /* milliseconds */);
            conn.setRequestMethod("GET");
            //conn.setDoInput(true);

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
                result.setResponse(response.toString());
                result.setErrorCode(CommsUtility.COMMS_SUCCESS);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            // error = e.getMessage();
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

            if(conn != null)
                conn.disconnect();

            return result;
        }

    }

    public CommsResult SendPostRequest(JSONObject requestObj, String requestUrl)
    {
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        // String error = null;
        CommsResult result = new CommsResult();
        result.errorCode = CommsUtility.COMMS_ERROR;
        try {

            URL url = new URL(CommsUtility.BASE_URL + requestUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CommsUtility.READ_TIMEOUT /*milliseconds*/);
            conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", CommsUtility.CONTENT_TYPE);
            conn.setRequestProperty("Accept", CommsUtility.CONTENT_TYPE);

                    /*JSONObject obj = new JSONObject();

                    obj.put(BusinessConsts.FACEBOOK_ID, user.getFacebookId());

                    if(user.getFirstName() != null) {
                        byte[] dataFirstName = user.getFirstName().getBytes("UTF-8");
                        String encodedFirstName = Base64.encodeToString(dataFirstName, Base64.DEFAULT);
                        obj.put(BusinessConsts.FIRST_NAME, encodedFirstName);
                    }

                    if(user.getLastName() != null) {
                        byte[] dataLastName = user.getLastName().getBytes("UTF-8");
                        String encodedLastName = Base64.encodeToString(dataLastName, Base64.DEFAULT);
                        obj.put(BusinessConsts.LAST_NAME, encodedLastName);
                    }
                    obj.put(BusinessConsts.GENDER, user.getGender());
                    obj.put(BusinessConsts.AGE,user.getAge());
*/
            String message = requestObj.toString();

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
                result.setResponse(response.toString());
                result.setErrorCode(CommsUtility.COMMS_SUCCESS);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            // error = e.getMessage();
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

            if(conn != null)
                conn.disconnect();

            return result;
        }

    }


    public CommsResult SendGetRequest( String requestUrl)
    {
        OutputStream os = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        // String error = null;
        CommsResult result = new CommsResult();
        result.errorCode = CommsUtility.COMMS_ERROR;
        try {

            URL url = new URL(CommsUtility.BASE_URL + requestUrl);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CommsUtility.READ_TIMEOUT /*milliseconds*/);
            conn.setConnectTimeout(CommsUtility.CONNECTION_TIMEOUT /* milliseconds */);
            //conn.setRequestMethod("POST");
            conn.setDoInput(true);

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
                result.setResponse(response.toString());
                result.setErrorCode(CommsUtility.COMMS_SUCCESS);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (ProtocolException e) {
            e.printStackTrace();
            // error = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            // error = e.getMessage();
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

            if(conn != null)
                conn.disconnect();

            return result;
        }

    }

}