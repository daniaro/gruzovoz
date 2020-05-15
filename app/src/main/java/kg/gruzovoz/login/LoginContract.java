package kg.gruzovoz.login;

import kg.gruzovoz.models.FirebaseUserData;

interface LoginContract {

    interface  LoginView{
        void showErrorToast();
        void showLoginError();
        void addAuthToken(String authToken);
        void registerFirebaseUser(FirebaseUserData firebaseUserToken);
        boolean isConnected();
        void showAlreadySignedToast();

        void notAuthorized();
    }

    interface LoginPresenter{
        void login(String phoneNumber, String password);
        void getFirebaseToken(String token);
    }
}
