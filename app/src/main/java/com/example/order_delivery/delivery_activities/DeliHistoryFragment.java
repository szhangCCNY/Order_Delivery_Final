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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.order_delivery.NotificationAdapter;
import com.example.order_delivery.R;
import com.example.order_delivery.adapters.HistoryDeliAdapter;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.Notification;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This class shows previous items that the delivery person delivered

 */
public class DeliHistoryFragment extends Fragment {

    private RecyclerView rvDeliHistory;
    private TextView tvDeliHistory;
    private HistoryDeliAdapter historyDeliAdapter;
    private List<CompleteOrder> all_item;

    public DeliHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDeliHistory = view.findViewById(R.id.rvDeliHistory);
        tvDeliHistory = view.findViewById(R.id.tvDeliHistory);

        all_item = new ArrayList<>();

        queryPastDelivery();
        historyDeliAdapter = new HistoryDeliAdapter(getContext(), all_item);
        rvDeliHistory.setAdapter(historyDeliAdapter);
        rvDeliHistory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deli_history, container, false);
    }

    public void queryPastDelivery() {
        ParseQuery<CompleteOrder> query = ParseQuery.getQuery(CompleteOrder.class);
        query.whereEqualTo("deliveryPerson", CurrentEmployeeInfo.currentEmployeeName);
        query.whereEqualTo("deliveryStatus", true);
        query.findInBackground(new FindCallback<CompleteOrder>() {
            @Override
            public void done(List<CompleteOrder> items, ParseException e) {
                if (e != null){
                    Log.e("Comments Activity", "Issue with getting posts", e);
                    return;
                }
                for (CompleteOrder item: items){
                    Log.i("Comments Activity", "item:" + item.getName());
                }
                all_item.addAll(items);
                historyDeliAdapter.notifyDataSetChanged();
            }
        });
    }
}