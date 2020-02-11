package com.ninjeng.softwaricacollegestudentcommunicationportal.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class CreateChannel {
    Context context;
    public final static String CHANNEL_1="WelcomeChannel;";

    public CreateChannel(Context context) {
        this.context = context;
    }
    public void CreateChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1,"Channel1", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
