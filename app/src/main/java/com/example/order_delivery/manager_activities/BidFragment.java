package com.example.order_delivery.manager_activities;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.order_delivery.R;
import com.example.order_delivery.adapters.BidAdapter;
import com.example.order_delivery.model.Bid;
import com.example.order_delivery.model.CompleteOrder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class BidFragment extends Fragment {
    private RecyclerView rvBid;
    private List<Bid> all_item;
    private Button btnBidAssign;
    private BidAdapter bidAdapter;
    public static double minBid = Double.MAX_VALUE;
    private String assignedTo;
    private String justification;
    private CompleteOrder completeOrder = new CompleteOrder();
    public static final int REQUEST_CODE = 1234;

    public BidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBid = view.findViewById(R.id.rvBid);
        btnBidAssign = view.findViewById(R.id.btnBidAssign);
        all_item = new ArrayList<>();
        bidAdapter = new BidAdapter(getContext(), all_item);
        rvBid.setAdapter(bidAdapter);
        rvBid.setLayoutManager(new LinearLayoutManager(getContext()));
        completeOrder = getArguments().getParcelable("CompleteOrder");
        rvBid.setHasFixedSize(true);
        queryPosts();
        btnBidAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(all_item.size() == 0){
                    Toast.makeText(getContext(), "No one to assign", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bid temp = all_item.get(BidAdapter.positionClicked);
                    if(temp.getBid() != minBid){
                        Intent intent = new Intent(getContext(), JustificationActivity.class);
                        //ask for reason
                        startActivityForResult(intent, REQUEST_CODE);
                        assignedTo = temp.getDeliveryPerson();
                        completeOrder.setDeliveryPerson(assignedTo);
                        completeOrder.setEmployeeResponsible(assignedTo);
                        completeOrder.saveInBackground();
                        getActivity().onBackPressed();
                    }
                    else{
                        System.out.println(minBid);
                        assignedTo = temp.getDeliveryPerson();
                        System.out.println(assignedTo);
                        //save deliver yin complete order
                        completeOrder.setDeliveryPerson(assignedTo);
                        completeOrder.setEmployeeResponsible(assignedTo);
                        completeOrder.saveInBackground();
                        getActivity().onBackPressed();
                    }
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bid, container, false);
    }
    public void queryPosts() {
        //whenever queryPosts
        //refresh minBid
        minBid = Double.MAX_VALUE;
        ParseQuery<Bid> query = ParseQuery.getQuery("Bid");
        query.whereEqualTo("orderId", completeOrder.getOrderId());
        query.findInBackground(new FindCallback<Bid>() {
            @Override
            public void done(List<Bid> items, ParseException e) {
                if (e != null){
                    Log.e("Order activity", "Issue with getting posts", e);
                    return;
                }
                for (Bid item: items){
                    System.out.println(item.getBid());
                    Log.i("Order activity", "item:" + item.getBid());
                    if (item.getBid() < minBid){
                        minBid = item.getBid();
                    }

                }
                System.out.println("min bid" + minBid);
                all_item.addAll(items);
                bidAdapter.notifyDataSetChanged();
            }
        });
    }

//    public void onAssignClick(View view) {
//        if(all_item.size() == 0){
//            Toast.makeText(this, "No one to assign", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Bid temp = all_item.get(BidAdapter.positionClicked);
//            if(temp.getBid() != minBid){
//                Intent intent = new Intent(this, JustificationActivity.class);
//                //ask for reason
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//            else{
//                assignedTo = temp.getDeliveryPerson();
//                System.out.println(assignedTo);
//                //save deliver yin complete order
//                completeOrder.setDeliveryPerson(assignedTo);
//                completeOrder.saveInBackground();
//                Intent intent = new Intent(this, OrderList.class);
//                startActivity(intent);
//                finish();
//            }
//
//        }
//    }
//
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //get data from intent (address)
            //update string
            Bid temp = all_item.get(BidAdapter.positionClicked);
            justification = data.getExtras().getString("justification");
            //save item here
            System.out.println(justification);
            //save deliver yin complete order
            completeOrder.setDeliverJust(justification);
            completeOrder.saveInBackground();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}