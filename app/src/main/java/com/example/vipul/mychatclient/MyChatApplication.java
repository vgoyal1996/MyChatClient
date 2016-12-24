package com.example.vipul.mychatclient;

import com.firebase.client.Firebase;

/**
 * Created by VIPUL on 12/15/2016.
 */
public class MyChatApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
