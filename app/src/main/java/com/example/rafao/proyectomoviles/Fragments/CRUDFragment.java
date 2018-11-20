package com.example.rafao.proyectomoviles.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rafao.proyectomoviles.Models.Dependencia;
import com.example.rafao.proyectomoviles.ProductsActivity;
import com.example.rafao.proyectomoviles.ProductsList;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CRUDFragment extends Fragment implements View.OnClickListener {

    private Spinner spinner1;
    private  FirebaseDatabase database;
    private ArrayList<Dependencia> DependenceList = new ArrayList<>();
    private ArrayList<String> DependencieList = new ArrayList<>();
    private DatabaseReference root;
    private Button btn1,btn2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.crud, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        spinner1 = view.findViewById(R.id.spinner3);

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

        btn1 = view.findViewById(R.id.agregar);
        btn2 = view.findViewById(R.id.mostrar);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String dep = spinner1.getSelectedItem().toString();
        int code = getCode(dep);
        Intent intent;
        if(v.getId() == R.id.agregar){
            intent = new Intent(getActivity(),ProductsActivity.class);
        }else{
            intent = new Intent(getActivity(),ProductsList.class);
        }
        intent.putExtra("code",code);
        startActivity(intent);
    }

    private int getCode(String dependencie) {
        int pos = DependencieList.indexOf(dependencie);
        return DependenceList.get(pos).Codigo;
    }
}
