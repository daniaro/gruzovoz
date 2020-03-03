package kg.gruzovoz.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import kg.gruzovoz.BaseActivity;
import kg.gruzovoz.R;


public class ChatFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Toolbar toolbar = root.findViewById(R.id.chat_app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        return root;
    }


//    //    @Override
//    public void showConfirmLogoutDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
//        builder.setTitle(getString(R.string.logout_title));
//        builder.setMessage(getString(R.string.logout_message));
//        builder.setNegativeButton(R.string.cancel_order, null);
//        builder.setPositiveButton(getString(R.string.action_logout), (dialog, which) -> {
//            BaseActivity.authToken = null;
//            SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.remove("authToken").apply();
//            new Handler().post(() -> {
//                Intent intent = getActivity().getIntent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                getActivity().overridePendingTransition(0, 0);
//                getActivity().finish();
//
//                getActivity().overridePendingTransition(0, 0);
//                startActivity(intent);
//            });
//
//
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_logout:
//                showConfirmLogoutDialog();
//                return true;
//        }
//        return false;
//    }

}
