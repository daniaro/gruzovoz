package kg.gruzovoz.chat.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.MessagesAdapter;
import kg.gruzovoz.models.Messages;

public class MessagesActivity extends AppCompatActivity implements MessagesContract.View{

    private ImageView closeIcon;
    private EditText editText;
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Messages> messageList = new ArrayList<>();


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

        initList();
        getMessages();

    }

    public void initList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessagesAdapter(this,messageList);
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
                                messageList.add(change.getDocument().toObject(Messages.class));
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
        Map<String, Object> map = new HashMap<>();
        map.put("sentAt","18:04");
        map.put("text",text);
        map.put("userFullName","Алмаз");
        FirebaseFirestore.getInstance().collection("messages").add(map);
    }


}
