package kg.gruzovoz.details;

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

    public DetailPresenter(DetailContract.DetailView view, long id) {
        this.view = view;
        this.id = id;
        populateInfo(id);
    }

    @Override
    public void populateInfo(long id) {
        Call<OrderDetail> call = service.getDetailedOrder(id);
        call.enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                view.setViewInfo(response.body());
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {

            }
        });
    }
}
