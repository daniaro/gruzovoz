package kg.gruzovoz.history;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.FixedTabsPagerAdapter;
import kg.gruzovoz.history.fragments.ActiveFragment;
import kg.gruzovoz.history.fragments.CompletedFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment  {


    public TabLayout tabLayout;
    public ViewPager viewPager;


    FixedTabsPagerAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        tabLayout = root.findViewById(R.id.tabs);
        viewPager = root.findViewById(R.id.viewPager);

        adapter = new FixedTabsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveFragment(),"Активные");
        adapter.addFragment(new CompletedFragment(),"Завершенные");
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Активные"));
        tabLayout.addTab(tabLayout.newTab().setText("Завершенные"));

        tabLayout.setupWithViewPager(viewPager);
        initTablayoutSelection();
        return root;
    }

    public void initTablayoutSelection(){
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




