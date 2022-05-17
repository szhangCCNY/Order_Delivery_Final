package com.example.order_delivery.chef_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.order_delivery.R;
import com.example.order_delivery.adapters.ChefMenuAdapter;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class is the chef menu screen
    allows chef to see their own menu
    also allow chef to add items on add item
 */
public class ChefMenuFragment extends Fragment {

    private ChefMenuAdapter chefMenuAdapter;
    private RecyclerView rvChefItem;
    private List<sz_item_cust> all_item;
    private Button btnChefAddItem;
    public static final int REQUEST_CODE = 99;

    public ChefMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvChefItem = view.findViewById(R.id.rvChefItem);
        btnChefAddItem = view.findViewById(R.id.btnChefAddItem);
        all_item = new ArrayList<>();
        queryChefItem();
        chefMenuAdapter = new ChefMenuAdapter(getContext(), all_item);
        rvChefItem.setAdapter(chefMenuAdapter);
        rvChefItem.setLayoutManager(new LinearLayoutManager(getContext()));
        //on add button click, go to add item activity
        btnChefAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChefAddActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //get updated menu after add
            all_item.clear();
            queryChefItem();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_menu, container, false);
    }

    //get specific item
    public void queryChefItem() {
        //query respective chef items
        ParseQuery<sz_item_cust> query = ParseQuery.getQuery("sz_item_cust");
        query.whereEqualTo("chef", CurrentEmployeeInfo.currentEmployeeName);
        //delivery1 test value set to current delivery person
        query.findInBackground(new FindCallback<sz_item_cust>() {
            @Override
            public void done(List<sz_item_cust> items, ParseException e) {
                if (e != null){
                    Log.e("Delivery Bid Activity", "Issue with getting posts", e);
                    return;
                }
                for (sz_item_cust item: items){
                    Log.i("Delivery Bid Activity", "item:" + item.getItemName());
                }
                all_item.addAll(items);
                chefMenuAdapter.notifyDataSetChanged();
            }
        });
    }
}