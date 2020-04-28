package kg.gruzovoz.order;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Results;

public interface OrdersContract {

    interface View extends BaseContract.BaseView{
        void hideProgressBar();
        void showDetailScreen(Results results);
        void stopRefreshingOrders();
        void showError();
        void showEmptyView();

    }

    interface Presenter extends BaseContract.BasePresenter {
    }
}
