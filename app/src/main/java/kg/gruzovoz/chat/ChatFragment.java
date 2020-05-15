package kg.gruzovoz.chat;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import kg.gruzovoz.BaseContract;
import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesActivity;
import kg.gruzovoz.fcm.MyFirebaseMessagingService;
import kg.gruzovoz.models.Messages;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends Fragment implements ChatContract.View {

    private TextView lastMessage;
    private TextView lastMessageTime;
    private TextView lastsender;
    private CardView imageViewMessageCounter;
    private TextView textViewMessageCounter;
    private ProgressBar progressBar;
    private CardView chatCardView;
    private static Long fbUserId;
    private List<Messages> messageList = new ArrayList<>();
    private SharedPreferences.Editor editor;
    private  static int message_counter;
    private BaseContract.OnOrderFinishedListener onOrderFinishedListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Toolbar toolbar = root.findViewById(R.id.chat_app_bar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        initViews(root);
        setViews();
        sharedPreferences();
        shwProgressBar();

        return root;
    }

    public ChatFragment() {

    }


    public ChatFragment(BaseContract.OnOrderFinishedListener onOrderFinishedListener) {
        this.onOrderFinishedListener = onOrderFinishedListener;
    }

    private void sharedPreferences() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        fbUserId = sharedPreferences.getLong("fbUserId",0);
        message_counter = sharedPreferences.getInt("message_counter", 0);
        editor =  sharedPreferences.edit();
    }

    @SuppressLint("SetTextI18n")
    private void setViews() {
        FirebaseFirestore.getInstance().collection("messages")
                .orderBy("sentAt")
                .limitToLast(1)
                .addSnapshotListener((snapshots, e) -> {
            try {
                assert snapshots != null;
                for (DocumentChange change : snapshots.getDocumentChanges()) {
                    switch (change.getType()) {
                        case ADDED:
                            Messages messages = change.getDocument().toObject(Messages.class);
                            messageList.add(messages);
                            if (!(messages.getUid().equals(String.valueOf(fbUserId)))) {
                                lastsender.setText(messages.getUserFullName() + ": ");
                            }
                            else {
                                lastsender.setText("Вы: ");
                            }
                            lastMessage.setText(messages.getText());
                            lastMessageTime.setText(String.valueOf(messages.getSentAt().toDate()).substring(11,16));

                            Log.e("message_counter", String.valueOf(message_counter));

                            if(message_counter == 0){
                                imageViewMessageCounter.setVisibility(View.GONE);
                                lastMessageTime.setTextColor(Color.parseColor("#928C8C"));

                            }
                            else {
                                lastMessageTime.setTextColor(Color.parseColor("#5A00FF"));
                                imageViewMessageCounter.setVisibility(View.VISIBLE);
                                textViewMessageCounter.setVisibility(View.VISIBLE);
                                textViewMessageCounter.setText(String.valueOf(message_counter));

                            }

                            hideProgressBar();
                            break;
                        case REMOVED:
                            break;
                        case MODIFIED:
                            break;
                    }
                }
            }
            catch (NullPointerException ex){
                shwProgressBar();

            }

                });
    }

    private void initViews(View root) {
        TextView chatName = root.findViewById(R.id.chat_name_tv);
        progressBar = root.findViewById(R.id.indeterminateBar);
        chatCardView = root.findViewById(R.id.chat_cv);
        lastMessage = root.findViewById(R.id.last_message_tv);
        lastMessageTime = root.findViewById(R.id.last_message_time_tv);
        lastsender = root.findViewById(R.id.last_sender_tv);
        imageViewMessageCounter = root.findViewById(R.id.image_view_message_counter);
        textViewMessageCounter = root.findViewById(R.id.text_view_message_counter);
        if (message_counter == 0){
            imageViewMessageCounter.setVisibility(View.GONE);
        }


        chatCardView.setOnClickListener(e -> {
            message_counter = 0;
            editor.putInt("message_counter", message_counter).commit();
            startActivityForResult(new Intent(getContext(), MessagesActivity.class),101);

            chatCardView.setEnabled(false);
            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                        Objects.requireNonNull(getActivity()).runOnUiThread(() -> chatCardView.setEnabled(true));
                }
            }, 3000);
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","result");

        if (resultCode == RESULT_OK && requestCode ==101) {
            message_counter = 0;
            onOrderFinishedListener.onOrderFinished();

        }
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        chatCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void shwProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        chatCardView.setVisibility(View.GONE);
    }


}
