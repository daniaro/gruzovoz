package kg.gruzovoz.history;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.history.fragments.ActiveFragment;
import kg.gruzovoz.history.fragments.CompletedFragment;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    HistoryContract.View view;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
    }

    @Override
    public void populateOrders() {
        Call<List<Order>> call = service.getOrdersHistory(BaseActivity.authToken);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                sortOrders(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    @Override
    public void sortOrders(List<Order> orders) {
        List<Order> ordersActive = new ArrayList<>();
        List<Order> ordersDone = new ArrayList<>();
        for (Order order : orders) {
            if (order.isDone()) {
                ordersDone.add(order);
            } else {
                ordersActive.add(order);
            }
        }
        view.setActiveFragmentOrders(ordersActive);
        view.setCompletedFragmentOrders(ordersDone);
    }
}
