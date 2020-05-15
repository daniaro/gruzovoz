package kg.gruzovoz.login;


import android.util.Log;

import org.jetbrains.annotations.NotNull;


import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.FirebaseUserData;
import kg.gruzovoz.models.Login;
import kg.gruzovoz.models.User;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView loginView;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);


    LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String phoneNumber, String password) {
        Login login = new Login(phoneNumber, password);
        Call<User> call = service.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    loginView.addAuthToken(String.format("%s", response.body().getToken()));
                    getFirebaseToken(String.format("%s", response.body().getToken()));

                } else {
                    if (loginView.isConnected()) {
                        if (response.code() == 401) {
                            loginView.notAuthorized();
                        }
                        else if (response.code() == 403){
                            loginView.showAlreadySignedToast();
                        }
                        else {
                            loginView.showLoginError();
                        }

                    } else {
                        loginView.showErrorToast();

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                if (loginView.isConnected()) {
                    loginView.showLoginError();
                } else {
                    loginView.showErrorToast();
                }
            }
        });

    }


    public void getFirebaseToken(String token){
        Call<FirebaseUserData> call = service.getFirebaseUserData("Token " + token);
        call.enqueue(new Callback<FirebaseUserData>() {
            @Override
            public void onResponse(@NotNull Call<FirebaseUserData> call, @NotNull Response<FirebaseUserData> response) {
                if (response.body() != null){
                    loginView.registerFirebaseUser(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<FirebaseUserData> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });

    }





}
