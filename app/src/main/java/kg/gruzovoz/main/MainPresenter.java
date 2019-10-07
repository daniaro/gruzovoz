package kg.gruzovoz.main;

import java.util.List;

import kg.gruzovoz.models.Order;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void populateOrders(List<Order> orders) {
        view.logOut();
    }

    @Override
    public void openDetailScreen(Order order) {

    }
}
