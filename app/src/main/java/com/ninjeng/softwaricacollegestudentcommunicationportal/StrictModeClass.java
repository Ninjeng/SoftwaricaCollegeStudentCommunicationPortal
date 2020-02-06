package com.ninjeng.softwaricacollegestudentcommunicationportal;

import android.os.StrictMode;

public class StrictModeClass {
    public  static void StrictMode(){
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
