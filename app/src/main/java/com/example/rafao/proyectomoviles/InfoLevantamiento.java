package com.example.rafao.proyectomoviles;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rafao.proyectomoviles.Fragments.InfoProductFragment;
import com.example.rafao.proyectomoviles.Models.Motivo;
import com.example.rafao.proyectomoviles.Models.Productos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoLevantamiento extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private ArrayList<String> MotiveList;
    private Spinner dropdown;
    private Button btn,btn1,btn2;
    private String codigo = "";

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levantamiento_productos);

        int code = getIntent().getIntExtra("code",0);
        dropdown = findViewById(R.id.spinner1);
        reference = database.getReference("/Motivos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MotiveList = new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    Motivo motive = item.getValue(Motivo.class);
                    MotiveList.add(motive.Motivo);
                }

                final ArrayAdapter<String> MotivesArrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item,MotiveList);
                MotivesArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                dropdown.setAdapter(MotivesArrayAdapter);
                dropdown.setSelection(0);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


        InfoProductFragment fragment = new InfoProductFragment();
        Bundle data = new Bundle();
        data.putInt("codigo",code);
        fragment.setArguments(data);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rootContainer02,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();

        btn = findViewById(R.id.cam);
        btn1 = findViewById(R.id.micro);
        btn2 = findViewById(R.id.fin);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(v -> startVoiceRecognitionActivity());
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cam:
                i = new Intent(v.getContext(),Camera.class);
                v.getContext().startActivity(i);
                break;
            case R.id.fin:
                terminarLevantamiento();
                break;
        }
    }

    private void terminarLevantamiento() {
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference ref = root.getReference("/Productos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot it: dataSnapshot.getChildren()){
                    Productos pr = it.getValue(Productos.class);
                    if(pr.Inventario == 1){
                        pr.Inventario = 0;
                        DatabaseReference u = ref.child(pr.id);
                        u.setValue(pr);
                    }
                }
                Toast.makeText(getApplicationContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Definimos el mensaje que aparecerá
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Dicte el codigo de producto");
        // Lanzamos la actividad esperando resultados
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);
            String [] palabras = matches.get(0).split(" ");
            for(int i=0;i<palabras.length;i++){
                codigo += ""+palabras[i];
            }
            codigo = codigo.toUpperCase();
            Log.i("MICRO",""+codigo);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("/Productos");
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dt :dataSnapshot.getChildren()) {
                        Productos pr = dt.getValue(Productos.class);
                        if (pr.Codigo.equals(codigo)) {
                            pr.Inventario = 1;
                            DatabaseReference child = ref.child(pr.id);
                            child.setValue(pr);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

}
