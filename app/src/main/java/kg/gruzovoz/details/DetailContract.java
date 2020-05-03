package kg.gruzovoz.details;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
        void showAcceptAlertDialog();
        void startCallActivity();

        void showCarTypeError();
        void showBalanceError();
        void showConfirmFinishAlertDialog();

        void showAcceptError();
    }

    interface DetailPresenter {
        void acceptOrder(long id);
        void finishOrder(long id);
    }
}
