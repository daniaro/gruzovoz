package kg.gruzovoz;

import java.util.List;

import kg.gruzovoz.models.Order;

public interface BaseContract {

    interface BaseView {
        void setOrders(List<Order> orders);
    }

    interface BasePresenter {
        void populateOrders();
    }

    interface OnItemClickListener {
        void onItemClick(Order order);
    }

    interface OnOrderFinishedListener {
        void onOrderFinished();
    }

    interface OnBaseOrderFinishedListener {
        void onBaseOrderFinished();
    }
}
