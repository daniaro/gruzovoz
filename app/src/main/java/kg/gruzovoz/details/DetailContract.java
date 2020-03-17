package kg.gruzovoz.details;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
        void showAcceptAlertDialog();
        void startCallActivity();

        void showError();
        void showBalanceError();
        void showConfirmFinishAlertDialog();
    }

    interface DetailPresenter {
        void acceptOrder(long id);
        void finishOrder(long id);
    }
}
