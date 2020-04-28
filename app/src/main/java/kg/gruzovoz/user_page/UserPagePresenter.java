package kg.gruzovoz.user_page;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.chat.messages.MessagesContract;
import kg.gruzovoz.models.UserPage;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserPagePresenter implements UserPageContract.Presenter {

    private UserPageContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    UserPagePresenter(UserPageContract.View view) {
        this.view = view;
    }

    @Override
    public void getPersonalData() {
        Call<UserPage> call = service.getPersonalData(BaseActivity.authToken);
        call.enqueue(new Callback<UserPage>() {
            @Override
            public void onResponse(@NotNull Call<UserPage> call, @NotNull Response<UserPage> response) {
                if (response.body() != null) {
                    view.setAllData(response.body());
                } else {

                }
            }
            @Override
            public void onFailure(@NotNull Call<UserPage> call, @NotNull Throwable t) {
                view.showError();
            }
        });

    }

    @Override
    public void logout(){
        Call<Void> call = service.logout(BaseActivity.authToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                Log.i(" onResponse logout",response.message());
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.i("onFailure logout",t.getMessage());

            }
        });
    }




}
