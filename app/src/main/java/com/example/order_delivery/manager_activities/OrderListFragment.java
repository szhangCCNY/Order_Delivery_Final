package com.example.order_delivery.manager_activities;

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
import com.example.order_delivery.adapters.OrderListAdapter;
import com.example.order_delivery.model.CompleteOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class shows a list of available order for manager
 */
public class OrderListFragment extends Fragment {

    private RecyclerView rvOrderList;
    private List<CompleteOrder> all_item;
    private OrderListAdapter orderListAdapter;
    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvOrderList = view.findViewById(R.id.rvOrderList);
        all_item = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getContext(), all_item);
        rvOrderList.setAdapter(orderListAdapter);
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        queryOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    public void queryOrders() {
        ParseQuery<CompleteOrder> query = ParseQuery.getQuery("CompleteOrder");
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
                orderListAdapter.notifyDataSetChanged();
            }
        });
    }
}