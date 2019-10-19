package kg.gruzovoz.details;

public class CallActivityPresenter implements CallActivityContract.CallPresenter {

    CallActivityContract.CallView view;

    public void CallActivityPresenter(CallActivityContract.CallView  view){this.view = view;}

    @Override
    public String getPhoneNumber() {
        //get phone number from api , now let in be string var
        String phoneNumber = " ";
        return  phoneNumber;
    }


}
