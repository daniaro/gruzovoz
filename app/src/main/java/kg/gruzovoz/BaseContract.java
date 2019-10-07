package kg.gruzovoz;

import java.util.List;

import kg.gruzovoz.models.Order;

public interface BaseContract {

    interface BaseView {
        void setOrders(List<Order> orders);
    }

    interface BasePresenter {
        void populateOrders(List<Order> orders);
        void openDetailScreen(Order order);

    }
    interface onItemClickListener {
        void onItemClick(Order order);
    }
}
