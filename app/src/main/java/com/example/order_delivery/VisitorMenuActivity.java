package com.example.order_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.adapters.VisitorMenuAdaptor;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    users that are not signed up can visit the visitor menu
    only get to see all restaurant items and their ratings
 */
public class VisitorMenuActivity extends AppCompatActivity {

    private RecyclerView rv_visitor_menu;
    private Button btn_return_signup;

    private VisitorMenuAdaptor visitorMenuAdaptor;
    private List<sz_item_cust> all_item;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        all_item = new ArrayList<>();
        rv_visitor_menu = findViewById(R.id.rv_visitor_menu);
        visitorMenuAdaptor = new VisitorMenuAdaptor(this, all_item);
        rv_visitor_menu.setAdapter(visitorMenuAdaptor);
        rv_visitor_menu.setLayoutManager(new LinearLayoutManager(this));
        queryPosts();
        rv_visitor_menu = findViewById(R.id.rv_visitor_menu);
        btn_return_signup = findViewById(R.id.btn_return_signup);
        btn_return_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VisitorMenuActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void queryPosts() {
        ParseQuery<sz_item_cust> query = ParseQuery.getQuery(sz_item_cust.class);
        query.findInBackground(new FindCallback<sz_item_cust>() {
            @Override
            public void done(List<sz_item_cust> items, ParseException e) {

                all_item.addAll(items);
                visitorMenuAdaptor.notifyDataSetChanged();
            }
        });
    }

}
