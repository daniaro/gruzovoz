package kg.gruzovoz.chat.messages;

public class MessagesPresenter implements MessagesContract.Presenter {

    MessagesContract.View view;

    public MessagesPresenter(MessagesContract.View view) {
        this.view = view;
    }
}
