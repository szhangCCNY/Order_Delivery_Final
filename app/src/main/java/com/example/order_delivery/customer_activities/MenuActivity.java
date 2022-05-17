package com.example.order_delivery.customer_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.order_delivery.customer_activities.fragments.HistoryFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.customer_activities.fragments.CheckoutFragment;
import com.example.order_delivery.customer_activities.fragments.CustNotificationFragment;
import com.example.order_delivery.customer_activities.fragments.CustProfileFragment;
import com.example.order_delivery.customer_activities.fragments.SelectMenuFragment;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/*
    The menu activity allows bottom customer to select bottom navigation menu
    and navigate them to correct screens

    this is not for the menu items by chef
 */
public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView menuBottomNavigationView;

    final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        System.out.println(CurrentUserInfo.currentUserName);


        menuBottomNavigationView = findViewById(R.id.menuBottomNavigationView);
        menuBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_menu:
                        //update fragments
                        Toast.makeText(MenuActivity.this, "menu clicked", Toast.LENGTH_SHORT).show();
                        fragment = new SelectMenuFragment();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(MenuActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
                        fragment = new CustProfileFragment();
                        break;
                    case R.id.action_report:
                        //need to chagne this
                        Toast.makeText(MenuActivity.this, "report clicked", Toast.LENGTH_SHORT).show();
                        fragment = new HistoryFragment();
                        break;
                    case R.id.action_bell:
                        Toast.makeText(MenuActivity.this, "bell clicked", Toast.LENGTH_SHORT).show();
                        fragment = new CustNotificationFragment();
                        break;
                    case R.id.action_cart:
                        Toast.makeText(MenuActivity.this, "cart clicked", Toast.LENGTH_SHORT).show();
                        fragment = new CheckoutFragment();
                        break;
                    default:
                        fragment = new SelectMenuFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flMenuContainer, fragment).commit();
                return true;
            }
        });
        //set default
        menuBottomNavigationView.setSelectedItemId(R.id.action_menu);
    }
}