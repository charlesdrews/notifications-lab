package com.charlesdrews.notificationlab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
