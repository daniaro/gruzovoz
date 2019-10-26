package kg.gruzovoz.login;

interface LoginContract {

    interface  LoginView{
       void showErrorToast();
       void showLoginError();
       void addAuthToken(String authToken);
       void registerUser();
       boolean isConnected();
    }

    interface LoginPresenter{
         void login(String phoneNumber, String password);
    }
}
