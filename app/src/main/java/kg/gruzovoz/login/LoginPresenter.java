package kg.gruzovoz.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;


import kg.gruzovoz.models.Login;
import kg.gruzovoz.models.User;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView loginView;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);
    DatabaseReference reference;

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
//                    registerFireBaseUser(String.format("%s", response.body().getToken()));

                } else {
                    if (loginView.isConnected()) {
                        loginView.showLoginError();
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


//    private void registerFireBaseUser(String authToken){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.signInWithCustomToken(authToken)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.i(TAG, "signInWithCustomToken:success",task.getException());
//
//                        FirebaseUser user = auth.getCurrentUser();
//                        Log.i(TAG, "registerFireBaseUser: " + user.getPhoneNumber());
//                        Log.i(TAG, "registerFireBaseUser: " + user);
////                        updateUI(user);
//                    } else {
//                        Log.w(TAG, "signInWithCustomToken:failure", task.getException());
//                        Log.w(TAG, "signInWithCustomToken:failure:"+ authToken);
//                        //Toast.makeText(CustomAuthActivity.this, "Authentication failed.",
//                                //Toast.LENGTH_SHORT).show();
//                        //updateUI(null);
//                    }
//                });
//    }



}
