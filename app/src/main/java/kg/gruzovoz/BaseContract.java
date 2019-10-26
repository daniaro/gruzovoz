package kg.gruzovoz;

import java.util.List;

import kg.gruzovoz.models.Order;

public interface BaseContract {

    interface BaseView {
        void setOrders(List<Order> orders);
    }

    interface BasePresenter {
        void populateOrders();
        //void openDetailScreen(Order order);

    }
    interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
