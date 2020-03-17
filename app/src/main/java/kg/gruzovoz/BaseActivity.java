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

import kg.gruzovoz.chat.ChatFragment;
import kg.gruzovoz.user_page.UserPageFragment;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.main.OrdersFragment;

public class BaseActivity extends AppCompatActivity {

    Fragment ordersFragment = new OrdersFragment();
    Fragment chatFragment = new ChatFragment();
    Fragment userPageFragment = new UserPageFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    SharedPreferences sharedPreferences;
    public static String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext()
                .getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        authToken = "Token " + sharedPreferences.getString("authToken", null);
        if (sharedPreferences.getString("authToken", null) == null) {
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Log.e("authToken: ",authToken);

        userPageFragment = new UserPageFragment(() -> {
            Fragment fragment = fragmentManager.findFragmentByTag("3");
            final FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
        });

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
                                .replace(R.id.main_container, chatFragment,"2").commit();
                        return true;
                    case R.id.navigation_user_page:
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, userPageFragment, "3").commit();
                        return true;
                }
                return false;
            };


}
