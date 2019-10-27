package kg.gruzovoz.main;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements OrdersContract.View {

    private OrdersContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyView;
    ProgressBar progressBar;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        Toolbar toolbar = root.findViewById(R.id.app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        progressBar = root.findViewById(R.id.indeterminateBar);
        emptyView = root.findViewById(R.id.empty_view);
        setHasOptionsMenu(true);
        initSwipeRefreshLayout(root);
        initRecyclerViewWithAdapter(root);
        return root;
    }

    private void initSwipeRefreshLayout(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.rippleColor), getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.populateOrders();
            }
        });
    }

    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            adapter = new OrdersAdapter(new BaseContract.OnItemClickListener() {
                @Override
                public void onItemClick(Order order) {
                    showDetailScreen(order);
                }
            });
            presenter = new OrdersPresenter(this);
            presenter.populateOrders();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showDetailScreen(Order order) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("order", order);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            presenter.populateOrders();
        }
    }

    @Override
    public void stopRefreshingOrders() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.orders_unavailable), Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showConfirmLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.logout_title));
        builder.setMessage(getString(R.string.logout_message));
        builder.setNegativeButton(R.string.cancel_order, null);
        builder.setPositiveButton(getString(R.string.action_logout), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseActivity.authToken = null;
                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("authToken").apply();
                String token = sharedPreferences.getString("authToken", "haha");
                Intent intent = new Intent(getContext(), BaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOrders(List<Order> orders) {
        adapter.setValues(orders);
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

