package kg.gruzovoz.history.fragments;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Order;

public interface HistoryContract {

    interface View extends BaseContract.BaseView {
        void showError();
        void setOrders(List<Order> orderList);
        void openDetailScreen(Order order);
        void showEmptyView();
        void hideProgressBar();

    }

    interface Presenter {
        void populateOrders(boolean isDone);
    }
}
