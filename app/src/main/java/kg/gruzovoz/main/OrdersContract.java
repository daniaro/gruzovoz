package kg.gruzovoz.main;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.adapters.OrdersAdapter;
import kg.gruzovoz.models.Order;

public interface OrdersContract {

    interface View extends BaseContract.BaseView{
        void hideProgressBar();
        void showDetailScreen(Order order);
        void stopRefreshingOrders();
        void showError();
//        void showConfirmLogoutDialog();
        void showEmptyView();

    }

    interface Presenter extends BaseContract.BasePresenter {
    }
}
