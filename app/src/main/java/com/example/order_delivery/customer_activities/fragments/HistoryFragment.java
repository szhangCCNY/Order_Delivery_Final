package com.example.order_delivery.customer_activities.fragments;

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
import com.example.order_delivery.adapters.BidAdapter;
import com.example.order_delivery.adapters.HistoryAdapter;
import com.example.order_delivery.adapters.OrderListAdapter;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Bid;
import com.example.order_delivery.model.CompleteOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This fragment allows customer to see previous completed orders
    basic feature includes seeing previous order
    compliment and complaint employees thats responsible for their order
 */
public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private List<CompleteOrder> all_item;
    private HistoryAdapter historyAdapter;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHistory = view.findViewById(R.id.rvHistory);
        all_item = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getContext(), all_item);
        rvHistory.setAdapter(historyAdapter);
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    public void queryPosts() {
        ParseQuery<CompleteOrder> query = ParseQuery.getQuery("CompleteOrder");
        query.whereEqualTo("customerName", CurrentUserInfo.currentUserName);
//        query.whereEqualTo("deliveryMethod", "pickup");
        query.findInBackground(new FindCallback<CompleteOrder>() {
            @Override
            public void done(List<CompleteOrder> items, ParseException e) {
                if (e != null){
                    Log.e("Order activity", "Issue with getting posts", e);
                    return;
                }
                for (CompleteOrder item: items){
                    Log.i("Order activity", "item:" + item.getOrderId());
                }
                all_item.addAll(items);
                historyAdapter.notifyDataSetChanged();
            }
        });
    }
}