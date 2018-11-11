package com.example.rafao.proyectomoviles.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rafao.proyectomoviles.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryFragment extends Fragment {

    Spinner dropdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inventario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropdown = view.findViewById(R.id.spinner1);

        String[] items = new String[]{
                "Laceflower",
                "Sugar maple",
                "Mountain mahogany",
                "Butterfly weed"
        };

        final List<String> itemsList = new ArrayList<>(Arrays.asList(items));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, itemsList);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dropdown.setAdapter(spinnerArrayAdapter);

    }
}
