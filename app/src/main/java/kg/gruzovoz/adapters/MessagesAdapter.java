package kg.gruzovoz.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesContract;
import kg.gruzovoz.models.Messages;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Messages> messageList;
    private MessagesContract.OnItemClickListener onItemClickListener;
    private final String userId;

    public MessagesAdapter(List<Messages> messageList, String userTokeId, MessagesContract.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.messageList = messageList;
        this.userId = userTokeId;

    }

    @Override
    public int getItemViewType(int position) {

        if (!messageList.get(position).getUid().equals(userId)){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_left, parent, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item_right, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                viewHolder0.bind(messageList.get(position), onItemClickListener);
                break;
            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                viewHolder1.bind(messageList.get(position), onItemClickListener);
                break;
        }
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {

        private TextView senderName;
        private TextView text;
        private TextView time;

        ViewHolder0(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name_tv);
            text = itemView.findViewById(R.id.message_tv);
            time = itemView.findViewById(R.id.time_tv);

        }

        @SuppressLint("SetTextI18n")
        void bind(Messages messages, MessagesContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(e-> onItemClickListener.onItemClick(messages));

            if (messages.getUid() == null || userId.equals("0")){
                senderName.setText("Invalid name");
            }
            else if (messages.isFromSuperAdmin()){
                //TODO: always false why?
                senderName.setText("Админ");
            }
            else {
                senderName.setText(messages.getUserFullName());
            }

            text.setText(messages.getText());
            try {
                time.setText(String.valueOf(messages.getSentAt()).substring(0,16));

            }catch (StringIndexOutOfBoundsException ex){

            }
//            time.setText(String.valueOf(messages.getSentAt().toDate()).substring(11,16));

        }

    }


    class ViewHolder1 extends RecyclerView.ViewHolder {

        private TextView senderName;
        private TextView text;
        private TextView time;

        ViewHolder1(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name_tv);
            text = itemView.findViewById(R.id.message_tv);
            time = itemView.findViewById(R.id.time_tv);

        }


        @SuppressLint("SetTextI18n")
        void bind(Messages messages, MessagesContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(e-> onItemClickListener.onItemClick(messages));

            if (messages.getUid() == null || userId.equals("0")){
                senderName.setText("Invalid name");
            }
            else if (messages.getUid().equals(userId)){
                senderName.setText("Вы");
            }
            else {
                senderName.setText("Invalid name");
            }

//            messages.setSentAt(Timestamp.now());
            text.setText(messages.getText());
            try {
                time.setText(String.valueOf(messages.getSentAt()).substring(0,16));
            }catch (StringIndexOutOfBoundsException ex){

            }
//            time.setText(String.valueOf(messages.getSentAt().toDate()).substring(11,16));


        }

    }
}