package com.android.romaniv.gchat.InternetCommunication;

import android.os.AsyncTask;

import com.android.romaniv.gchat.InternetCommunication.AsyncResponse;
import com.android.romaniv.gchat.InternetCommunication.GChatHttpFetchr;

/**
 * Created by Петро on 05.05.2015.
 */
public class GChatItemsTask extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;
    private String method;

    @Override
    protected String doInBackground(String... params) {
        method = params[0];
        GChatHttpFetchr fetcher = new GChatHttpFetchr();
        return fetcher.fetchItems(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        switch (method){
            case NETConstants.AddNewUser:
                delegate.AddUserFinish(result);
                break;
            case NETConstants.CheckUser:
                delegate.CheckUserFinish(result);
                break;
            case NETConstants.SelectAllUser:
                delegate.SelectAllUserFinish(result);
                break;
            default:
            delegate.processFinish(result);
                break;
        }

    }
}
