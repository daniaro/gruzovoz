package kg.gruzovoz.details;

public interface CallContract {

    interface CallView {
        void setPhoneNumber(String phoneNumber);
    }

    interface CallPresenter {
        void parsePhoneNumber(String phoneNumber);
    }
}
