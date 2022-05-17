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
import com.example.order_delivery.adapters.CustListAdapter;
import com.example.order_delivery.model.sz_customer;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CustListFragment extends Fragment {

    private RecyclerView rvCustList;
    private CustListAdapter custListAdaptor;
    private List<sz_customer> test;
    private String TAG = "custlist";

    public CustListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCustList = view.findViewById(R.id.rvCustList);

        test = new ArrayList<>();


        custListAdaptor = new CustListAdapter(getContext(), test);
        rvCustList.setAdapter(custListAdaptor);
        rvCustList.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cust_list, container, false);
    }

    public void queryPosts() {
        ParseQuery<sz_customer> query = ParseQuery.getQuery("sz_customer");
        query.findInBackground(new FindCallback<sz_customer>() {
            @Override
            public void done(List<sz_customer> items, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (sz_customer item: items){
                    Log.i(TAG, "item:" + item.getName());
                }
                test.addAll(items);
                custListAdaptor.notifyDataSetChanged();
            }
        });
    }
}