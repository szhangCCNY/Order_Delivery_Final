package com.example.order_delivery.delivery_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.order_delivery.EmployeeNotificationFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.chef_activity.EmployeeProfileFragment;
import com.example.order_delivery.customer_activities.fragments.SelectMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/*
    This class allows delivery users to navigate through the bottom menu icons to
    their corresponding fragments
 */
public class DeliveryActivity extends AppCompatActivity {

    private BottomNavigationView DeliveryBottomNav;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        DeliveryBottomNav = findViewById(R.id.DeliveryBottomNav);
        DeliveryBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_deli_bid:
                        //update fragments
                        Toast.makeText(DeliveryActivity.this, "menu clicked", Toast.LENGTH_SHORT).show();
                        fragment = new DeliBidFragment();
                        break;
                    case R.id.action_deli_hist:
                        Toast.makeText(DeliveryActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
                        fragment = new DeliHistoryFragment();
                        break;
                    case R.id.action_deli_home:
                        //need to chagne this
                        Toast.makeText(DeliveryActivity.this, "report clicked", Toast.LENGTH_SHORT).show();
                        fragment = new CurrentDeliveryFragment();
                        break;
                    case R.id.action_deli_message:
                        Toast.makeText(DeliveryActivity.this, "bell clicked", Toast.LENGTH_SHORT).show();
                        fragment = new EmployeeNotificationFragment();
                        break;
                    case R.id.action_deli_profile:
                        Toast.makeText(DeliveryActivity.this, "bell clicked", Toast.LENGTH_SHORT).show();
                        fragment = new EmployeeProfileFragment();
                        break;
                    default:
                        fragment = new SelectMenuFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flDeliveryContainer, fragment).commit();
                return true;
            }
        });
        //set default
        DeliveryBottomNav.setSelectedItemId(R.id.action_deli_bid);
    }
}