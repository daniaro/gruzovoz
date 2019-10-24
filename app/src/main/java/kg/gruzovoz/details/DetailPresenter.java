package kg.gruzovoz.details;

import android.util.Log;
import android.widget.Toast;

import kg.gruzovoz.models.OrderDetail;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.DetailPresenter {

    private long id;
    private DetailContract.DetailView view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    public DetailPresenter(DetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void populateInfo(long id, String authToken) {
        Call<OrderDetail> call = service.getDetailedOrder(id, authToken);
        call.enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                Log.i(getClass().getSimpleName(), "AAAAAAAAAAAAAAAAAAAAAAAAA" + response.body().getCarType() + response.body().getPhoneNumber());
                view.setViewInfo(response.body());
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.getMessage());
            }
        });
    }
}
