package kg.gruzovoz.login;

interface LoginContract {

    interface  LoginView{
       void showErrorToast();
       void addAuthToken(String authToken);
       void registerUser();
    }

    interface LoginPresenter{
         void login(String phoneNumber, String password);
    }
}
