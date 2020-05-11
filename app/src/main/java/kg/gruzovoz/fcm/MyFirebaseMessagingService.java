package kg.gruzovoz.fcm;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesActivity;


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
        Log.e("FCM", "onMessageReceived");
        if (remoteMessage.getNotification() != null) {
            if (!MessagesActivity.active) {
                sendNotification(this, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                message_counter++;
                editor.putInt("message_counter", message_counter).commit();
                Log.e("message_counterMFMS", String.valueOf(message_counter));

            } else {
                message_counter = 0;
                editor.putInt("message_counter", message_counter).commit();
            }

        }
    }

    public static void sendNotification(Context context, String title, String messageBody) {

        Intent intent = new Intent(context, MessagesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification =
                new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSound(defaultSoundUri)
                        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        .build();

        NotificationManagerCompat.from(context).notify(0, notification);
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.app_name),
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManagerCompat.from(context).createNotificationChannel(channel);

        }
    }
}
