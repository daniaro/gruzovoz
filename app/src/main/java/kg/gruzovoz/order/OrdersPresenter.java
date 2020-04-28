package kg.gruzovoz.order;

import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;

public class OrdersPresenter implements OrdersContract.Presenter {

    private OrdersContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }

//    @Override
//    public void populateOrders(){
//        Call<List<Results>> call = service.getAllOrders(BaseActivity.authToken);
//        call.enqueue(new Callback<List<Results>>() {
//            @Override
//            public void onResponse(@NotNull Call<List<Results>> call, @NotNull Response<List<Results>> response) {
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
//            public void onFailure(@NotNull Call<List<Results>> call, @NotNull Throwable t) {
//                view.showError();
//            }
//        });
//    }
}
