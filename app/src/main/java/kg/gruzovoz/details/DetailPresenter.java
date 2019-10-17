package kg.gruzovoz.details;

public class DetailPresenter implements DetailContract.DetailPresenter {

    DetailContract.DetailView view;

    public void CallActivityPresenter(DetailContract.DetailView  view){this.view = view;}

    @Override
    public String getPhoneNumber() {
        //get phone number from api , now let in be string var
        String phoneNumber = " ";
        return  phoneNumber;
    }
}
