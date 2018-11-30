package com.example.rafao.proyectomoviles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.example.rafao.proyectomoviles.Models.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends Activity{
    private final int DURACION_SPLASH = 2000;
    private DatabaseReference root;
    private FirebaseDatabase database;
    private SharedPreferences sp;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();

        root = database.getReference("/Usuarios");

        sp = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if(sp.contains("email")){
                String email = sp.getString("email","");
                intent = new Intent(this,PrincipalPage.class);
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot it: dataSnapshot.getChildren()) {
                            Usuario value = it.getValue(Usuario.class);
                            if(value.correo.equals(email)){
                                intent.putExtra("user",value);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }, DURACION_SPLASH);
    }
}
