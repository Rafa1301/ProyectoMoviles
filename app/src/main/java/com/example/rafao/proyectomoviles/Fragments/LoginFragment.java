package com.example.rafao.proyectomoviles.Fragments;

import android.content.Intent;
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
import com.example.rafao.proyectomoviles.PrincipalPage;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button btn;
    private EditText users,password;
    private String user, pass;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference root;

    //SessionManagement session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loginuv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        root = database.getReference("/Usuarios");

        users = view.findViewById(R.id.editText);
        password = view.findViewById(R.id.editText2);

        btn = view.findViewById(R.id.btn2);
        btn.setOnClickListener(this);

        //session = new SessionManagement(getContext());
        //session.checkLogin();

    }

    public void singIn(){
        user = users.getText().toString();
        pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        root.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot item: dataSnapshot.getChildren()) {
                                    Usuario userLogin = item.getValue(Usuario.class);
                                    if(userLogin.correo.equals(user)){
                                        if(userLogin.habilitado != 0){
                                            //session.createLoginSession(userLogin.nombre,user);
                                            Intent intent = new Intent(getContext(),PrincipalPage.class);
                                            intent.putExtra("user",userLogin);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(getContext(), "No puedes iniciar sesion.\nContacta al administrador.",
                                                Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                        });
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn2){
            singIn();
        }
    }
}
