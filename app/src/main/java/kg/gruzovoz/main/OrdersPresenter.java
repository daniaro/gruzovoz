package kg.gruzovoz.main;

import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenter implements OrdersContract.Presenter {

    OrdersContract.View view;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);
    Order order;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }

//    public void populateOrdersForTipe(){
//        Call<List<Order>> call = service.getOrdersForType(BaseActivity.authToken);
//        call.enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                if (response.body() != null && response.body().size() > 0) {
//                    view.setOrders(response.body());
//                    view.hideProgressBar();
//                } else {
//                    view.setOrders(response.body());
//                    view.showEmptyView();
//                }
//                view.stopRefreshingOrders();
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                view.showError();
//            }
//        });
//    }

    @Override
    public void populateOrders() {
//        switch (order.getCargoType()) {
//            case 1:
//                populateOrdersForTipe();
//                break;
//            case 2:
//                populateOrdersForTipe();
//                break;
//            case 3:
//                populateOrdersForTipe();
//                break;
//
//        }


        Call<List<Order>> call = service.getAllOrders(BaseActivity.authToken);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    view.setOrders(response.body());
                    view.hideProgressBar();
                } else {
                    view.setOrders(response.body());
                    view.showEmptyView();
                }
                view.stopRefreshingOrders();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                view.showError();
            }
        });
    }
}
