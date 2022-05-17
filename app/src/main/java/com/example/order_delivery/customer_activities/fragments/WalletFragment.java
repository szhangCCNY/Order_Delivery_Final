package com.example.order_delivery.customer_activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.order_delivery.MessageFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.sz_customer;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;

/*
    This fragment allows customers to add money into wallet
 */
public class WalletFragment extends Fragment {

    private double amount;
    private TextInputEditText inputText;
    private Button btnAddMoney;
    private sz_customer currentCustomer;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputText = view.findViewById(R.id.input);
        btnAddMoney = view.findViewById(R.id.btnAddMoney);
        if(getArguments() != null){
            currentCustomer = getArguments().getParcelable("currentCustomer");
        }
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputText.getText().toString().length() == 0){
                    Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                }
                else{
                    //check for validity of input before commiting
                    try{
                        amount = Double.parseDouble(inputText.getText().toString());
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        // Create the CategoryDetailsFragment
                        Fragment custProfileFragment = new CustProfileFragment();
                        //update current balance locally
                        CurrentUserInfo.currentUserBalance = CurrentUserInfo.currentUserBalance + amount;
                        currentCustomer.setBalance(CurrentUserInfo.currentUserBalance);
                        currentCustomer.saveInBackground();
                        //update current balance locally
                        activity.
                                getSupportFragmentManager()
                                .beginTransaction()
                                //flmenucontainer in activity menu
                                .replace(R.id.flMenuContainer, custProfileFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getContext(), "Cannot understand value, please try again", Toast.LENGTH_SHORT).show();
                    }
                    //bring to compliment/ complaint fragment

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }
}