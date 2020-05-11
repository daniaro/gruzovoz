package kg.gruzovoz.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.paging.PaginationListener;
import kg.gruzovoz.details.DetailActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.models.Results;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static kg.gruzovoz.paging.PaginationListener.PAGE_START;

public class OrdersFragment extends Fragment implements OrdersContract.View {

    private OrdersContract.Presenter presenter;
    private OrdersAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;


    private int currentPage = PAGE_START;
    private static final int TOTAL_ITEMS_TO_LOAD = 8;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;



    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        Toolbar toolbar = root.findViewById(R.id.app_bar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        progressBar = root.findViewById(R.id.indeterminateBar);
        emptyView = root.findViewById(R.id.empty_view);
        setHasOptionsMenu(true);
        initSwipeRefreshLayout(root);
        initRecyclerViewWithAdapter(root);
        getOrdersCount(root);
        return root;
    }

    private void initSwipeRefreshLayout(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.rippleColor), getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(() ->{
            itemCount = 0;
            currentPage = PAGE_START;
            isLastPage = false;
            adapter.clear();
            populateOrders();

        });
    }


    private void initRecyclerViewWithAdapter(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext()); //getActivity
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            adapter = new OrdersAdapter(new Order(),new ArrayList<>(), this::showDetailScreen);
            populateOrders();
        }
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                populateOrders();
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
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }


    @Override
    public void showDetailScreen(Results results) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("order", results);
        startActivityForResult(intent, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            populateOrders();

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
        adapter.setValues(null);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void setOrders(List<Results> results) {
        adapter.setValues(results);
    }


    private void getOrdersCount(View root) {
        Log.d(TAG, "getOrdersCount");
        FirebaseFirestore.getInstance().collection("orders")
                .addSnapshotListener((snapshots, e) -> {
                    try {
                        assert snapshots != null;
                        for (DocumentChange change : snapshots.getDocumentChanges()) {
                            switch (change.getType()) {
                                case ADDED:
                                    break;
                                case REMOVED:
                                    break;
                                case MODIFIED:
                                    Map<String, Object> map = change.getDocument().getData();
                                    map.get("actives_count");
                                    adapter.clear();
                                    populateOrders();
                                    Log.d(TAG, " MODIFIED Value is: " + map.get("actives_count"));
                                    break;
                            }
                        }
                    }
                    catch (NullPointerException ex){
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                });
    }


    private void populateOrders () {
            CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);
            final Order[] orders = new Order[1];
            Call<Order> call = service.getAllOrders(BaseActivity.authToken,currentPage );
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(@NotNull Call<Order> call, @NotNull Response<Order> response) {
                    orders[0] = response.body();
                    if (response.body() != null && response.body().getResults().size() > 0) {
                        if (currentPage != PAGE_START) adapter.removeLoading();
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
                    } else {
                        showEmptyView();
                        stopRefreshingOrders();

                    }
                }

                @Override
                public void onFailure(@NotNull Call<Order> call, @NotNull Throwable t) {
                    showError();
                }
            });
        }


}

