package kg.gruzovoz.fcm;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesActivity;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static int message_counter;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getApplicationContext()
                .getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("FCM","onMessageReceived");
        if (remoteMessage.getNotification() != null ){
            if (!MessagesActivity.active) {
                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                message_counter++;
                editor.putInt("message_counter",message_counter).commit();
                Log.e("message_counterMFMS", String.valueOf(message_counter));

            }
            else {
                message_counter = 0;
                editor.putInt("message_counter",message_counter).commit();
            }

        }
    }

//    private void sendN(String title, String messageBody){
//        Intent intent = new Intent(this, MessagesActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent notificationIntent = PendingIntent.getActivity(this, 999, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this.getApplicationContext());
//        builder.setContentIntent(notificationIntent);
//        builder.setAutoCancel(true);
//        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_menu_view));
//        builder.setSmallIcon(android.R.drawable.ic_dialog_map);
//        builder.setContentText("Test Message Text");
//        builder.setTicker("Test Ticker Text");
//        builder.setContentTitle("Test Message Title");
//
//        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
//        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
//
//        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[0]);
//
//        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(999, builder.build());
//
//
//
//    }


    private void sendNotification(String title, String messageBody) {

        Intent intent = new Intent(this, MessagesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSound(defaultSoundUri)
                        .setColor(ContextCompat.getColor(this, R.color.colorPrimary));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_MAX);


            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
