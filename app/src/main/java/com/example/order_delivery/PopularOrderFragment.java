package com.example.order_delivery;

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

import com.example.order_delivery.adapters.PopularOrderAdapter;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Notification;
import com.example.order_delivery.model.TrackOrder;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This fragment shows popular order
    shows user top items
    highest rate orders
    and popular orders
 */
public class PopularOrderFragment extends Fragment {

    private RecyclerView rvPopularItem;
    private PopularOrderAdapter popularOrderAdapter;
    private List<TrackOrder> userPopularOrder;
    private List<sz_item_cust> highRateOrder;
    private List<sz_item_cust> popularOrder;

    //keep track of when all 3 querys are completed before populating views
    private boolean[] complete;

    public PopularOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPopularItem = view.findViewById(R.id.rvPopularItem);
        complete = new boolean []{false, false, false};
        userPopularOrder = new ArrayList<>();
        highRateOrder = new ArrayList<>();
        popularOrder = new ArrayList<>();

        queryUserPopularItem();
        queryPopularItem();
        queryHighestRate();


        popularOrderAdapter = new PopularOrderAdapter(getContext(), userPopularOrder, popularOrder, highRateOrder);
        rvPopularItem.setHasFixedSize(true);
        rvPopularItem.setAdapter(popularOrderAdapter);
        rvPopularItem.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_order, container, false);
    }

    public void queryHighestRate(){
        ParseQuery<sz_item_cust> query = ParseQuery.getQuery(sz_item_cust.class);
        query.addDescendingOrder("rating");
        query.findInBackground(new FindCallback<sz_item_cust>() {
            @Override
            public void done(List<sz_item_cust> items, ParseException e) {
                complete[0] = true;
                for(int i = 0; i < items.size(); i ++){
                    highRateOrder.add(items.get(i));
                    if(highRateOrder.size() == 3){
                        break;
                    }
                    System.out.println("highest rate " + highRateOrder.get(i).getItemName());
                }
                notifyAdapter(complete);
            }
        });
    }
    public void queryPopularItem(){
        ParseQuery<sz_item_cust> query = ParseQuery.getQuery(sz_item_cust.class);
        query.addDescendingOrder("orderCount");
        query.findInBackground(new FindCallback<sz_item_cust>() {
            @Override
            public void done(List<sz_item_cust> items, ParseException e) {
                complete[1] = true;
                for(int i = 0; i < items.size(); i ++){
                    popularOrder.add(items.get(i));
                    if(popularOrder.size() == 3){
                        break;
                    }
                    System.out.println("popular order " + popularOrder.get(i).getItemName());
                }
                notifyAdapter(complete);
            }
        });
    }

    public void queryUserPopularItem(){
        ParseQuery<TrackOrder> query = ParseQuery.getQuery(TrackOrder.class);
        query.whereEqualTo("username", CurrentUserInfo.currentUserName);
        query.addDescendingOrder("count");
        query.findInBackground(new FindCallback<TrackOrder>() {
            @Override
            public void done(List<TrackOrder> items, ParseException e) {
                complete[2] = true;

                for(int i = 0; i < items.size(); i ++){
                    userPopularOrder.add(items.get(i));
                    System.out.println("user order " + userPopularOrder.get(i).getItemName());
                    if(userPopularOrder.size() == 3){
                        break;
                    }
                }
                notifyAdapter(complete);
            }
        });
    }

    //only when the querying of all 3 category is complete is when the recyclerview populates item
    public void notifyAdapter(boolean[] booleans){
        System.out.println(booleans[0] +" " + booleans[1] +" "+  booleans[2]);
        if(booleans[0] && booleans[1] && booleans[2]){
            popularOrderAdapter.notifyDataSetChanged();
        }
    }
}