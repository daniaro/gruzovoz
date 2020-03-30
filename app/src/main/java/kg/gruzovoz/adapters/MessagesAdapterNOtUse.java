package kg.gruzovoz.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesContract;
import kg.gruzovoz.models.Messages;


public class MessagesAdapterNOtUse extends RecyclerView.Adapter<MessagesAdapterNOtUse.ViewHolder> {

    private List<Messages> messageList;
    private MessagesContract.OnItemClickListener onItemClickListener;
    private final String userId;


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    public MessagesAdapterNOtUse(List<Messages> messageList, String userTokeId, MessagesContract.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.messageList = messageList;
        this.userId = userTokeId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item_right, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(messageList.get(position), onItemClickListener);

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


        @SuppressLint("LongLogTag")
        void bind(Messages messages, MessagesContract.OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(e-> onItemClickListener.onItemClick(messages));

            if (messages.getUid() == null || userId.equals("0")){
                senderName.setText("Invalid name");
            }
            else if (messages.getUid().equals(userId)){
                 senderName.setText("Вы");
            }
            else if (messages.isFromSuperAdmin()){
                //TODO: always false why?
                senderName.setText("Админ");
            }
            else {
                senderName.setText(messages.getUserFullName());

            }

            String dateStr = messages.getSentAt();

            text.setText(messages.getText());
            time.setText(parseDate(dateStr));

        }

        private String parseDate(String dateStr) {
            Date date;

            TimeZone timeZone = TimeZone.getTimeZone("Asia/Bishkek");
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            sdf.setTimeZone(timeZone);

            try {
                date = sdf.parse(dateStr);
                assert date != null;
                String format =  sdf.format(date);
                return format.substring(11);

            } catch (Exception e) {
                e.printStackTrace();
                return "неверная дата";
            }
        }
    }


}
