package com.example.rafao.proyectomoviles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rafao.proyectomoviles.Fragments.ScannerFragment;

public class Camera extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String codigo = "";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_fragment);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            codigo = "";
        } else {
            //Log.i("Camera",codigo);
            codigo = extras.getString("Codigo");
        }

        initPaging(codigo);

    }

    private void initPaging(String codigo) {
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ScannerFragment(codigo));

        ViewPager viewPager = findViewById(R.id.viewpager1);
        viewPager.setAdapter(pagerAdapter);
    }
}
