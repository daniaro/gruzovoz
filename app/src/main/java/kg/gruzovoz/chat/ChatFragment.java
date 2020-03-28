package kg.gruzovoz.chat;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import kg.gruzovoz.R;
import kg.gruzovoz.chat.messages.MessagesActivity;

public class ChatFragment extends Fragment implements ChatContract.View {

    private CardView chatCardView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Toolbar toolbar = root.findViewById(R.id.chat_app_bar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        chatCardView = root.findViewById(R.id.chat_cv);
        chatCardView.setOnClickListener(e -> startActivity( new Intent(getContext(), MessagesActivity.class)));

        return root;
    }






}
