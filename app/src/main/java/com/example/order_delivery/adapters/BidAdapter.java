package com.example.order_delivery.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.model.Bid;

import java.util.List;

/*
    This java class is to populate bid content for the manager to see
    The manager assigns deliver person to a order
    if the person assigned is not lowest bid
        manager is asked to justify
 */
public class  BidAdapter extends RecyclerView.Adapter<BidAdapter.ViewHolder> {
    private Context context;
    private List<Bid> item_bid; //list of bid
    public static int positionClicked;

    //constructor for adapter
    public BidAdapter(Context context, List<Bid> item_bid){
        this.context = context;
        this.item_bid = item_bid;

    }
    @NonNull
    @Override
    //method is required by android
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //populate each view with bid layout
        View view = LayoutInflater.from(context).inflate(R.layout.bid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //method is required by android
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bid item = item_bid.get(position);
        holder.bind(item);
    }

    @Override
    //method is required by android
    public int getItemCount() {
        return item_bid.size();
    }

    //This viewholder class populates the recycler view with the correct content
    class ViewHolder extends RecyclerView.ViewHolder{
        //variable names for bid_layout
        private TextView tvBidderName;
        private TextView tvBidderPrice;
        private LinearLayout BidItemContainer;

        //initialize variable to correct view
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBidderName = itemView.findViewById(R.id.tvBidderName);
            tvBidderPrice = itemView.findViewById(R.id.tvBidderPrice);
            BidItemContainer = itemView.findViewById(R.id.BidItemContainer);
        }


        //method binds the correct content to view
        public void bind(Bid item) {
            //set content in he order
            //delivery person name
            //bid price
            //on click to return position clicked to assign delivery person for manager
            tvBidderName.setText(item.getDeliveryPerson());
            tvBidderPrice.setText(String.valueOf(item.getBid()));
            BidItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(positionClicked);
                    positionClicked = getAdapterPosition();
                }
            });
        }
    }
}
