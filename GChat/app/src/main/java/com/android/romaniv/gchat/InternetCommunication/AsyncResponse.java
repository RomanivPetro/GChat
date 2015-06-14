package com.android.romaniv.gchat.InternetCommunication;

import java.util.EventObject;

/**
 * Created by Петро on 05.05.2015.
 */
public interface AsyncResponse {
    void processFinish(String output);
    void AddUserFinish(String output);
    void CheckUserFinish(String output);
    void SelectAllUserFinish(String output);
}


