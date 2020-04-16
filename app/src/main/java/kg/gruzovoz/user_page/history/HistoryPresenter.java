package kg.gruzovoz.user_page.history;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.Results;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);

    HistoryPresenter(HistoryContract.View view) {
        this.view = view;
    }


}
