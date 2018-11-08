package com.example.rafao.proyectomoviles;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafao.proyectomoviles.Fragments.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView user;
    TextView pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //user = findViewById(R.id.editText3);
        //pass = findViewById(R.id.editText4);
        initPaging();
    }

    /*public void perform_action(View v){
        String user = this.user.getText().toString();
        String pass = this.pass.getText().toString();

        mAuth.createUserWithEmailAndPassword(user,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Usuario Creado.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Usuario No Incorrecto.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    private void initPaging() {
        LoginFragment fragmentOne = new LoginFragment();
        RegisterFragment fragmentTwo= new RegisterFragment();

        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(fragmentOne);
        pagerAdapter.addFragment(fragmentTwo);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
    }

}

