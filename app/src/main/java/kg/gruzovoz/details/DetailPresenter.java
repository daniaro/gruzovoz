package kg.gruzovoz.details;

import android.util.Log;

import kg.gruzovoz.models.AcceptOrder;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.DetailPresenter {

    private long id;
    private DetailContract.DetailView view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);
    private Order order;
    private final AcceptOrder acceptOrder = new AcceptOrder();

    public DetailPresenter(DetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public String getPhoneNumber() {
        return order.getPhoneNumber();
    }

    @Override
    public void acceptOrder(long id, String authToken) {
        Call<Void> call = service.acceptOrder(id, authToken, acceptOrder);
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
}
