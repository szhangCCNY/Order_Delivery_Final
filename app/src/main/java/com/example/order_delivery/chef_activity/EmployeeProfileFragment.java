package com.example.order_delivery.chef_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.order_delivery.LoginActivity;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/*
    This class allows Employees (chef and delivery) to see current chef information
    chef is able to see
        their username
                warnings
                salary
                title
                compliments
 */
public class EmployeeProfileFragment extends Fragment {

    private TextView tvChefUsername;
    private TextView tvChefWarnings;
    private TextView tvChefSalary;
    private TextView tvChefTitle;
    private TextView tvChefCompliments;
    private Button btnEmployeeSignOut;

    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvChefUsername = view.findViewById(R.id.tvChefUsername);
        tvChefUsername.setText(CurrentEmployeeInfo.currentEmployeeName);
        System.out.println(CurrentEmployeeInfo.currentEmployeeName);

        tvChefWarnings = view.findViewById(R.id.tvChefWarnings);
        tvChefWarnings.setText(String.valueOf(CurrentEmployeeInfo.currentEmployeeWarning));
        System.out.println(CurrentEmployeeInfo.currentEmployeeWarning);

        tvChefSalary = view.findViewById(R.id.tvChefSalary);
        tvChefSalary.setText(String.valueOf(CurrentEmployeeInfo.currentEmployeeSalary));
        System.out.println(CurrentEmployeeInfo.currentEmployeeSalary);

        tvChefTitle = view.findViewById(R.id.tvChefTitle);
        tvChefTitle.setText(CurrentEmployeeInfo.currentEmployeeTitle);
        System.out.println(CurrentEmployeeInfo.currentEmployeeTitle);

        tvChefCompliments = view.findViewById(R.id.tvChefCompliments);
        tvChefCompliments.setText(String.valueOf(CurrentEmployeeInfo.currentEmployeeCompliments));
        System.out.println(CurrentEmployeeInfo.currentEmployeeCompliments);

        btnEmployeeSignOut = view.findViewById(R.id.btnEmployeeSignOut);
        btnEmployeeSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        System.out.println("logging out");
                        // log out here
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chef_profile, container, false);
    }
}