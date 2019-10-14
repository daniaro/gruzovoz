package kg.gruzovoz.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kg.gruzovoz.models.Order;

public class OrdersPresenter implements OrdersContract.Presenter {

    OrdersContract.View view;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }

    @Override
    public void populateOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Order order = new Order(i, "Спринтер", "Сегодня", "Байтик Баатыра 70", "Байтик Баатыра 110а", 16487, 10, "Доступно", "Хочу чтобы все четко доставили");
            ordersList.add(order);
            Log.e("log", order.toString());
        }

        view.setOrders(ordersList);
    }

    @Override
    public void openDetailScreen(Order order) {
        view.showDetailScreen(order.getId());
    }
}
