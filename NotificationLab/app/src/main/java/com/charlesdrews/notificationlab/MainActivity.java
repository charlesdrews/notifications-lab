package com.charlesdrews.notificationlab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // launch a notification with a call action
        NotificationCompat.Builder callBuilder = new NotificationCompat.Builder(this);
        callBuilder.setSmallIcon(R.drawable.ic_phone_forwarded_white_18dp);
        callBuilder.setContentTitle("Make a phone call");
        callBuilder.setContentText("Call 555-555-5555");

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: 5555555555"));

        PendingIntent callPendingIntent = PendingIntent.getActivity(
                this, (int) System.currentTimeMillis(), callIntent, 0);

        //callBuilder.setContentIntent(callPendingIntent);
        //callBuilder.setAutoCancel(false);
        callBuilder.addAction(R.drawable.ic_phone_forwarded_white_18dp, "CALL NOW!", callPendingIntent);
        NotificationManager callManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        callManager.notify(2, callBuilder.build());



        // launch a notification showing network connectivity
        ConnectivityManager cmc = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cmc.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_perm_scan_wifi_red_500_18dp);
        builder.setContentTitle("Notification Alert!");

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        if (isConnected) {
            style.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.wireless_network_bridge));
            builder.setAutoCancel(true);
        } else {
            style.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.p02_server));
            builder.setAutoCancel(false);
        }

        builder.setStyle(style);

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, (int) System.currentTimeMillis(), intent, 0);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
