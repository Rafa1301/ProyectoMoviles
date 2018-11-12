package com.example.rafao.proyectomoviles;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.rafao.proyectomoviles.Fragments.LoginFragment;
import com.example.rafao.proyectomoviles.Fragments.RegisterFragment;
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

