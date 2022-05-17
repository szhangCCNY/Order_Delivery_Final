package com.example.order_delivery.manager_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_delivery.R;

/*
    This class asks and records justification for when delivery is assigned to anyone thats not lowest bid
 */
public class JustificationActivity extends AppCompatActivity {
    private TextView tvJustifySubject;
    private EditText etJustification;
    private Button btnSubmitJust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justification);
        tvJustifySubject = findViewById(R.id.tvJustifySubject);
        etJustification = findViewById(R.id.etJustification);
        btnSubmitJust = findViewById(R.id.btnSubmitJust);
        tvJustifySubject.setText("Delivery Justification");
        btnSubmitJust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etJustification.getText().toString().length() != 0){
                    System.out.println(etJustification.getText());
                    Intent intent = new Intent();
                    intent.putExtra("justification", etJustification.getText().toString() );
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(JustificationActivity.this, "Please leave a comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}