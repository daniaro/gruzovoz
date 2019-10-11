package kg.gruzovoz.history.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.gruzovoz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {


    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View rootView  = inflater.inflate(R.layout.fragment_active, container, false);


        return rootView;
    }

}
