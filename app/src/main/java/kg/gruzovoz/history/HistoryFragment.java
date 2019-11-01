package kg.gruzovoz.history;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.FixedTabsPagerAdapter;
import kg.gruzovoz.history.fragments.ActiveFragment;
import kg.gruzovoz.history.fragments.CompletedFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActiveFragment activeFragment;
    private CompletedFragment completedFragment;
    private FixedTabsPagerAdapter adapter;
    private BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener;

    public HistoryFragment(BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener) {
        this.onBaseOrderFinishedListener = onBaseOrderFinishedListener;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        Toolbar toolbar = root.findViewById(R.id.history_app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout = root.findViewById(R.id.tabs);

        viewPager = root.findViewById(R.id.viewPager);

        activeFragment = new ActiveFragment();
        activeFragment.setOnOrderFinishedListener(new BaseContract.OnOrderFinishedListener() {
            @Override
            public void onOrderFinished() {
                onBaseOrderFinishedListener.onBaseOrderFinished();
            }
        });
        completedFragment = new CompletedFragment();

        adapter = new FixedTabsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(activeFragment,"Активные");
        adapter.addFragment(completedFragment,"Завершенные");
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Активные"));
        tabLayout.addTab(tabLayout.newTab().setText("Завершенные"));

        tabLayout.setupWithViewPager(viewPager);
        initTabLayoutSelection();

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
}




