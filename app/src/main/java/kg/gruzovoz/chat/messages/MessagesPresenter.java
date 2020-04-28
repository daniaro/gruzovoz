package kg.gruzovoz.chat.messages;

import android.annotation.SuppressLint;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.models.Notify;
import kg.gruzovoz.models.NotifyStatus;
import kg.gruzovoz.network.CargoService;
import kg.gruzovoz.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesPresenter implements MessagesContract.Presenter {

    MessagesContract.View view;
    private CargoService service = RetrofitClientInstance.getRetrofitInstance().create(CargoService.class);


    public MessagesPresenter(MessagesContract.View view) {
        this.view = view;
    }

    @Override
    public void sendNotify(String fbUserId,String fbUserName, String text){
        Notify notify = new Notify(fbUserId,fbUserName,text);

        Call<NotifyStatus> call = service.sendNotify(BaseActivity.authToken, notify);
        call.enqueue(new Callback<NotifyStatus>() {
            @Override
            public void onResponse(@NotNull Call<NotifyStatus> call, @NotNull Response<NotifyStatus> response) {
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<NotifyStatus> call, @NotNull Throwable t) {

            }
        });

    }
}
