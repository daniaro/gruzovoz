package kg.gruzovoz.login;

import kg.gruzovoz.main.OrdersPresenter;
import kg.gruzovoz.models.Login;
import kg.gruzovoz.models.User;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.LoginPresenter {

    OrdersPresenter ordersPresenter;
    LoginContract.LoginView loginView;
    public String token;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);


    public LoginPresenter(LoginContract.LoginView loginView){
        this.loginView = loginView;
    }


    @Override
    public void login() {

        Login login = new Login("+996551234567","trueadminpass");
        Call<User> call =  service.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    loginView.addAuthToken(response.body().getToken());

//                    Toast.makeText(LoginPresenter., response.body().getToken(), Toast.LENGTH_SHORT).show();

                } else {
                    loginView.showErrorToast();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                   loginView.showErrorToast();
            }
        });

    }

//    public void getAllOrders(){
//        Call<List<Order>> call = service.getAllOrders(token);
//        call.enqueue(new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                if (response.isSuccessful()) {
//                    token = response.body().toString();
////                    Toast.makeText(LoginPresenter., response.body().getToken(), Toast.LENGTH_SHORT).show();
//
//                }else {
//                    loginView.errorToast();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                loginView.errorToast();
//            }
//        });
//
//    }
}
