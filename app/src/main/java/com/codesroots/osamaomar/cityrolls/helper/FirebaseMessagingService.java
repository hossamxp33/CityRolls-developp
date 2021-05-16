package com.codesroots.osamaomar.cityrolls.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.codesroots.osamaomar.cityrolls.R;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;


public class FirebaseMessagingService extends
        com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            JSONObject myJson = null;
            String title = "";
            String description = "";
            String click = "";
            String id1 = remoteMessage.getData().get("company_id");
            if (remoteMessage.getData() != null) {
                myJson = new JSONObject(remoteMessage.getData());
                title = remoteMessage.getNotification().getTitle();
                description =remoteMessage.getNotification().getBody();
                click = myJson.optString("click_action").toString();
                sendNotification(title, description, click, id1);
            } else if (remoteMessage.getNotification() != null) {
//                title = remoteMessage.getNotification().getTitle();
//                description = remoteMessage.getNotification().getBody();
//                click = remoteMessage.getNotification().getClickAction();
//                sendNotification(title, description, click, "0");
            }

        } catch (JsonSyntaxException a) {
            a.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedMessages() {
    }

//    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//    }

    private void sendNotification(String title, String messageBody, String click, String id) {
        try {
            Intent intent = new Intent(click);
            intent.putExtra("companyUserid", Integer.parseInt(id));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

            String channelId = getString(R.string.default_notification_channel_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setContentTitle(title)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(messageBody))
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent)
                           .setPriority(Notification.PRIORITY_HIGH);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.logo);
                notificationBuilder.setLargeIcon(bitmap1);
            } else {
                notificationBuilder.setSmallIcon(R.drawable.logo);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        title,
                        NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

