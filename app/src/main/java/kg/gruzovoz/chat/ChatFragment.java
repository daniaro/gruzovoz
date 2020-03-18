package kg.gruzovoz.chat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kg.gruzovoz.R;
import kg.gruzovoz.models.ChatUser;

public class ChatFragment extends Fragment implements ChatContract.View {

    private CircleImageView profile_image;
    private TextView user_name;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Toolbar toolbar = root.findViewById(R.id.chat_app_bar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        profile_image = root.findViewById(R.id.profile_image);
        user_name = root.findViewById(R.id.user_name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChatUser chatUser = dataSnapshot.getValue(ChatUser.class);
                assert chatUser != null;
                user_name.setText(chatUser.getUsername());
                if (chatUser.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher_round);
                }
                else {
                    Glide.with(getActivity()).load(chatUser.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }




}
