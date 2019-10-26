package kg.gruzovoz.history;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Order;

public interface HistoryContract {

    interface View extends BaseContract.BaseView {
        void showError();
        void setActiveFragmentOrders(List<Order> orders);
        void setCompletedFragmentOrders(List<Order> orders);
    }

    interface Presenter extends BaseContract.BasePresenter{
        void sortOrders(List<Order> orders);
    }

}
