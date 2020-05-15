package kg.gruzovoz.chat.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kg.gruzovoz.R;
import kg.gruzovoz.adapters.MessagesAdapter;
import kg.gruzovoz.login.LoginActivity;
import kg.gruzovoz.models.FinishOrder;
import kg.gruzovoz.models.Messages;
import kg.gruzovoz.models.Results;

public class MessagesActivity extends AppCompatActivity implements MessagesContract.View{

    private MessagesContract.Presenter presenter = new MessagesPresenter(this);
    private List<Messages> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;

    private EditText editText;
    private LinearLayout emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static String fbUserName;
    public static Long fbUserId;
    public static boolean active = false;
    private boolean indexForScroll = true;
    private static final int TOTAL_ITEMS_TO_LOAD = 100;
    private int curentPage = 1;
    public static int message_counter;



    SharedPreferences.Editor editor;

    @Override
    public void onStart() {
        super.onStart();
        active = true;

    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        initViews();
        getSharedPref();
        initList();
        getMessages(indexForScroll);
        subscribeTopic();
        setPresence(Boolean.TRUE);
        message_counter = 0;
        editor.putInt("message_counterForMFMR", message_counter).commit();
    }


    private void initViews() {
        emptyView = findViewById(R.id.empty_view);
        editText = findViewById(R.id.edit_text);
        recyclerView = findViewById(R.id.recyler_view_messages);
        ImageView closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(e ->{
            onBackPressed();
            editor.putInt("message_counter",0).commit();
        });
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_message);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            curentPage++;
            indexForScroll = false;
            messageList.clear();
            getMessages(indexForScroll);
            swipeRefreshLayout.setRefreshing(false);
        });
    }


    private void getSharedPref() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        fbUserId = sharedPreferences.getLong("fbUserId",0);
        fbUserName = sharedPreferences.getString("fbUserName", null);
        editor = sharedPreferences.edit();

    }


    public void initList(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
//        recyclerView.setHasFixedSize(true);
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


    private void getMessages(boolean indexForScroll) {
        FirebaseFirestore.getInstance().collection("messages").orderBy("sentAt")
                .limitToLast(curentPage * TOTAL_ITEMS_TO_LOAD)
                .addSnapshotListener((snapshots, e) -> {
                    try {
                        assert snapshots != null;
                        for (DocumentChange change : snapshots.getDocumentChanges()) {
                            switch (change.getType()) {
                                case ADDED:
                                    Messages messages = change.getDocument().toObject(Messages.class);
                                    messageList.add(0,messages);
                                    if (indexForScroll)
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
        Log.e("santAt", String.valueOf(FieldValue.serverTimestamp()));
        map.put("sentAt", FieldValue.serverTimestamp());
//        map.put("sentAt", Timestamp.now());
        map.put("text",text);
        map.put("userFullName", fbUserName);
        map.put("isFromSuperAdmin", false);
        map.put("uid", String.valueOf(fbUserId));

        FirebaseFirestore.getInstance().collection("messages").add(map);

        presenter.sendNotify(String.valueOf(fbUserId),fbUserName,text);

    }


    private void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("cargo_general")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d("msg", msg);
                    Log.d("subscribing status",msg);
                });
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String t = intent.getStringExtra("title");
            String t1 = intent.getStringExtra("body");
            //alert data here
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("myFunction"));
        Log.e("onResume", "broadcastIntent");

    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        Log.e("onPause", "broadcastIntent");

    }


    private void setPresence(boolean online)  {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myConnectionsRef = database.getReference("presence");

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    DatabaseReference con = myConnectionsRef.child(String.valueOf(fbUserId));
                    con.setValue(online);

                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.e("error onCancelled", "Listener was cancelled at .info/connected");
            }
        });
    }

    @Override
    public void notAuthorized() {
        Intent intent = new Intent(this, LoginActivity.class);
        editor.putString("authToken", null).commit();
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

}
