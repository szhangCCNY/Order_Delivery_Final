package com.example.order_delivery.customer_activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.order_delivery.R;
import com.example.order_delivery.adapters.sz_item_custAdapter;
import com.example.order_delivery.model.sz_item_cust;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    private RecyclerView rvMenu;
    private sz_item_custAdapter adapter;
    private List<sz_item_cust> all_item;
//    private List<sz_item_cust> temp_item;
//    private CountDownTimer countDownTimer;
//    private String category;
    private String chefName;
//    private ViewPager viewPager;
//    private MenuPageAdapter adapterViewPager;
    public static final String TAG = "MenuFrag";
    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMenu = view.findViewById(R.id.rvMenu);
        all_item = new ArrayList<>();
//        temp_item =  new ArrayList<>();
//        category = getArguments().getString("category");
        chefName = getArguments().getString("chefName");

        adapter = new sz_item_custAdapter(getContext(), all_item);
        rvMenu.setAdapter(adapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));

        System.out.println(chefName);
        queryPosts();


//        refreshView();
//        viewPager = (ViewPager) view.findViewById(R.id.vpPager);
//        adapterViewPager = new MenuPageAdapter(getParentFragmentManager());
//        viewPager.setAdapter(adapterViewPager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void queryPosts() {
        ParseQuery<sz_item_cust> query = ParseQuery.getQuery(sz_item_cust.class);
//        query.whereEqualTo("category", category);
        query.whereEqualTo("chef", chefName);
        query.findInBackground(new FindCallback<sz_item_cust>() {
            @Override
            public void done(List<sz_item_cust> items, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (sz_item_cust item: items){
                    Log.i(TAG, "item:" + item.getItemName());
                }
                all_item.addAll(items);
                adapter.notifyDataSetChanged();
            }
        });
    }
}