package kg.gruzovoz.user_page.history;


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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.adapters.PaginationListener;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.models.Results;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kg.gruzovoz.adapters.PaginationListener.PAGE_START;

public class CompletedFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;


    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;


    public CompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_completed, container, false);

        progressBar = root.findViewById(R.id.progressBar_historyCompleted);
        emptyView = root.findViewById(R.id.emptyView_historyCompleted);
        initRecyclerViewWithAdapter(root);

        populateOrders(true);
        return root;

    }

    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerViewCompleted);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new OrdersAdapter(new Order(), results -> openDetailScreen(results));


        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        presenter = new HistoryPresenter(this);

        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                populateOrders(true);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.completed_orders_unavailable), Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOrders(List<Results> resultsList) {
        adapter.setValues(resultsList);
    }

    @Override
    public void openDetailScreen(Results results) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("order", results);
        startActivityForResult(intent, 102);
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
    public void stopRefreshingOrders() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 101) {
            populateOrders(true);
        }
    }

    public void populateOrders(boolean isDone) {
        Call<Order> call = service.getOrdersHistory(BaseActivity.authToken, isDone);
        final Order[] orders = new Order[1];
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NotNull Call<Order> call, @NotNull Response<Order> response) {
                orders[0] = response.body();
                if (response.body() != null && response.body().getResults().size() >0) {
                    if (currentPage != PAGE_START)
                        adapter.removeLoading();
                    Collections.reverse(response.body().getResults());
                    adapter.addItems(orders[0]);
                    // setOrders(response.body().getResults());
                    stopRefreshingOrders();
                    hideProgressBar();

                    if (response.body().getNext() != null) {
                        adapter.addLoading();
                    } else {
                        isLastPage = true;
                    }
                    isLoading = false;
                }else {
                    showEmptyView();
                }
                stopRefreshingOrders();
            }

            @Override
            public void onFailure(@NotNull Call<Order> call, @NotNull Throwable t) {
                showError();
            }
        });
    }
}
