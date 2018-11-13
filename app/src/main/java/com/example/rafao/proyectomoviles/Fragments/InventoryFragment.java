package com.example.rafao.proyectomoviles.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rafao.proyectomoviles.Models.Dependencia;
import com.example.rafao.proyectomoviles.Models.Motivo;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventoryFragment extends Fragment {

    private Spinner dropdown;
    private Spinner spinner1;
    private ArrayList<String> MotiveList = new ArrayList<>();
    private ArrayList<Dependencia> DependenceList = new ArrayList<>();
    private ArrayList<String> DependencieList = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference root;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inventario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropdown = view.findViewById(R.id.spinner1);
        spinner1 = view.findViewById(R.id.spinner2);
        btn = view.findViewById(R.id.btn3);

        database = FirebaseDatabase.getInstance();
        root = database.getReference("/Motivos");

        root.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Motivo motive = item.getValue(Motivo.class);
                    MotiveList.add(motive.Motivo);
                }

                final ArrayAdapter<String> MotivesArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,MotiveList);
                MotivesArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                dropdown.setAdapter(MotivesArrayAdapter);
                dropdown.setSelection(0);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        root = database.getReference("/Dependencia");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Dependencia dependence = item.getValue(Dependencia.class);
                    DependenceList.add(dependence);
                    DependencieList.add(dependence.Nombre);
                }

                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,DependencieList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                spinner1.setAdapter(spinnerArrayAdapter);
                spinner1.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(v -> {
            String dependencie = spinner1.getSelectedItem().toString();
            int codigo = getCode(dependencie);

            InfoListFragment fragment2 = new InfoListFragment();
            Bundle data = new Bundle();
            data.putInt("codigo",codigo);
            fragment2.setArguments(data);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootContainer,fragment2)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit ();
        });

    }

    private int getCode(String dependencie) {
        int pos = DependencieList.indexOf(dependencie);
        return DependenceList.get(pos).Codigo;
    }

}
