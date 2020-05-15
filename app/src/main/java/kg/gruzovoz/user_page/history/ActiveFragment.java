package kg.gruzovoz.user_page.history;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.paging.PaginationListener;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.models.Results;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kg.gruzovoz.paging.PaginationListener.PAGE_START;


public class ActiveFragment extends Fragment implements HistoryContract.View{

    private HistoryContract.Presenter presenter;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);


    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences.Editor editor;


    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    private BaseContract.OnOrderFinishedListener onOrderFinishedListener;

    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_active, container, false);

        progressBar = root.findViewById(R.id.progressBar_historyActive);
        emptyView = root.findViewById(R.id.emptyView_historyActive);

        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        initSwipeRefreshLayout(root);
        initRecyclerViewWithAdapter(root);

        return root;
    }

    private void initSwipeRefreshLayout(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout_historyActive);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.rippleColor), getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(() ->{
            currentPage = PAGE_START;
            isLastPage = false;
            adapter.clear();
            populateOrders(false);
        });
    }

    @Override
    public void stopRefreshingOrders() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setOnOrderFinishedListener(BaseContract.OnOrderFinishedListener onOrderFinishedListener) {
        this.onOrderFinishedListener = onOrderFinishedListener;
    }

    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerViewActive);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new OrdersAdapter(new Order(),new ArrayList<>(), this::openDetailScreen);

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter = new HistoryPresenter(this);
        populateOrders(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                populateOrders(false);
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
        Toast.makeText(getContext(), getString(R.string.active_orders_unavailable), Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
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
            populateOrders(false);

            onOrderFinishedListener.onOrderFinished();
        }
    }

    private void populateOrders(boolean isDone) {
        Call<Order> call = service.getOrdersHistory(BaseActivity.authToken, isDone,currentPage);
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
                    stopRefreshingOrders();
                    hideProgressBar();

                    if (response.body().getNext() != null) {
                        adapter.addLoading();
                    } else {
                        isLastPage = true;
                    }
                    isLoading = false;

                }else if (response.code() == 401) {
                    editor.putString("authToken", null).commit();
                    Objects.requireNonNull(getActivity()).recreate();

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
