package at.cosmosinsurance.online;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationUtils extends ContextWrapper {

    private NotificationManagerCompat mManager;
    public static final String PRIVATE_CHANNEL_ID = "at.cosmosinsurance.online.private";
    public static final String PUBLIC_CHANNEL_ID = "at.cosmosinsurance.online.public";
    public static final String PRIVATE_CHANNEL_NAME = "PRIVATE CHANNEL";
    public static final String PUBLIC_CHANNEL_NAME = "PUBLIC CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {
        // create private channel
        NotificationChannel androidChannel = new NotificationChannel(PRIVATE_CHANNEL_ID,
                PRIVATE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.BLUE);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(androidChannel);

        // create public channel
        NotificationChannel iosChannel = new NotificationChannel(PUBLIC_CHANNEL_ID,
                PUBLIC_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        iosChannel.enableLights(true);
        iosChannel.enableVibration(true);
        iosChannel.setLightColor(Color.GRAY);
        iosChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(iosChannel);
    }

    private NotificationManagerCompat getManager() {
        if (mManager == null) {
            mManager = NotificationManagerCompat.from(getApplicationContext());
        }
        return mManager;
    }

    public NotificationCompat.Builder getPrivateChannelNotification(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), PRIVATE_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getPublicChannelNotification(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), PUBLIC_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }

    public void showPrivateNotification(String title, String body, int notificationId) {
        NotificationCompat.Builder privateNotificationBuilder = getPrivateChannelNotification(title, body);
        getManager().notify(notificationId, privateNotificationBuilder.build());
    }

    public void showPublicNotification(String title, String body, int notificationId) {
        NotificationCompat.Builder publicNotificationBuilder = getPublicChannelNotification(title, body);
        getManager().notify(notificationId, publicNotificationBuilder.build());
    }

    public void cancelNotification(int notificationId) {
        getManager().cancel(notificationId);
    }

    public void cancelAllNotifications() {
        getManager().cancelAll();
    }
}
