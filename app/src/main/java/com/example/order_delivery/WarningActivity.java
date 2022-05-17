package com.example.order_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.order_delivery.customer_activities.MenuActivity;
import com.example.order_delivery.local_model.CurrentUserInfo;

/*
    This warning activity is only shown when user meets certain warning conditions
    depending on the condition of the customer
    user sees warning depending if they are blacklisted
    kicked out of system
    they are demoted
    or just a warning

    demoted user and people who receive just a warning are allowed to return to menu screen
    else they just see warning messages and must exit by themselves
 */
public class WarningActivity extends AppCompatActivity {

    private TextView tvWarningMessage;
    private Button btnReturnMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        btnReturnMenu = findViewById(R.id.btnReturnMenu);
        tvWarningMessage = findViewById(R.id.tvWarningMessage);

        System.out.println(CurrentUserInfo.currentUser.getBlackList());
        if(CurrentUserInfo.currentUser.getBlackList()){
            tvWarningMessage.setText("YOU ARE BLACK LISTED");
            setBtnReturnMenu(false);
        }
        else{
            if(CurrentUserInfo.currentUserVip == true){
                tvWarningMessage.setText("YOU ARE DEMOTED TO REGISTERED CUSTOMER");
                CurrentUserInfo.currentUser.setWarning(0);
                CurrentUserInfo.currentUser.setVip(false);
                CurrentUserInfo.currentUser.saveInBackground();
                CurrentUserInfo.currentUserWarning = 0;
                CurrentUserInfo.currentUserVip = false;
                setBtnReturnMenu(true);
            }
            else if (CurrentUserInfo.currentUserWarning > 2 || !CurrentUserInfo.currentUser.getActivate()){
                tvWarningMessage.setText("YOU ARE KICKED OUT OF THE SYSTEM EITHER DUE TO INACTIVITY OR TOO MANY WARNINGS, BUT IS NOT BLACKLISTED. PLEASE CONTACT MANAGER ");
                setBtnReturnMenu(false);
            }
            else{
                tvWarningMessage.setText("PLEASE BEHAVE YOURSELF. YOU CURRENT HAVE "+CurrentUserInfo.currentUserWarning +"WARNINGS");
                setBtnReturnMenu(true);
            }
        }
    }

    public void setBtnReturnMenu(boolean allow){
        Intent intent = new Intent(this, MenuActivity.class);
        if(!allow){
            btnReturnMenu.setText("Exit");
            btnReturnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        else{
            btnReturnMenu.setText("Return to Menu");
            btnReturnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}