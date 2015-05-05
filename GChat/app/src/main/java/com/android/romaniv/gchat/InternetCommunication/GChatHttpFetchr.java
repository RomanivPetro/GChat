package com.android.romaniv.gchat.InternetCommunication;

import android.net.Uri;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Петро on 04.05.2015.
 */
public class GChatHttpFetchr {
    public static final String TAG = "GChatHttpRequest";
    private String result;

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchItems(String method, String parameters) {
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(NETConstants.SCHEMA)
                    .authority(NETConstants.ENDPOINT)
                    .appendPath(NETConstants.PATH)
                    .appendQueryParameter(method, parameters);
            String myUrl = builder.build().toString();
            Log.d(TAG, "End point: " + myUrl);
            result = getUrl(myUrl);
            Log.i(TAG, "Received data: " + result);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
        return result;
    }

}
