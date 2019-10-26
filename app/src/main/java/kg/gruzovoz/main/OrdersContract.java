package kg.gruzovoz.main;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Order;

public interface OrdersContract {

    interface View extends BaseContract.BaseView{
        void logOut();
        void showDetailScreen(Order order);
        void stopRefreshingOrders();
        void showError();
        void showConfirmLogoutDialog();

    }

    interface Presenter extends BaseContract.BasePresenter{
    }
}
