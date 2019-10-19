package kg.gruzovoz.main;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kg.gruzovoz.models.Order;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenter implements OrdersContract.Presenter {

    OrdersContract.View view;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
    }



    @Override
    public void populateOrders() {
<<<<<<< HEAD
//        ArrayList<Order> ordersList = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            Order order = new Order(i, "Спринтер", "Сегодня", "Байтик Баатыра 70", "Байтик Баатыра 110а", 16487, 10, "Доступно", "Хочу чтобы все четко доставили");
//            ordersList.add(order);
//            Log.e("log", order.toString());
//        }

        Call<List<Order>> call = service.getAllOrders("Token 7b86ca9dc2c619467f92d9e084c6a91fa2daa5d7");
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                view.setOrders(response.body());
                view.stopRefreshingOrders();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                //TODO to implement this feature
            }
        });
=======
        view.logOut();
        ArrayList<Order> ordersList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Order order = new Order(i, "Спринтер", "Сегодня", "Байтик Баатыра 70", "Байтик Баатыра 110а", 16487, 10, "Доступно", "Хочу чтобы все четко доставили");
            ordersList.add(order);
            Log.e("log", order.toString());
        }

        view.setOrders(ordersList);
>>>>>>> cb07cc48759e5df724684c7d0055f0c6e1c3453b
    }

    @Override
    public void openDetailScreen(Order order) {
        view.showDetailScreen(order.getId());
    }
}
