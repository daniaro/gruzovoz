package kg.gruzovoz.history.fragments;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    HistoryContract.View view;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    public HistoryPresenter(ActiveFragment activeFragment) {
        this.view = activeFragment;
    }

    public HistoryPresenter(CompletedFragment finishedFragment) {
        this.view = finishedFragment  ;
    }



    @Override
    public void populateOrders() {
        Call<List<Order>> call = service.getAllOrders("Token 7b86ca9dc2c619467f92d9e084c6a91fa2daa5d7");
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Log.i(getClass().getSimpleName(), "id: " + response.body().get(i).getId() + "address: " + response.body().get(i).getAddress());
                }
                Collections.reverse(response.body());
                view.setOrders(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                view.showError();
            }
        });

    }


    @Override
    public void openDetailScreen(Order order) {

    }
}
