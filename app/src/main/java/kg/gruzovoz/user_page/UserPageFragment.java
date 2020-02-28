package kg.gruzovoz.user_page;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.FixedTabsPagerAdapter;
import kg.gruzovoz.user_page.fragments.ActiveFragment;
import kg.gruzovoz.user_page.fragments.CompletedFragment;


public class UserPageFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActiveFragment activeFragment;
    private CompletedFragment completedFragment;
    private FixedTabsPagerAdapter adapter;
    private BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener;

    public UserPageFragment() {
        // required empty public constructor
    }

    public UserPageFragment(BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener) {
        this.onBaseOrderFinishedListener = onBaseOrderFinishedListener;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_page, container, false);
        Toolbar toolbar = root.findViewById(R.id.user_page_app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tabLayout = root.findViewById(R.id.tabs);
        viewPager = root.findViewById(R.id.viewPager);

        activeFragment = new ActiveFragment();
        activeFragment.setOnOrderFinishedListener(() -> onBaseOrderFinishedListener.onBaseOrderFinished());
        completedFragment = new CompletedFragment();

        adapter = new FixedTabsPagerAdapter(getChildFragmentManager());
            adapter.addFragment(activeFragment,"АКТИВНЫЕ");
        adapter.addFragment(completedFragment,"ЗАВЕРШЕННЫЕ");
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("АКТИВНЫЕ"));
        tabLayout.addTab(tabLayout.newTab().setText("ЗАВЕРШЕННЫЕ"));

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

//    @Override
    public void showConfirmLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_message));
        builder.setNegativeButton(R.string.cancel_order, null);
        builder.setPositiveButton(getString(R.string.action_logout), (dialog, which) -> {
            BaseActivity.authToken = null;
            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("authToken").apply();
            new Handler().post(() -> {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            });


        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                showConfirmLogoutDialog();
                return true;
        }
        return false;
    }
}




