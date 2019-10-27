package kg.gruzovoz.details;

import android.util.Log;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.AcceptOrder;
import kg.gruzovoz.models.FinishOrder;
import kg.gruzovoz.models.Order;
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

    public DetailPresenter(DetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void acceptOrder(long id) {
        Call<Void> call = service.acceptOrder(id, BaseActivity.authToken, acceptOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(DetailPresenter.class.getName(), "Заказ успешно принят");
                view.startCallActivity();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(DetailPresenter.class.getName(), "Невозможно принять заказ");
            }
        });
    }

    @Override
    public void finishOrder(long id) {
        Call<Void> call = service.finishOrder(id, authToken, finishOrder);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 206) {
                    Log.e("TAG", "Order finished");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
