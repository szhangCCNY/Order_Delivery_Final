package com.example.order_delivery;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_delivery.adapters.HistoryAdapter;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.Response;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/*
    this class allows communication between manager and the person who received complaint
    purpose of this is so the person who received a complaint can defend themselves
    also allows a message feature between the 2
 */
public class CompResponseFragment extends Fragment {

    private RecyclerView rvResponse;
    private TextView tvResponse;
    private Button btnResponseSubmit;
    private EditText etResponse;
    private List<Response> all_item;
    private CompResponseAdapter compResponseAdapter;
    private String complaintId;

    public CompResponseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvResponse = view.findViewById(R.id.rvResponse);
        tvResponse = view.findViewById(R.id.tvResponse);
        btnResponseSubmit = view.findViewById(R.id.btnResponseSubmit);
        complaintId = getArguments().getString("complaintId");
        System.out.println(complaintId);
        all_item = new ArrayList<>();
        queryPosts();
        rvResponse.setHasFixedSize(true);
        compResponseAdapter = new CompResponseAdapter(getContext(), all_item);
        rvResponse.setAdapter(compResponseAdapter);
        etResponse = view.findViewById(R.id.etResponse);
        rvResponse.setLayoutManager(new LinearLayoutManager(getContext()));
        btnResponseSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etResponse.getText().toString().length() == 0){
                    Toast.makeText(getContext(), "please input message", Toast.LENGTH_SHORT).show();
                }
                else{
                    Response response = new Response();
                    response.setUsername(LoginActivity.loginUsername);
                    response.setMessage(etResponse.getText().toString());
                    response.setComplaintId(complaintId);
                    response.saveInBackground();
                    etResponse.setText("");
                    all_item.add(response);
                    compResponseAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comp_response, container, false);
    }

    public void queryPosts(){
        //query post on complaint id remmeber to pass complaint id from notification fragment
        ParseQuery<Response> query = ParseQuery.getQuery("Response");
        query.whereEqualTo("complaintId", complaintId);
        query.findInBackground(new FindCallback<Response>() {
            @Override
            public void done(List<Response> items, ParseException e) {
                if (e != null){
                    Log.e("Order activity", "Issue with getting posts", e);
                    return;
                }
                for (Response item: items){
                    Log.i("Order activity", "item:" + item.getUsername());
                }
                all_item.addAll(items);
                compResponseAdapter.notifyDataSetChanged();
            }
        });
    }
}