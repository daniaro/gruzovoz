package kg.gruzovoz.history.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.models.Order;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends Fragment implements HistoryContract.View {
    private HistoryContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_completed, container, false);

        presenter = new HistoryPresenter(this);
        presenter.populateOrders();
        initRecyclerViewWithAdapter(root);
        return root;

    }
    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerViewCompleted);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new OrdersAdapter(new BaseContract.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                // TODO implement this
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }


    @Override
    public void setOrders(List<Order> orders) {
        adapter.setValues(orders);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.orders_unavailable), Toast.LENGTH_LONG).show();
    }
}
