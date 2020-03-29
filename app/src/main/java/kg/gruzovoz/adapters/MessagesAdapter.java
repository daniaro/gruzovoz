package kg.gruzovoz.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Messages> messageList;
    private MessagesContract.OnItemClickListener onItemClickListener;
    private final String userTokenId;


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    public MessagesAdapter(List<Messages> messageList, String userTokeId, MessagesContract.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.messageList = messageList;
        this.userTokenId = userTokeId;
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

            //TODO: getid and admin status


            Log.e(TAG, "userfullname:" + messages.getUid() );
            Log.e(TAG, "userTOkenId:" + userTokenId );


            if (messages.getUid()==null){
                senderName.setText("Invalid name");
            }
            else if (messages.getUid().equals(userTokenId)){
                 senderName.setText("Вы");
            }
            else {
                senderName.setText(messages.getUserFullName());

            }


            text.setText(messages.getText());


            Date date;

            TimeZone timeZone = TimeZone.getTimeZone("Asia/Bishkek");
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            sdf.setTimeZone(timeZone);

            String dateStr = messages.getSentAt();

            try {
                date = sdf.parse(dateStr);

                assert date != null;
                String format =  sdf.format(date);
                String finalDate = format.substring(11);

                time.setText(finalDate);


            } catch (Exception e) {
                e.printStackTrace();

                time.setText("неверная дата");

            }

        }
    }


}
