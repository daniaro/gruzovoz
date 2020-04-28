package kg.gruzovoz.details.call;

public interface CallContract {

    interface CallView {
        void setPhoneNumber(String phoneNumber);
    }

    interface CallPresenter {
        void parsePhoneNumber(String phoneNumber);
    }
}
