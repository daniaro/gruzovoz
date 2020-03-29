package kg.gruzovoz.chat.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.MessagesAdapter;
import kg.gruzovoz.models.Messages;
import kg.gruzovoz.models.UserPage;

public class MessagesActivity extends AppCompatActivity implements MessagesContract.View{

    private ImageView closeIcon;
    private EditText editText;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Messages> messageList = new ArrayList<>();
    private UserPage userPage = new UserPage();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static String driversName;



    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        editText = findViewById(R.id.edit_text);
        recyclerView = findViewById(R.id.recyler_view_messages);
        closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(e ->{
            onBackPressed();
            finish();
        });

        sharedPreferences = getApplicationContext()
                .getSharedPreferences("myNamePreferences", Context.MODE_PRIVATE);

        driversName =sharedPreferences.getString("myName", null);
        Log.e("Messagesactivity", driversName);

        initList();
        getMessages();

    }

    public void initList(){
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new MessagesAdapter(messageList,driversName, e ->{
        });
        recyclerView.setAdapter(adapter);


    }

    public void onClickSendMesssage(View view) {
        String text = editText.getText().toString().trim();
        editText.setText("");

        sendMessage(text);
    }



    private void getMessages() {
        FirebaseFirestore.getInstance().collection("messages")
                .addSnapshotListener((snapshots, e) -> {
                    for (DocumentChange change : snapshots.getDocumentChanges()){
                        switch (change.getType()){
                            case ADDED:
                                Messages messages = change.getDocument().toObject(Messages.class);
                                messageList.add(messages);
                                Log.i("Message",messages.getText());
                                break;
                            case REMOVED:
                                break;
                            case MODIFIED:
                                break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                });

    }

    private void sendMessage(String text) {
        UserPage userPage = new UserPage();
        Map<String, Object> map = new HashMap<>();
//        map.put("sentAt", Timestamp.now());
//        map.put("sentAt", "noTime");
        map.put("sentAt", setFormatedDate());
        map.put("text",text);
        map.put("userFullName", driversName);
//        map.put("userFullName", userPage.getUser().getUsername());
        FirebaseFirestore.getInstance().collection("messages").add(map);
    }

    @SuppressLint("LongLogTag")
    private String setFormatedDate() {
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Bishkek");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        sdf.setTimeZone(timeZone);

        return sdf.format(date);

    }


}
