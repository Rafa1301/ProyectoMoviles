package com.example.rafao.proyectomoviles.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

import com.example.rafao.proyectomoviles.MainActivity;
import com.example.rafao.proyectomoviles.Models.Usuario;
import com.example.rafao.proyectomoviles.PrincipalPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SessionManagement {
    SharedPreferences pref;
    Editor editor;
    Context _context;

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String email){
        editor.putString("email", email);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(pref.contains("email")){
            Intent i = new Intent(_context, PrincipalPage.class);
            DatabaseReference root = FirebaseDatabase.getInstance().getReference();
            DatabaseReference users = root.child("Usuarios");
            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot u :dataSnapshot.getChildren()) {
                        Usuario login = u.getValue(Usuario.class);
                        if(login.correo.equals(pref.getString("email",""))){
                            i.putExtra("user",login);
                        }
                    }
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _context.startActivity(i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

}
