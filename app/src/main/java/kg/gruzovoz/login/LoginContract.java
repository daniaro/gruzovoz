package kg.gruzovoz.login;

import kg.gruzovoz.models.FirebaseUserToken;

interface LoginContract {

    interface  LoginView{
       void showErrorToast();
       void showLoginError();
       void addAuthToken(String authToken);
       void setDataForFirebaseToken(FirebaseUserToken firebaseUserToken);
       boolean isConnected();
    }

    interface LoginPresenter{
         void login(String phoneNumber, String password);
         void getFirebaseToken(String token);
    }
}
