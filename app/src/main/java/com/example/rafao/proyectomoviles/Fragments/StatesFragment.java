package com.example.rafao.proyectomoviles.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rafao.proyectomoviles.R;

public class StatesFragment extends Fragment implements View.OnClickListener {

    private Button btn1,btn2;
    private int state;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.usuarios,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn1 = view.findViewById(R.id.habil);
        btn2 = view.findViewById(R.id.inhabil);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.habil){
            state = 1;
        }else{
            state = 0;
        }

        UserStatesFragment states = new UserStatesFragment(state);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameHa,states)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();

    }
}
