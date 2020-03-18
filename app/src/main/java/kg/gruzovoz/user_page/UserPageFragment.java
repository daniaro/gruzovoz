package kg.gruzovoz.user_page;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.FixedTabsPagerAdapter;
import kg.gruzovoz.main.OrdersPresenter;
import kg.gruzovoz.models.UserPage;
import kg.gruzovoz.user_page.history.ActiveFragment;
import kg.gruzovoz.user_page.history.CompletedFragment;



public class UserPageFragment extends Fragment implements UserPageContract.View {

    private UserPageContract.Presenter presenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener;

    private TextView nameTextView;
    private TextView phoneNumberTextView;
    private TextView balanceTextView;
    private TextView carNameTextView;
    private TextView carNumberTextView;



    public UserPageFragment() {
        // required empty public constructor
    }

    public UserPageFragment(BaseContract.OnBaseOrderFinishedListener onBaseOrderFinishedListener) {
        this.onBaseOrderFinishedListener = onBaseOrderFinishedListener;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_page, container, false);

        UserPagePresenter presenter = new UserPagePresenter(this);

        initTabLayout(root);
        initTabLayoutSelection();

        // setHasOptionsMenu(true);

        LinearLayout logutLL = root.findViewById(R.id.logout);
        logutLL.setOnClickListener(e -> showConfirmLogoutDialog());

        nameTextView = root.findViewById(R.id.name_user_page);
        phoneNumberTextView = root.findViewById(R.id.phone_number_user_page);
        balanceTextView = root.findViewById(R.id.balance_user_page);
        carNameTextView = root.findViewById(R.id.car_name_user_page);
        carNumberTextView = root.findViewById(R.id.cane_number_user_page);

        presenter.getPersonalData();

        return root;
    }


    private void initTabLayout(View root){
        tabLayout = root.findViewById(R.id.tabs);
        viewPager = root.findViewById(R.id.viewPager);

        ActiveFragment activeFragment = new ActiveFragment();
        activeFragment.setOnOrderFinishedListener(() -> onBaseOrderFinishedListener.onBaseOrderFinished());
        CompletedFragment completedFragment = new CompletedFragment();

        FixedTabsPagerAdapter adapter = new FixedTabsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(activeFragment,"АКТИВНЫЕ");
        adapter.addFragment(completedFragment,"ЗАВЕРШЕННЫЕ");
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("АКТИВНЫЕ"));
        tabLayout.addTab(tabLayout.newTab().setText("ЗАВЕРШЕННЫЕ"));

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initTabLayoutSelection(){
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
    public void showConfirmLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.AlertDialogTheme);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void setAllData(UserPage user_page) {
        nameTextView.setText(user_page.getUser().getUsername());
        phoneNumberTextView.setText(user_page.getUser().getPhone_number());
        balanceTextView.setText(user_page.getBalance());
        carNameTextView.setText(user_page.getType_of_car() + "("+user_page.getCar_color()+")");
        carNumberTextView.setText(user_page.getCar_number());

    }





    @Override
    public void showError(){
        Toast.makeText(getContext(), getString(R.string.data_unavailable), Toast.LENGTH_LONG).show();
    }

}




