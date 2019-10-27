package kg.gruzovoz.details;

import kg.gruzovoz.models.Order;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
        void showAcceptAlertDialog();
        void startCallActivity();
    }

    interface DetailPresenter {
        void acceptOrder(long id);
        void finishOrder(long id);
    }
}
