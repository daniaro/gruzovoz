package kg.gruzovoz.main;

import java.util.List;

import kg.gruzovoz.models.Order;

public interface MainContract {

    interface View {
        void logOut();
        void openHistoryScreen();
        void setoOrders(List<Order> orders);

    }

    interface Presenter {
        void populateOrders();
        void openDetailScreen(Order order);
    }

    interface OnItemClickListener {
        void onItemClick(Order order);
    }
}
