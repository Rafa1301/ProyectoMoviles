package com.example.rafao.proyectomoviles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rafao.proyectomoviles.Fragments.CRUDFragment;
import com.example.rafao.proyectomoviles.Fragments.InventoryFragment;
import com.example.rafao.proyectomoviles.Fragments.StatesFragment;
import com.example.rafao.proyectomoviles.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PrincipalPage extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Usuario user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalpage);

        Intent i = getIntent();
        user = (Usuario)i.getSerializableExtra("user");

        toolbar =  findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);

        setIconForTabs();

    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CRUDFragment());
        adapter.addFrag(new InventoryFragment());
        if(user.admin != 0){
            adapter.addFrag(new StatesFragment());
        }
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle("Inventario");
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Levantamientos");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Usuarios");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIconForTabs() {
        int[] tabIcons = {
                R.mipmap.ic_home2,
                R.mipmap.ic_note,
                R.mipmap.ic_person,
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        if(user.admin != 0){
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        }
    }

}
