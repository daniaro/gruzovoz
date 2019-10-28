package kg.gruzovoz.history.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment implements HistoryContract.View{

    private HistoryContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private ProgressBar progressBar;

    private BaseContract.OnOrderFinishedListener onOrderFinishedListener;

    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_active, container, false);

        progressBar = root.findViewById(R.id.progressBar_historyActive);
        emptyView = root.findViewById(R.id.emptyView_historyActive);
        initRecyclerViewWithAdapter(root);

        return root;
    }

    public void setOnOrderFinishedListener(BaseContract.OnOrderFinishedListener onOrderFinishedListener) {
        this.onOrderFinishedListener = onOrderFinishedListener;
    }

    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerViewActive);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new OrdersAdapter(new BaseContract.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                openDetailScreen(order);
            }
        });

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter = new HistoryPresenter(this);
        presenter.populateOrders(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.active_orders_unavailable), Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }

    @Override
    public void setOrders(List<Order> orderList) {
        adapter.setValues(orderList);
    }

    @Override
    public void openDetailScreen(Order order) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("order", order);
        startActivityForResult(intent, 101);
    }

    @Override
    public void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            presenter.populateOrders(false);
            onOrderFinishedListener.onOrderFinished();
        }
    }
}
