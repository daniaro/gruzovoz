package kg.gruzovoz.details;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
        void startCallActivity();
        void showAcceptAlertDialog();
        void showConfirmFinishAlertDialog();
        void showCarTypeError();
        void showBalanceError();
        void showAcceptError();
        void notAuthorized();

    }

    interface DetailPresenter {
        void acceptOrder(long id);
        void finishOrder(long id);
    }
}
