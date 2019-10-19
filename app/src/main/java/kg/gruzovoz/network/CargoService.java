package kg.gruzovoz.network;

import java.util.List;

import kg.gruzovoz.models.Order;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CargoService {

    @GET("order/all/")
    Call<List<Order>> getAllOrders(@Header("Authorization") String authToken);
}
