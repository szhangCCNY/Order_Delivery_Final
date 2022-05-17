package com.example.order_delivery.delivery_activities;

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
import com.example.order_delivery.adapters.CurrentDeliAdapter;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.CompleteOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This class shows the corresponding current delivery for delivery person
 */
public class CurrentDeliveryFragment extends Fragment {

    private RecyclerView rvCurrentDeliveryList;
    private CurrentDeliAdapter currentDeliAdapter;
    private List<CompleteOrder> all_item;


    public CurrentDeliveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        all_item = new ArrayList<>();
        rvCurrentDeliveryList = view.findViewById(R.id.rvCurrentDeliveryList);
        currentDeliAdapter = new CurrentDeliAdapter(getContext(), all_item);
        rvCurrentDeliveryList.setAdapter(currentDeliAdapter);
        rvCurrentDeliveryList.setLayoutManager(new LinearLayoutManager(getContext()));
        queryDelivery();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_delivery, container, false);
    }
    private void queryDelivery() {
        ParseQuery<CompleteOrder> query = ParseQuery.getQuery("CompleteOrder");
        //delivery1 test value set to current delivery person
        query.whereEqualTo("deliveryPerson", CurrentEmployeeInfo.currentEmployeeName);
        query.whereEqualTo("deliveryStatus", false);
        query.findInBackground(new FindCallback<CompleteOrder>() {
            @Override
            public void done(List<CompleteOrder> items, ParseException e) {
                if (e != null){
                    Log.e("Delivery Bid Activity", "Issue with getting posts", e);
                    return;
                }
                for (CompleteOrder item: items){
                    Log.i("Delivery Bid Activity", "item:" + item.getName());
                }
                all_item.addAll(items);
                currentDeliAdapter.notifyDataSetChanged();
            }
        });
    }
}