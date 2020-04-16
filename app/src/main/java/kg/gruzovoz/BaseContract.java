package kg.gruzovoz;

import java.util.List;

import kg.gruzovoz.models.Results;

public interface BaseContract {

    interface BaseView {
        void setOrders(List<Results> results);
    }

    interface BasePresenter {
//        void populateOrders();
    }

    interface OnItemClickListener {
        void onItemClick(Results results);
    }

    interface OnOrderFinishedListener {
        void onOrderFinished();
    }

    interface OnBaseOrderFinishedListener {
        void onBaseOrderFinished();
    }
}
