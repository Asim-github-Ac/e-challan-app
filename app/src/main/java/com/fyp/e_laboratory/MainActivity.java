package com.fyp.e_laboratory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.fyp.e_laboratory.Fragments.ChatFragments;
import com.fyp.e_laboratory.Fragments.HomeFragments;
import com.fyp.e_laboratory.Fragments.SettingsFragments;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar =  findViewById(R.id.bottomNavigationView);
        bottomAppBar.setOnNavigationItemSelectedListener(bottomNav);
        HomeFragment();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.home:
                    fragment =new HomeFragments();
                    break;
                case R.id.chat:
                    fragment=new ChatFragments();
                    break;
                case R.id.settings:
                    fragment= new SettingsFragments();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,fragment).commit();
            return true;
        }
    };
    private void HomeFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new HomeFragments()).commit();
    }
}