package com.example.order_delivery.manager_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.order_delivery.R;
import com.example.order_delivery.customer_activities.fragments.SelectMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/*
    This class allows manager to navigate through each menu icon to their respective fragments
 */
public class ManagerActivity extends AppCompatActivity {

    private BottomNavigationView ManagerBottomNavigation;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ManagerBottomNavigation = findViewById(R.id.ManagerBottomNavigation);
        ManagerBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_man_cust:
                        //update fragments
                        Toast.makeText(ManagerActivity.this, "customer clicked", Toast.LENGTH_SHORT).show();
                        fragment = new CustListFragment();
                        break;
                    case R.id.action_man_employ:
                        Toast.makeText(ManagerActivity.this, "employee clicked", Toast.LENGTH_SHORT).show();
                        fragment = new EmployListFragment();
                        break;
                    case R.id.action_man_deli:
                        //need to chagne this
                        Toast.makeText(ManagerActivity.this, "report clicked", Toast.LENGTH_SHORT).show();
                        fragment = new OrderListFragment();
                        break;
                    case R.id.action_man_message:
                        Toast.makeText(ManagerActivity.this, "bell clicked", Toast.LENGTH_SHORT).show();
                        fragment = new ManNotificationFragment();
                        break;
                    default:
                        fragment = new EmployListFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flManagerContainer, fragment).commit();
                return true;
            }
        });
        //set default
        ManagerBottomNavigation.setSelectedItemId(R.id.action_man_cust);
    }
}