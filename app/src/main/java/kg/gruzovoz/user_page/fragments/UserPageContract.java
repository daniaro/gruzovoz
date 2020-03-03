package kg.gruzovoz.user_page.fragments;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Order;

public interface UserPageContract {

    interface View extends BaseContract.BaseView {
        void showError();
        void setOrders(List<Order> orderList);
        void openDetailScreen(Order order);
        void showEmptyView();
        void hideProgressBar();
        void stopRefreshingOrders();


    }

    interface Presenter {
        void populateOrders(boolean isDone);
    }
}
