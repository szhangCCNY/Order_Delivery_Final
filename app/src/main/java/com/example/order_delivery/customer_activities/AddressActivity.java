package com.example.order_delivery.customer_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.order_delivery.R;

/*
    this class is for when customer chooses delivery for their checkout items
    takes input address and recordds it
 */
public class AddressActivity extends AppCompatActivity {

    private EditText etAddress;
    private Button btSubmit;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        etAddress = findViewById(R.id.etAddress);
        btSubmit = findViewById(R.id.btnPickup);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = etAddress.getText().toString();
                System.out.println(address);
                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}