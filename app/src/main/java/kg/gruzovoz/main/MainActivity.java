package kg.gruzovoz.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.history.HistoryFragment;
import kg.gruzovoz.models.Order;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    final Fragment ordersFragment = new OrdersFragment();
    final Fragment historyFragment = new HistoryFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = ordersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        presenter = new MainPresenter(this);
    }

    @Override
    public void logOut() {

    }

    @Override
    public void openHistoryScreen() {

    }

    @Override
    public void setOrders(List<Order> orders) {

    }
}
