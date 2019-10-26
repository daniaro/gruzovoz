package kg.gruzovoz.network;

import java.util.List;

import kg.gruzovoz.models.AcceptOrder;
import kg.gruzovoz.models.Login;
import kg.gruzovoz.models.Order;
import kg.gruzovoz.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CargoService {

    @GET("order/all/")
    Call<List<Order>> getAllOrders(@Header("Authorization") String authToken);

    @POST("auth/login/")
    Call<User> login(@Body Login login);

    @PUT("/order/done/{id}/")
    Call<Void> acceptOrder(@Path("id") long id, @Header("Authorization") String authToken, @Body AcceptOrder acceptOrder);

    //@PUT("order/done/{id}/")


}
