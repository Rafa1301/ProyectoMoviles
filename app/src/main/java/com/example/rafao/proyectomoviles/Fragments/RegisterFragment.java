package com.example.rafao.proyectomoviles.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafao.proyectomoviles.Models.Usuario;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private Button btn;
    private EditText nombre;
    private EditText apellidos;
    private EditText user;
    private EditText pass;

    private String usuario;
    private String contra;
    private String FirstName;
    private String LastName;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        root = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        user = view.findViewById(R.id.correo);
        pass = view.findViewById(R.id.contra);
        nombre = view.findViewById(R.id.nombre);
        apellidos = view.findViewById(R.id.apellidos);

        btn = view.findViewById(R.id.registro);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registro) {
            RegisterUser();
        }
    }

    private void RegisterUser() {
        usuario = user.getText().toString();
        contra = pass.getText().toString();
        FirstName = nombre.getText().toString();
        LastName = apellidos.getText().toString();

        mAuth.createUserWithEmailAndPassword(usuario, contra)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mAuth.signInWithEmailAndPassword(usuario, contra)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        DatabaseReference users = root.child("Usuarios");

                                        String id = users.push().getKey();

                                        Usuario newUser = new Usuario();
                                        newUser.id = id;
                                        newUser.nombre = FirstName;
                                        newUser.apellidos = LastName;
                                        newUser.correo = usuario;
                                        newUser.habilitado = 0;
                                        newUser.admin = 0;

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put(id, newUser);

                                        users.updateChildren(hashMap)
                                                .addOnSuccessListener( v -> {
                                                    Toast.makeText(getContext(), "Registro Exitoso.",
                                                        Toast.LENGTH_SHORT).show();
                                                }); //updateChildren envía los datos asíncronamente por lo que hay que definir los listener correspodientes
                                    }
                                });
                    }else{
                        Toast.makeText(getContext(), "Error al Registrarte.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}