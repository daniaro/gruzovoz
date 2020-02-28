package kg.gruzovoz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kg.gruzovoz.chat.ChatFragment;
import kg.gruzovoz.user_page.UserPageFragment;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.main.OrdersFragment;

public class BaseActivity extends AppCompatActivity {

    Fragment ordersFragment = new OrdersFragment();
    Fragment chatFragment = new ChatFragment();
    Fragment historyFragment;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    SharedPreferences sharedPreferences;
    public static String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        authToken = "Token " + sharedPreferences.getString("authToken", null);
        if (sharedPreferences.getString("authToken", null) == null) {
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // detach and reattach UserPageFragment when the user accepts an order
        historyFragment = new UserPageFragment(() -> {
            Fragment fragment = fragmentManager.findFragmentByTag("3");
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        });

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().add(R.id.main_container, ordersFragment, "1").commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_orders:
                            fragmentManager.beginTransaction().replace(R.id.main_container, ordersFragment).commit();
                            return true;
                        case R.id.navigation_chat:
                            fragmentManager.beginTransaction().replace(R.id.main_container, chatFragment,"2").commit();
                            return true;
                        case R.id.navigation_history:
                            fragmentManager.beginTransaction().replace(R.id.main_container, historyFragment, "3").commit();
                            return true;
                    }
                    return false;
                }
            };


}
