package com.example.order_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.order_delivery.chef_activity.ChefActivity;
import com.example.order_delivery.customer_activities.MenuActivity;
import com.example.order_delivery.delivery_activities.DeliveryActivity;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.manager_activities.ManagerActivity;
import com.example.order_delivery.model.Employee;
import com.example.order_delivery.model.sz_customer;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/*
    This is a basic login screen for customer, chef, delivery, and manager
    when user logs in, basic information is recorded
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private String username;
    private String password;
    public static String loginType;
    public static String loginUsername;
    public static String messageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if (ParseUser.getCurrentUser() != null) {
//            goMainActivity(ParseUser.getCurrentUser().getString("type"));
//        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user" + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    //TODO: better error handling
                    Log.e(TAG, "Issues with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: navigate to the main activity if the user has signed in properly
                loginType = user.getString("type");
                loginUsername = user.getUsername();
                goMainActivity(loginType);
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT);
            }
        });
    }

    //this method checks what type the user is and log them in accordingly
    private void goMainActivity(String type) {
        //  Log.d(TAG, "Navigating to Main Activity");
        Intent i;
        switch (type) {
            case "customer":
                messageType = "customer";
                i = new Intent(this, MenuActivity.class);

                //for warning
                Intent j = new Intent(this, WarningActivity.class);
                ParseQuery<sz_customer> query = ParseQuery.getQuery(sz_customer.class);
                query.whereEqualTo("username", username);
                query.getFirstInBackground(new GetCallback<sz_customer>() {
                    @Override
                    public void done(sz_customer object, ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Issue with getting current user", e);
                            return;
                        }
                        CurrentUserInfo currentUserInfo = new CurrentUserInfo (object);
                        //if current customer has warnings, bring them to warning screen
                        if(CurrentUserInfo.currentUserWarning >= 2 ||CurrentUserInfo.currentUser.getBlackList()){
                            startActivity(j);
                        }
                        else{
                            startActivity(i);
                        }
                    }
                });
                //add warning check
                break;
            case "chef":
                loginType = "employee";
                messageType = "chef";
                i = new Intent(this, ChefActivity.class);
                ParseQuery<Employee> query2 = ParseQuery.getQuery(Employee.class);
                query2.whereEqualTo("username", username);
                query2.getFirstInBackground(new GetCallback<Employee>() {
                    @Override
                    public void done(Employee object, ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Issue with getting current user", e);
                            return;
                        }
                        System.out.println(object.getName());
                        CurrentEmployeeInfo currentEmployeeInfo = new CurrentEmployeeInfo (object);
                        startActivity(i);
                    }
                });
                break;

            case "delivery":
                loginType = "employee";
                messageType = "delivery";
                i = new Intent(this, DeliveryActivity.class);
                ParseQuery<Employee> query3 = ParseQuery.getQuery(Employee.class);
                query3.whereEqualTo("username", username);
                query3.getFirstInBackground(new GetCallback<Employee>() {
                    @Override
                    public void done(Employee object, ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Issue with getting current user", e);
                            return;
                        }
                        System.out.println(object.getName());
                        CurrentEmployeeInfo currentEmployeeInfo = new CurrentEmployeeInfo (object);
                        startActivity(i);
                    }
                });
                break;

            case "manager":
                i = new Intent(this, ManagerActivity.class);
                startActivity(i);
                break;
        }
        finish();
    }

}