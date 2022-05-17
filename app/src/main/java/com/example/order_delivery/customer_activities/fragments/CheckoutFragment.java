package com.example.order_delivery.customer_activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_delivery.LoginActivity;
import com.example.order_delivery.R;
import com.example.order_delivery.adapters.CheckoutAdapter;
import com.example.order_delivery.customer_activities.AddressActivity;
import com.example.order_delivery.local_model.CartItem;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.CheckoutList;
import com.example.order_delivery.model.TrackOrder;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

/*
    This is the checkout screen for customers
    when they order food, they are able to checkout their list
    they have the option to choose pickup or delivery
    if customer is vip
        they receive discount
    if customer is not vip, they are promoted to one after 5 orders

 */
public class CheckoutFragment extends Fragment {

    private double totalCost;
    private TextView tvTotalCost;
    private RecyclerView rvCheckoutList;
    private CheckoutAdapter adapter;
    private String address;
    private String list = "";
    private Button btnDelivery;
    private Button btnPickup;
    public static final int REQUEST_CODE = 20;

    public static ArrayList<CartItem> cartItemList = new ArrayList<>();
    public static final String TAG = "CHECKOUT Fragment";

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalCost = view.findViewById(R.id.tvTotalCost);
        rvCheckoutList = view.findViewById(R.id.rvCheckoutList);
        totalCost = 0;
        adapter = new CheckoutAdapter(getContext(), cartItemList);
        rvCheckoutList.setHasFixedSize(true);
        rvCheckoutList.setAdapter(adapter);
        rvCheckoutList.setLayoutManager(new LinearLayoutManager(getContext()));
        btnDelivery = view.findViewById(R.id.btnDelivery);
        btnPickup = view.findViewById(R.id.btnPickup);
        btnPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder(false);
            }
        });

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItemList.isEmpty()){
                    Toast.makeText(getContext(), "Please add item to cart", Toast.LENGTH_SHORT).show();
                }
                else{
                    processOrder(true);
                }
            }
        });
        setTotalCost();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    public void setTotalCost(){
        //calculate price
        String temp;
        for(int i = 0; i < cartItemList.size(); i++){
            totalCost += cartItemList.get(i).getQuantity() * cartItemList.get(i).getPrice();
            if (i != cartItemList.size() - 1){
                temp = cartItemList.get(i).getName() + ":" + cartItemList.get(i).getQuantity() + ",";
            }
            else{
                temp = cartItemList.get(i).getName() + ":" + cartItemList.get(i).getQuantity();
            }
            list += temp;
        }
        System.out.println(list);
        if(CurrentUserInfo.currentUserVip){
            totalCost *= 0.95; // 5 percent discount
            if(CurrentUserInfo.currentUserOrderCount == 3){
                //free delivery
                tvTotalCost.setText("You qualify for free delivery and your total with discount: " + "$" + Double.toString(totalCost));
            }
            else{
                tvTotalCost.setText("Your total with discount: " + "$" + Double.toString(totalCost));
            }
        }
        else{
            tvTotalCost.setText("Your total: " + "$" + Double.toString(totalCost));
        }
    }





    //this method saves checkout item into database
    public void saveCheckoutItem(boolean delivery) {
        CheckoutList checkoutList = new CheckoutList();

        checkoutList.setCustomerName(CurrentUserInfo.currentUserName);
        checkoutList.setTotal(totalCost);
        checkoutList.setDelivery(delivery);
        checkoutList.setList(list);
        if(delivery != false){
            checkoutList.setAddress(address);
        }
        checkoutList.saveInBackground();
        //on save checkout
        updateOrderCountDelivery();
        ArrayList<CartItem> temp = new ArrayList<>(cartItemList);
        updateOrderCount(temp);
        //update user order

    }

    //move method update user preference and menu count
    public void updateOrderCount(ArrayList<CartItem> list){
        //updates orderCount for both user and chef
//        ArrayList<CartItem> temp = new ArrayList<>(list);
//        System.out.println(temp.size());
        updateUserPreference(list);
        updateMenuCount(list);
    }

    public void updateMenuCount(ArrayList<CartItem> list){
        System.out.println(list.size());
        for(int i = 0; i < list.size(); i++){
            int j = i;
            ParseQuery<sz_item_cust> query = ParseQuery.getQuery(sz_item_cust.class);
            query.whereEqualTo("name", list.get(i).getName());
            query.getFirstInBackground(new GetCallback<sz_item_cust>() {
                @Override
                public void done(sz_item_cust object, ParseException e) {
                    if (e != null){
                        System.out.println("error occur when saving");
                    }
                    else{
                        object.setOrderCount(object.getOrderCount() + list.get(j).getQuantity());
                        object.saveInBackground();
                    }
                }
            });
        }
    };

    public void updateUserPreference(ArrayList<CartItem> list){
        for(int i = 0; i < list.size(); i++) {
            System.out.println(i);
            //create a local copy of cartItemList
            int j = i;
            ParseQuery<TrackOrder> query = ParseQuery.getQuery(TrackOrder.class);
            query.whereEqualTo("itemName", list.get(j).getName());
            query.whereEqualTo("username", LoginActivity.loginUsername);
            query.getFirstInBackground(new GetCallback<TrackOrder>() {
                @Override
                public void done(TrackOrder object, ParseException e) {
                    if (e != null){
                        TrackOrder order = new TrackOrder();
                        order.setItemName(list.get(j).getName());
                        order.setImageUrl(list.get(j).getImageUrl());
                        order.setUserName(LoginActivity.loginUsername);
                        order.setCount(list.get(j).getQuantity());
                        order.saveInBackground();
                    }
                    else{
                        object.setCount(object.getCount() + list.get(j).getQuantity());
                        object.saveInBackground();
                    }
                }
            });
        }

    };

    //this method keeps track and updates of userDeliveryCount
    //returns true if user qualitfies for free delivery
    //on checkout
    public void updateOrderCountDelivery(){
        if(CurrentUserInfo.currentUserVip){
            if(CurrentUserInfo.currentUserOrderCount <= 3){
                //update remote
                CurrentUserInfo.currentUser.setOrderCount(CurrentUserInfo.currentUserOrderCount + 1);
                CurrentUserInfo.currentUser.saveInBackground();
                //update locally
                CurrentUserInfo.currentUserOrderCount += 1;
            }
            else{
                //current delivery count is == 3
                //set current delivery to 0
                CurrentUserInfo.currentUser.setOrderCount(CurrentUserInfo.currentUserOrderCount + 1);
                CurrentUserInfo.currentUser.saveInBackground();
                CurrentUserInfo.currentUserOrderCount += 1;
            }
        }
        else{
            if(CurrentUserInfo.currentUser.getOrderCount() <= 5){
                //update remote
                CurrentUserInfo.currentUser.setOrderCount(CurrentUserInfo.currentUserOrderCount + 1);
                CurrentUserInfo.currentUser.saveInBackground();
                //update locally
                CurrentUserInfo.currentUserOrderCount += 1;
            }
            else{
                //current delivery count is == 5
                //set current delivery to 0
                //update vip status
                System.out.println("testing checkout fragment");
                CurrentUserInfo.currentUser.setVip(true);
                //update vip status local
                CurrentUserInfo.currentUserVip = true;
                CurrentUserInfo.currentUser.setOrderCount(CurrentUserInfo.currentUserOrderCount + 1);
                CurrentUserInfo.currentUserOrderCount += 1;
                //save remote
                CurrentUserInfo.currentUser.saveInBackground();
            }
        }
    }


