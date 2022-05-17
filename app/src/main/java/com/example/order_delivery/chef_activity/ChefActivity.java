package com.example.order_delivery.chef_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.order_delivery.EmployeeNotificationFragment;
import com.example.order_delivery.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/*
    The chef activity allows chef users to navigate through corresponding
    menu icons to their fragments

 */
public class ChefActivity extends AppCompatActivity {


    private BottomNavigationView ChefBottomNavigation;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        ChefBottomNavigation = findViewById(R.id.ChefBottomNavigation);
        ChefBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_chef_menu:
                        fragment = new ChefMenuFragment();
                        break;
                    case R.id.action_chef_order:
                        fragment = new ChefOrderFragment();
                        break;
                    case R.id.action_chef_profile:
                        fragment = new EmployeeProfileFragment();
                        break;
                    case R.id.action_chef_message:
                        fragment = new EmployeeNotificationFragment();
                        break;
                    default:
                        fragment = new ChefMenuFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flChefContainer, fragment).commit();
                return true;
            }
        });
        //set default
        ChefBottomNavigation.setSelectedItemId(R.id.action_chef_menu);
    }
}