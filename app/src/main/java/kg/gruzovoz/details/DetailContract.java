package kg.gruzovoz.details;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.models.OrderDetail;

public interface DetailContract {

    interface DetailView {
        void setViewInfo(OrderDetail order);
        void showAcceptAlertDialog();
    }

    interface DetailPresenter {
        void populateInfo(long id, String authToken);
    }
}
