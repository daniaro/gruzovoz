package kg.gruzovoz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import kg.gruzovoz.chat.ChatFragment;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.order.OrdersFragment;
import kg.gruzovoz.user_page.UserPageFragment;

public class BaseActivity extends AppCompatActivity {

    Fragment ordersFragment = new OrdersFragment();
    Fragment chatFragment = new ChatFragment();
    Fragment userPageFragment = new UserPageFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    SharedPreferences sharedPreferences;
    public static String authToken;
    public static Long fbUserId;

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

        chatFragment = new ChatFragment(() -> {
            Fragment fragment = fragmentManager.findFragmentByTag("2");
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        });

        setPresence(Boolean.TRUE,ServerValue.TIMESTAMP);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.main_container, ordersFragment, "1").commit();
        }
    }

    //TODO: last seen
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
        setPresence(Boolean.FALSE,ServerValue.TIMESTAMP);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setPresence(Boolean.TRUE, ServerValue.TIMESTAMP);
//        setPresence(Boolean.TRUE, FieldValue.serverTimestamp());
    }

    public void setPresence(boolean online, Map<String, String> lastSeen)  {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference("presence");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                DatabaseReference con = myConnectionsRef.child(String.valueOf(fbUserId));
                if (connected) {
                    con.setValue(online);
                }
                if (!online){
                    Log.e("lastseen",lastSeen.toString());
                    con.setValue(lastSeen);
                }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.e("error onCancelled", "Listener was cancelled at .info/connected");
            }
        });
    }



}
