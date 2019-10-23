package kg.gruzovoz.details;

public class CallPresenter implements CallContract.CallPresenter {

    CallContract.CallView view;

    public void CallActivityPresenter(CallContract.CallView  view){this.view = view;}

    @Override
    public String getPhoneNumber() {
        //get phone number from api , now let in be string var
        String phoneNumber = " ";
        return  phoneNumber;
    }


}
