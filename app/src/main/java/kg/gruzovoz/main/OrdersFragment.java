package kg.gruzovoz.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements OrdersContract.View{

    private OrdersContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        initSwipeRefreshLayout(root);
        initRecyclerViewWithAdapter(root);

        presenter = new OrdersPresenter(this);
        presenter.populateOrders();



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

        adapter = new OrdersAdapter(new BaseContract.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                presenter.openDetailScreen(order);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void logOut() {
        // TODO to implement the log out feature
    }

    @Override
    public void showDetailScreen(long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void stopRefreshingOrders() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setOrders(List<Order> orders) {
        adapter.setValues(orders);
    }
}
