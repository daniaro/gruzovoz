package kg.gruzovoz.main;

import java.util.List;

import kg.gruzovoz.models.Order;

public class OrdersPresenter implements OrdersContract.Presenter {

    OrdersContract.View view;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }



    @Override
    public void populateOrders() {
        view.logOut();
    }

    @Override
    public void openDetailScreen(Order order) {

    }
}
