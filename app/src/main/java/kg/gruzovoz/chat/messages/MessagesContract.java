package kg.gruzovoz.chat.messages;

import kg.gruzovoz.models.Messages;
import kg.gruzovoz.models.Notify;

public interface MessagesContract {

    interface OnItemClickListener{
        void onItemClick(Messages messages);

    }

    interface  View{
        void notAuthorized();
    }

    interface Presenter{
        void sendNotify(String fbUserId,String fbUserName, String text);
    }
}
