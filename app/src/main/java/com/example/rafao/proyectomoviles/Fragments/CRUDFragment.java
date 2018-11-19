package com.example.rafao.proyectomoviles.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rafao.proyectomoviles.Models.Dependencia;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CRUDFragment extends Fragment {

    private TextView text;
    private Spinner spinner1;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private  FirebaseDatabase database;
    private ArrayList<Dependencia> DependenceList = new ArrayList<>();
    private ArrayList<String> DependencieList = new ArrayList<>();
    private DatabaseReference root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.crud, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
       // text = view.findViewById(R.id.voice);
        spinner1 = view.findViewById(R.id.spinner3);

        //text.setOnClickListener(v -> startVoiceRecognitionActivity());

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

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("CRUD",spinner1.getSelectedItem().toString());
//                InventoryInfoFragment fragment = new InventoryInfoFragment();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.rootContainer,fragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void startVoiceRecognitionActivity() {
        // Definición del intent para realizar en análisis del mensaje
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Indicamos el modelo de lenguaje para el intent
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Definimos el mensaje que aparecerá
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"¿Que desea realizar?");
        // Lanzamos la actividad esperando resultados
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Si el reconocimiento a sido bueno
        if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
            //El intent nos envia un ArrayList aunque en este caso solo
            //utilizaremos la pos.0
            ArrayList<String> matches = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);
            //Separo el texto en palabras.
            String [ ] palabras = matches.get(0).split(" ");
            //Si la primera palabra es LLAMAR
            Log.i("CRUD",palabras[0]);
            }
    }
}
