package kg.gruzovoz.chat;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import kg.gruzovoz.R;

public class ChatFragment extends Fragment implements ChatContract.View {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Toolbar toolbar = root.findViewById(R.id.chat_app_bar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        return root;
    }




}
