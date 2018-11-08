package com.example.rafao.proyectomoviles;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView user;
    TextView pass;
    Button boton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        mAuth = FirebaseAuth.getInstance();

        //boton = findViewById(R.id.registro);
        boton.setOnClickListener(this);

        user = findViewById(R.id.editText3);
        pass = findViewById(R.id.editText4);

    }

    @Override
    public void onClick(View v) {
        String user = this.user.getText().toString();
        String pass = this.pass.getText().toString();

        mAuth.createUserWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("PKTA", "createUserWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("PKTA","Error al crear usuaio");
                        }

                        // ...
                    }
                });
    }
}
