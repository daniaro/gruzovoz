package kg.gruzovoz.user_page;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.UserPage;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPagePresenter implements UserPageContract.Presenter {

    private UserPageContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    UserPagePresenter(UserPageContract.View view) {
        this.view = view;
    }

    @Override
    public void getPersonalData() {
        Call<List<UserPage>> call = service.getPersonalData(BaseActivity.authToken);
        call.enqueue(new Callback<List<UserPage>>() {
            @Override
            public void onResponse(@NotNull Call<List<UserPage>> call, @NotNull Response<List<UserPage>> response) {
//                    List<UserPage> userPageList = response.body();
                    view.setAllData(response.body().get(0));
                    Log.e("onResponse", response.message());

            }

            @Override
            public void onFailure(@NotNull Call<List<UserPage>> call, @NotNull Throwable t) {
                Log.e("onFailure", Objects.requireNonNull(t.getMessage()));

            }
        });
    }
}
