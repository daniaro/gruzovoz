package kg.gruzovoz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesContract;
import kg.gruzovoz.models.Messages;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<Messages> messageList;
    private LayoutInflater inflater;
    private MessagesContract.OnItemClickListener clickListener;


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessagesAdapter(Context context, List<Messages> messages) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.messageList = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_item_right,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messageList.get(position), clickListener);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView senderName;
        private TextView text;
        private TextView time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            senderName = itemView.findViewById(R.id.sender_name_tv);
            text = itemView.findViewById(R.id.message_tv);
            time = itemView.findViewById(R.id.time_tv);

        }


        void bind(Messages messages, MessagesContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(e-> onItemClickListener.onItemClick(messages));

            senderName.setText(messages.getUserFullName());
            text.setText(messages.getText());
            time.setText(messages.getSentAt());
        }
    }
}
