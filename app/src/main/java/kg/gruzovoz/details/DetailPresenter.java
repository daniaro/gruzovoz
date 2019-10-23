package kg.gruzovoz.details;

import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;

public class DetailPresenter implements DetailContract.DetailPresenter {

    DetailContract.DetailView view;
    CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    public DetailPresenter(DetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void populateInfo() {

    }
}
