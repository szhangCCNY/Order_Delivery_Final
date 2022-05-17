package com.example.order_delivery.customer_activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.order_delivery.LoginActivity;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/*
    This fragment shows customer their profile information
    they can see their vip status, balance, warning, and is able to add money
 */
public class CustProfileFragment extends Fragment {

    private TextView tvProfileName;
    private TextView tvProfileVipStatus;
    private TextView tvProfileCurrentBalance;
    private TextView tvProfileUserWarning;
    private Button btnProfileAddMoney;
    private Button btnSignOut;
    private double addAmount;
    public static int REQUEST_CODE = 21;

    public CustProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileCurrentBalance = view.findViewById(R.id.tvProfileCurrentBalance);
        tvProfileVipStatus = view.findViewById(R.id.tvProfileVipStatus);
        tvProfileUserWarning = view.findViewById(R.id.tvProfileUserWarning);
        btnProfileAddMoney = view.findViewById(R.id.btnProfileAddMoney);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        if(getArguments() != null){
            addAmount = getArguments().getDouble("addAmount");
            System.out.println(addAmount);

        }
        else{
            System.out.println("no arguments to get");
        }

        btnProfileAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment walletFragment = new WalletFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("currentCustomer", CurrentUserInfo.currentUser);
                walletFragment.setArguments(bundle);
                activity.
                        getSupportFragmentManager()
                        .beginTransaction()
                        //flmenucontainer in activity menu
                        .replace(R.id.flMenuContainer, walletFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvProfileName.setText(CurrentUserInfo.currentUserName);
        tvProfileCurrentBalance.setText(Double.toString(CurrentUserInfo.currentUserBalance));
        tvProfileUserWarning.setText(Integer.toString(CurrentUserInfo.currentUserWarning));
        if (CurrentUserInfo.currentUserVip == true){
            tvProfileVipStatus.setText("Vip");
        }
        else {
            tvProfileVipStatus.setText("Regular");
        }
        btnSignOut.setOnClickListener(new View.OnClickListener() {
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
        return inflater.inflate(R.layout.fragment_cust_profile, container, false);
    }
}