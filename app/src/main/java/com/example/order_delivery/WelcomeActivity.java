package com.example.order_delivery;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/*
    The welcome activity provides basic navigations to users that are not signed in
    here, users can navigate to signup screen, login screen, or menu screen
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
    }

    public void register(View view) {
        startActivity(new Intent(WelcomeActivity.this, SignupActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }


    public void visitorMenu(View view) {
        startActivity(new Intent(WelcomeActivity.this, VisitorMenuActivity.class));
    }
}