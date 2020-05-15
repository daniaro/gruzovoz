package kg.gruzovoz.order;

import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;

public class OrdersPresenter implements OrdersContract.Presenter {

    private OrdersContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }
}
