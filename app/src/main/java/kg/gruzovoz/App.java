package kg.gruzovoz;

import androidx.multidex.MultiDexApplication;

import kg.gruzovoz.fcm.MyFirebaseMessagingService;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MyFirebaseMessagingService.createNotificationChannel(this);
    }
}
