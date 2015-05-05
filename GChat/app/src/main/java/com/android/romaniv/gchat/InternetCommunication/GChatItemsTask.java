package com.android.romaniv.gchat.InternetCommunication;

import android.os.AsyncTask;

import com.android.romaniv.gchat.InternetCommunication.AsyncResponse;
import com.android.romaniv.gchat.InternetCommunication.GChatHttpFetchr;

/**
 * Created by Петро on 05.05.2015.
 */
public class GChatItemsTask extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;

    @Override
    protected String doInBackground(String... params) {
        GChatHttpFetchr fetcher = new GChatHttpFetchr();
        return fetcher.fetchItems(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