//    public void onPickUpClick(View view) {
//        processOrder(false);
//    }
    public void processOrder(boolean delivery){
        if (totalCost > CurrentUserInfo.currentUserBalance) {
            Toast.makeText(getContext(), "Total cost greater than balance: Receiving a warning", Toast.LENGTH_SHORT).show();
            CurrentUserInfo.currentUserWarning += 1;
            CurrentUserInfo.currentUser.setWarning(CurrentUserInfo.currentUserWarning);
            CurrentUserInfo.currentUser.saveInBackground();
            System.out.println(CurrentUserInfo.currentUserWarning + "current warning");
        }
        else{
            CurrentUserInfo.currentUserBalance -= totalCost;
            Math.round(CurrentUserInfo.currentUserBalance);
            CurrentUserInfo.currentUser.setBalance(CurrentUserInfo.currentUserBalance);
            System.out.println(CurrentUserInfo.currentUserBalance + "current balance");
            CurrentUserInfo.currentUser.saveInBackground();
            if(delivery == true){
                Intent intent = new Intent(getContext(), AddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
            else{
                saveCheckoutItem(delivery);
                cartItemList.clear();
                totalCost = 0;
                tvTotalCost.setText("$ " + String.valueOf(totalCost));
                adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //get data from intent (address)
            //update string
            address = data.getExtras().getString("address");
            System.out.println(address + "in checkout");
            saveCheckoutItem(true);
            updateOrderCountDelivery();
            ArrayList<CartItem> temp = new ArrayList<>(cartItemList);
            updateOrderCount(temp);
            cartItemList.clear();
            totalCost = 0;
            tvTotalCost.setText("$ " + String.valueOf(totalCost));
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}