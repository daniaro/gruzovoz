package kg.gruzovoz.user_page.history;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    HistoryPresenter(HistoryContract.View view) {
        this.view = view;
    }

    @Override
    public void populateOrders(boolean isDone) {
        Call<List<Order>> call = service.getOrdersHistory(BaseActivity.authToken, isDone);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NotNull Call<List<Order>> call, @NotNull Response<List<Order>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    Collections.reverse(response.body());
                    view.setOrders(response.body());
                    view.hideProgressBar();
                } else {
                    view.showEmptyView();
                    view.setOrders(response.body());
                }
                view.stopRefreshingOrders();
            }

            @Override
            public void onFailure(@NotNull Call<List<Order>> call, @NotNull Throwable t) {
                view.showError();
            }
        });
    }

}
