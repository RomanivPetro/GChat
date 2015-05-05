package com.android.romaniv.gchat.InternetCommunication;

import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Петро on 05.05.2015.
 */
public class Utils {
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static String getIp() {
        // WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        // String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String ip = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://ip2country.sourceforge.net/ip2c.php?format=JSON");
            // HttpGet httpget = new HttpGet("http://whatismyip.com.au/");
            // HttpGet httpget = new HttpGet("http://www.whatismyip.org/");
            HttpResponse response;

            response = httpclient.execute(httpget);
            //Log.i("externalip",response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            entity.getContentLength();
            String str = EntityUtils.toString(entity);
            JSONObject json_data = new JSONObject(str);
            ip = json_data.getString("ip");

        } catch (Exception e) {
        }

        return ip;
    }
}
