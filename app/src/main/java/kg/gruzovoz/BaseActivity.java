package kg.gruzovoz;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import kg.gruzovoz.chat.ChatFragment;
import kg.gruzovoz.user_page.UserPageFragment;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.order.OrdersFragment;

public class BaseActivity extends AppCompatActivity {

    Fragment ordersFragment = new OrdersFragment();
    Fragment chatFragment = new ChatFragment();
    Fragment userPageFragment = new UserPageFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    SharedPreferences sharedPreferences;
    public static String authToken;
    public static Long fbUserId;
    boolean online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext()
                .getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        fbUserId = sharedPreferences.getLong("fbUserId",0);

        authToken = "Token " + sharedPreferences.getString("authToken", null);
        if (sharedPreferences.getString("authToken", null) == null) {
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Log.e("authToken: ", authToken);

        userPageFragment = new UserPageFragment(() -> {
            Fragment fragment = fragmentManager.findFragmentByTag("3");
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        });

        setPresence(Boolean.TRUE);
//        initAutoStart();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, ordersFragment, "1").commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_orders:
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, ordersFragment).commit();
                        return true;
                    case R.id.navigation_chat:
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, chatFragment, "2").commit();
                        return true;
                    case R.id.navigation_user_page:
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, userPageFragment, "3").commit();
                        return true;
                }
                return false;
            };

    @Override
    protected void onStop() {
        super.onStop();
        setPresence(Boolean.FALSE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPresence(Boolean.TRUE);
    }

    private void setPresence(boolean online)  {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference("presence");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    DatabaseReference con = myConnectionsRef.child(String.valueOf(fbUserId));
                    con.setValue(online);

                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.e("error onCancelled", "Listener was cancelled at .info/connected");
            }
        });
    }

//    private void initAutoStart() {
//        if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("xiaomi") ) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(this));
//                builder.setTitle("Важно для Xiaomi")
//                        .setMessage("Чтобы не пропустить уведомления о новых сообщених, включите AutoStart")
//                        .setCancelable(false)
//                        .setNegativeButton(R.string.cancel, null)
//                        .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
//                    try {
//                        Intent intent = new Intent();
//                        intent.setComponent(new ComponentName(
//                                "com.miui.securitycenter",
//                                "com.miui.permcenter.autostart.AutoStartManagementActivity"
//                        ));
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//
//                    }
//                });
//                builder.show();
//
//        } else if (Build.BRAND.equals("Honor")) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(this));
//            builder.setTitle("Важно для Honor")
//                    .setMessage("Чтобы не пропустить уведомления о новых сообщених, включите AutoStart")
//                    .setCancelable(false)
//                    .setNegativeButton(R.string.cancel, null)
//                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
//                        try {
//                            Intent intent = new Intent();
//                            intent.setComponent(new ComponentName(
//                                    "com.huawei.systemmanager",
//                                    "com.huawei.systemmanager.optimize.process.ProtectActivity"
//                            ));
//                            startActivity(intent);
//                        } catch (ActivityNotFoundException e) {
//
//                        }
//                    });
//            builder.show();
//        }
//    }
}
