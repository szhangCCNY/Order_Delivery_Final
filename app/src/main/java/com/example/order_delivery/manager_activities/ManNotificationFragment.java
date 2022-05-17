package com.example.order_delivery.manager_activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.NotificationAdapter;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.model.Notification;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/*
    This class queries messages thats for manager
 */
public class ManNotificationFragment extends Fragment {

    private RecyclerView rvNotification;
    private NotificationAdapter notificationAdapter;
    private List<Notification> all_item;
    public ManNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNotification = view.findViewById(R.id.rvNotification);
        all_item = new ArrayList<>();
        queryMessages();
        notificationAdapter = new NotificationAdapter(getContext(), all_item);
        rvNotification.setHasFixedSize(true);
        rvNotification.setAdapter(notificationAdapter);
        rvNotification.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    private void queryMessages() {
        ParseQuery<Notification> query = ParseQuery.getQuery(Notification.class);
        query.whereEqualTo("toUsername", "manager");
        query.findInBackground(new FindCallback<Notification>() {
            @Override
            public void done(List<Notification> items, ParseException e) {
                if (e != null){
                    Log.e("Comments Activity", "Issue with getting posts", e);
                    return;
                }
                for (Notification item: items){
                    Log.i("Comments Activity", "item:" + item.getToUsername());
                }
                all_item.addAll(items);
                notificationAdapter.notifyDataSetChanged();
            }
        });
    }
}