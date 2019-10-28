package kg.gruzovoz.details;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
        void showAcceptAlertDialog();
        void startCallActivity();

    }

    interface DetailPresenter {
        String getPhoneNumber();
        void acceptOrder(long id);
        void finishOrder(long id);
    }
}
