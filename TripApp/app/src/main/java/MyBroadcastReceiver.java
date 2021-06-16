package com.example.tripapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_BATTERY_LOW.contentEquals(intent.getAction())){
            Toast.makeText(context,"Battery Low",Toast.LENGTH_SHORT).show();
        }

        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(context,"Boot Completed",Toast.LENGTH_SHORT).show();
        }

        if(Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())){
            Toast.makeText(context,"Airplane Mood Changed",Toast.LENGTH_SHORT).show();
        }

    }
}
