package com.example.rafao.proyectomoviles.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafao.proyectomoviles.MainActivity;
import com.example.rafao.proyectomoviles.Models.Usuario;
import com.example.rafao.proyectomoviles.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Button btn;
    private EditText user;
    private EditText pass;
    private FirebaseAuth mAuth;
    private String usuario;
    private String contra;
    private FirebaseDatabase database;
    private DatabaseReference root;

    private int Login = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        root = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        user = view.findViewById(R.id.editText3);
        pass = view.findViewById(R.id.editText4);

        btn = view.findViewById(R.id.registro);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        usuario = user.getText().toString();
        contra = pass.getText().toString();
        mAuth.createUserWithEmailAndPassword(usuario,contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            IniciaSession();
                            if(Login == 1){
                                FirebaseUser user = mAuth.getCurrentUser();

                                Usuario newUser = new Usuario();
                                newUser.nombre = "Eduardo";
                                newUser.apellidos = "Soriano";

                                DatabaseReference users = root.child("Usuarios");

                                HashMap<String, Object> hashMap = new HashMap<> ();
                                hashMap.put (user.getEmail(), newUser);

                                users.updateChildren (hashMap) //updateChildren envía los datos asíncronamente por lo que hay que definir los listener correspodientes
                                        .addOnSuccessListener (v -> {
                                            Log.i ("PKTA", "Información almacenada");
                                        });
                            }
                        }else{
                            Log.i("PKTA","Usuario No Registrado Exitosamente");
                        }
                    }
                });
    }

    private void IniciaSession() {
        mAuth.signInWithEmailAndPassword(usuario, contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Login = 1;
                        } else {
                            Login = 0;
                        }
                    }
                });
    }
}
