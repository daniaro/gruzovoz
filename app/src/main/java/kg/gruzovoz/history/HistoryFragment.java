package kg.gruzovoz.history;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.FixedTabsPagerAdapter;
import kg.gruzovoz.history.fragments.ActiveFragment;
import kg.gruzovoz.history.fragments.CompletedFragment;
import kg.gruzovoz.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActiveFragment activeFragment;
    private CompletedFragment completedFragment;
    private FixedTabsPagerAdapter adapter;

    private HistoryContract.Presenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        tabLayout = root.findViewById(R.id.tabs);
        viewPager = root.findViewById(R.id.viewPager);
        activeFragment = new ActiveFragment();
        completedFragment = new CompletedFragment();

        adapter = new FixedTabsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(activeFragment,"Активные");
        adapter.addFragment(completedFragment,"Завершенные");
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Активные"));
        tabLayout.addTab(tabLayout.newTab().setText("Завершенные"));

        tabLayout.setupWithViewPager(viewPager);
        initTabLayoutSelection();

        presenter = new HistoryPresenter(this);
        return root;
    }

    public void initTabLayoutSelection(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void showError() {

    }

    @Override
    public void setActiveFragmentOrders(List<Order> orders) {

    }

    @Override
    public void setCompletedFragmentOrders(List<Order> orders) {

    }

    @Override
    public void setOrders(List<Order> orders) {

    }
}




