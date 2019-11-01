package kg.gruzovoz.details;

public class CallPresenter implements CallContract.CallPresenter {

    CallContract.CallView view;

    public CallPresenter(CallContract.CallView  view){this.view = view;}

    @Override
    public void parsePhoneNumber(String phoneNumber) {
        view.setPhoneNumber(String.format("%s %s %s %s %s",
                phoneNumber.substring(0, 4),
                phoneNumber.substring(4, 7),
                phoneNumber.substring(7, 9),
                phoneNumber.substring(7, 9),
                phoneNumber.substring(9, 11)));
    }
}
