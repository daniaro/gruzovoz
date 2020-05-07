//package kg.gruzovoz.fcm
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.util.Log
//import androidx.core.app.NotificationCompat
//import androidx.core.content.ContextCompat
//import kg.gruzovoz.App
//import kg.gruzovoz.R
//
//class Notfn {
//
//    private var manager =
//            App.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//    init {
//        initHighChannel()
//    }
//
//    private fun initHighChannel() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
//
//        Log.e(tag, "initHighChannel")
//        val channel =
//                NotificationChannel(channelHigh, "new_message", NotificationManager.IMPORTANCE_MAX)
////        channel.importance = NotificationManager.IMPORTANCE_HIGH
//        manager.createNotificationChannel(channel)
//
//    }
//
//    fun showNotification(title: String, body: String, url: String?) {
//        val notifyIntent = Intent(App.context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            if (title.contains("Новый заказ")) putExtra("title", title)
//            else {
//                putExtra("title", title)
//                putExtra("body", body)
//            }
//            if (!url.isNullOrEmpty()) {
//                action = Intent.ACTION_VIEW
//                data = Uri.parse(url)
//            }
//
//        }
//        val pendingIntent = PendingIntent.getActivity(
//                App.context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//
//        val builder = NotificationCompat.Builder(App.context, channelHigh)
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentIntent(pendingIntent)
//                .setContentTitle(title)
//                .setAutoCancel(true)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.ic_notification)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setColor(ContextCompat.getColor(App.context, R.color.colorPrimary))
//
//        manager.notify(0, builder.build())
//        updateBadge()
//    }
//}