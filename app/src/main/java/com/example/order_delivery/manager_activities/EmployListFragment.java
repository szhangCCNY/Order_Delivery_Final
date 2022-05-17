package com.example.order_delivery.manager_activities;

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
import android.widget.Button;

import com.example.order_delivery.R;
import com.example.order_delivery.adapters.EmployListAdapter;
import com.example.order_delivery.customer_activities.fragments.WalletFragment;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Employee;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class shows a list of employees
    this class also allows navigation to add employee activity
 */
public class EmployListFragment extends Fragment {

    private RecyclerView rvEmployList;
    private EmployListAdapter employListAdaptor;
    private List<Employee> test;
    private Button btnAddEmployee;
    private String TAG = "EmployList";

    public EmployListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvEmployList = view.findViewById(R.id.rvEmployList);
        btnAddEmployee = view.findViewById(R.id.btnAddEmployee);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                // Create the CategoryDetailsFragment
                Fragment addEmployFragment = new AddEmployeeFragment();
                activity.
                        getSupportFragmentManager()
                        .beginTransaction()
                        //flmenucontainer in activity menu
                        .replace(R.id.flManagerContainer, addEmployFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        test = new ArrayList<>();

        employListAdaptor = new EmployListAdapter(getContext(), test);
        rvEmployList.setAdapter(employListAdaptor);
        rvEmployList.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employ_list, container, false);
    }

    public void queryPosts() {
        ParseQuery<Employee> query = ParseQuery.getQuery("Employee");
        query.findInBackground(new FindCallback<Employee>() {
            @Override
            public void done(List<Employee> items, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Employee item: items){
                    Log.i(TAG, "item:" + item.getName());
                }
                test.addAll(items);
                employListAdaptor.notifyDataSetChanged();
            }
        });
    }
}