package kg.gruzovoz.details;


import android.content.Intent;

import org.jetbrains.annotations.NotNull;


import java.util.Objects;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.models.AcceptOrder;
import kg.gruzovoz.models.FinishOrder;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kg.gruzovoz.BaseActivity.authToken;

public class DetailPresenter implements DetailContract.DetailPresenter {

    private DetailContract.DetailView view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);
    private final AcceptOrder acceptOrder = new AcceptOrder();
    private final FinishOrder finishOrder = new FinishOrder();

    DetailPresenter(DetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void acceptOrder(long id) {
        Call<Void> call = service.acceptOrder(id, BaseActivity.authToken, acceptOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.code() == 401) {
                    view.notAuthorized();
                }
                else if (response.code() == 402){
                    view.showAcceptError();
                }
                else if (response.code() == 403) {
                    view.showBalanceError();
                }
                else if (response.code() == 400) {
                    view.showCarTypeError();
                }

                else {
                    view.startCallActivity();
                }

            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                view.showCarTypeError();
                t.getMessage();
            }

        });

    }

    @Override
    public void finishOrder(long id) {
        Call<Void> call = service.finishOrder(id, authToken, finishOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.code() == 401) {
                    view.notAuthorized();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
            }
        });
    }
}
