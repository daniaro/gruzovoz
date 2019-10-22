package kg.gruzovoz.login;

interface LoginContract {

    interface  LoginView{
       void errorToast();

    }

    interface LoginPresenter{
         void login();
//         void getAllOrders();
    }
}
