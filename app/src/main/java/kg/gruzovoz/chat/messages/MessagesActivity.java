package kg.gruzovoz.chat.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.MessagesAdapter;
import kg.gruzovoz.models.Messages;

public class MessagesActivity extends AppCompatActivity implements MessagesContract.View{

    private EditText editText;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Messages> messageList = new ArrayList<>();
    public static String fbUserName;
    public static Long fbUserId;
    private LinearLayout emptyView;
    private Messages messages = new Messages();


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        initViews();
        getSharedPref();
        initList();
        getMessages();



    }


    private void initViews() {
        emptyView = findViewById(R.id.empty_view);
        editText = findViewById(R.id.edit_text);
        recyclerView = findViewById(R.id.recyler_view_messages);
        ImageView closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(e ->{
            onBackPressed();
            finish();
        });
    }

    private void getSharedPref() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

        fbUserId = sharedPreferences.getLong("fbUserId",0);
        fbUserName = sharedPreferences.getString("fbUserName", null);

    }

    public void initList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.scrollToPosition(messageList.lastIndexOf(new Messages()));
        adapter = new MessagesAdapter(messageList, String.valueOf(fbUserId), e ->{

        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


    public void onClickSendMesssage(View view) {
        String text = editText.getText().toString().trim();
        editText.setText("");
        sendMessage(text);
    }


    private void getMessages() {
        FirebaseFirestore.getInstance().collection("messages").orderBy("sentAt").limit(50)
                .addSnapshotListener((snapshots, e) -> {
                    try {
                        for (DocumentChange change : snapshots.getDocumentChanges()) {
                            switch (change.getType()) {
                                case ADDED:
                                    Messages messages = change.getDocument().toObject(Messages.class);
                                    messageList.add(0,messages);
                                    recyclerView.scrollToPosition(messageList.lastIndexOf(messages));

                                    break;
                                case REMOVED:
                                    break;
                                case MODIFIED:
                                    break;
                            }
                        }
                    }
                    catch (NullPointerException ex){
                        recyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);

                    }

                    adapter.notifyDataSetChanged();
                });

    }

    private void sendMessage(String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("sentAt", Timestamp.now());
        map.put("text",text);
        map.put("userFullName", fbUserName);
        map.put("isFromSuperAdmin", false);
        map.put("uid", String.valueOf(fbUserId));
        FirebaseFirestore.getInstance().collection("messages").add(map);
//        recyclerView.scrollToPosition(messageList.lastIndexOf(messages));
//        adapter.notifyDataSetChanged();


    }



}
