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
import com.example.order_delivery.adapters.DeliveryBidAdapter;
import com.example.order_delivery.model.CompleteOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class shows the current available bid for the delivery person
 */
public class DeliBidFragment extends Fragment {

    private RecyclerView rvDeliveryBid;
    private DeliveryBidAdapter deliveryBidAdapter;
    private List<CompleteOrder> all_item;

    public DeliBidFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDeliveryBid = view.findViewById(R.id.rvDeliveryBid);
        all_item = new ArrayList<>();


        deliveryBidAdapter = new DeliveryBidAdapter(getContext(), all_item);
        rvDeliveryBid.setAdapter(deliveryBidAdapter);
        rvDeliveryBid.setLayoutManager(new LinearLayoutManager(getContext()));
        queryAvailableBids();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deli_bid, container, false);
    }
    public void queryAvailableBids() {
        ParseQuery<CompleteOrder> query = ParseQuery.getQuery("CompleteOrder");
        query.whereEqualTo("deliveryPerson", "not assigned");
        query.whereEqualTo("deliveryMethod", "delivery");
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
                deliveryBidAdapter.notifyDataSetChanged();
            }
        });
    }
}