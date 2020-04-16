package kg.gruzovoz.user_page.history;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.Results;

public interface HistoryContract {

    interface View extends BaseContract.BaseView {
        void showError();
        void setOrders(List<Results> resultsList);
        void openDetailScreen(Results results);
        void showEmptyView();
        void hideProgressBar();
        void stopRefreshingOrders();
    }

    interface Presenter {
//        void populateOrders(boolean isDone);
    }
}
