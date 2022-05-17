package com.example.order_delivery.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.R;
import com.example.order_delivery.manager_activities.BidFragment;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.TrackOrder;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

/*
    This class shows a 9 items
    3 top items from user order
    3 top items from popular orders
    3 top items from highest rating
 */
public class PopularOrderAdapter extends RecyclerView.Adapter<PopularOrderAdapter.ViewHolder> {
    private Context context;
    private List<TrackOrder> userOrder = new ArrayList<>();
    private List<sz_item_cust> popularOrders = new ArrayList<>();
    private List<sz_item_cust> highRateOrder = new ArrayList<>();
    private final int REQUEST_CODE = 30;

    public PopularOrderAdapter(Context context, List<TrackOrder> userOrder , List<sz_item_cust> popularOrders, List<sz_item_cust> highRateOrder){
        this.context = context;
        this.popularOrders = popularOrders;
        this.highRateOrder = highRateOrder;
        this.userOrder = userOrder;
    }

    @NonNull
    @Override
    //this class uses popular_order_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //row 1 = user popular
        //row 2 = popular items
        //row 3 = highest rating
        if(position == 0){
            holder.bindUserPopular();
        }
        if(position == 1){
            holder.bindPopular();
        }
        if(position == 2){
            holder.bindHighRate();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPopularItem;
        private ImageView ivPopularItem1;
        private ImageView ivPopularItem2;
        private ImageView ivPopularItem3;
        private List<ImageView> ivList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPopularItem = itemView.findViewById(R.id.tvPopularItem);
            ivPopularItem1 = itemView.findViewById(R.id.ivPopItem1);
            ivPopularItem2 = itemView.findViewById(R.id.ivPopItem2);
            ivPopularItem3 = itemView.findViewById(R.id.ivPopItem3);
            ivList = new ArrayList<>();
            ivList.add(ivPopularItem1);
            ivList.add(ivPopularItem2);
            ivList.add(ivPopularItem3);
        }

        public void bindUserPopular(){
            tvPopularItem.setText("Your Popular Orders");
            for(int i = 0; i < userOrder.size(); i++){
                System.out.println(userOrder.get(i).getUserName());
                Glide.with(context).load(userOrder.get(i).getImageUrl()).into(ivList.get(i));
            }
        }

        public void bindPopular(){
            tvPopularItem.setText("Our Restaurant Popular Orders");
            for(int i = 0; i < popularOrders.size(); i++){
                ParseFile image = popularOrders.get(i).getItemImage();
                if(image != null){
                    Glide.with(context).load(image.getUrl()).into(ivList.get(i));
                }
            }
        }

        public void bindHighRate(){
            tvPopularItem.setText("Our Restaurant Highest Rating");
            for(int i = 0; i < highRateOrder.size(); i++){
                ParseFile image = highRateOrder.get(i).getItemImage();
                if(image != null){
                    Glide.with(context).load(image.getUrl()).into(ivList.get(i));
                }
            }

        }
    }
}