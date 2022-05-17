package com.example.order_delivery.chef_activity;

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

import com.example.order_delivery.R;
import com.example.order_delivery.model.CheckoutList;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This class allows chef to see current available orders from customer
 */
public class ChefOrderFragment extends Fragment {

    private ChefOrderAdapter chefOrderAdapter;
    private RecyclerView rvChefOrder;
    private List<CheckoutList> all_item;


    public ChefOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvChefOrder = view.findViewById(R.id.rvChefOrder);
        all_item = new ArrayList<>();
        queryOrders();
        chefOrderAdapter = new ChefOrderAdapter(getContext(), all_item);
        rvChefOrder.setAdapter(chefOrderAdapter);
        rvChefOrder.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_order, container, false);
    }

    //get specific item
    public void queryOrders() {
        //query current checkoutlist items from customer
        ParseQuery<CheckoutList> query = ParseQuery.getQuery("CheckoutList");
        //delivery1 test value set to current delivery person
        query.findInBackground(new FindCallback<CheckoutList>() {
            @Override
            public void done(List<CheckoutList> items, ParseException e) {
                if (e != null){
                    Log.e("Delivery Bid Activity", "Issue with getting posts", e);
                    return;
                }
                for (CheckoutList item: items){
                    Log.i("Delivery Bid Activity", "item:" + item.getFormattedList());
                }
                all_item.addAll(items);
                System.out.println(all_item.size());
                chefOrderAdapter.notifyDataSetChanged();
            }
        });
    }
}