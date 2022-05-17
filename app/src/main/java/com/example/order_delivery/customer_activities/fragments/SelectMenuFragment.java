package com.example.order_delivery.customer_activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.order_delivery.PopularOrderFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.adapters.SelectMenuAdapter;
import com.example.order_delivery.adapters.sz_item_custAdapter;
import com.example.order_delivery.model.Employee;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    This class allows customer to see a list of employees
    chef employees are clickable and brings customer to respective chef menu items
 */
public class SelectMenuFragment extends Fragment {


    private ImageButton btnHotItems;
    private RecyclerView rvSelectMenu;
    private SelectMenuAdapter selectMenuAdapter;
    private List<Employee> employeeList;
    private List<Employee> chefList;
    private List<Employee> deliList;
//    private String category;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSelectMenu = view.findViewById(R.id.rvSelectMenu);
        btnHotItems = view.findViewById(R.id.btnHotItems);
        btnHotItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment popFragment = new PopularOrderFragment();
                activity.
                        getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMenuContainer, popFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        employeeList = new ArrayList<>();
        chefList = new ArrayList<>();
        deliList = new ArrayList<>();
        selectMenuAdapter = new SelectMenuAdapter(getContext(), employeeList);
        rvSelectMenu.setAdapter(selectMenuAdapter);
        rvSelectMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        queryEmployee();
    }

    public void queryEmployee() {
        ParseQuery<Employee> query = ParseQuery.getQuery("Employee");
        query.addAscendingOrder("title");
        query.whereEqualTo("title", "chef");
        query.findInBackground(new FindCallback<Employee>() {
            @Override
            public void done(List<Employee> items, ParseException e) {
                employeeList.addAll(items);
                query.addAscendingOrder("delivery");
                query.whereEqualTo("title", "delivery");
                query.findInBackground(new FindCallback<Employee>() {
                    @Override
                    public void done(List<Employee> items, ParseException e) {
                        employeeList.addAll(items);
                        selectMenuAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_menu, container, false);
    }
}