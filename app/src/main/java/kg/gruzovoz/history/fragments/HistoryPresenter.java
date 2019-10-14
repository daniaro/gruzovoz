package kg.gruzovoz.history.fragments;

import java.util.List;

import kg.gruzovoz.history.fragments.HistoryContract;
import kg.gruzovoz.models.Order;

public class HistoryPresenter implements HistoryContract.Presenter {

    HistoryContract.View view;

    public void HistoryPresenter(HistoryContract.View view){this.view = view;}

    @Override
    public void populateOrders() {

    }

    @Override
    public void openDetailScreen(Order order) {

    }
}
