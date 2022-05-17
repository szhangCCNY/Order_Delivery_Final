package com.example.order_delivery.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.Bid;
import com.example.order_delivery.model.CompleteOrder;

import java.util.List;

/*
    This class populates a list of delivery bid for the delivery person
    delivery person sees which order are available for delivery and inputs a bid
 */
public class DeliveryBidAdapter extends RecyclerView.Adapter<DeliveryBidAdapter.ViewHolder> {
    private Context context;
    private List<CompleteOrder> item_delivery;

    public DeliveryBidAdapter(Context context, List<CompleteOrder> item_delivery){
        this.context = context;
        this.item_delivery = item_delivery;

    }
    @NonNull
    @Override
    //this class uses bid_item_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bid_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryBidAdapter.ViewHolder holder, int position) {
        CompleteOrder item = item_delivery.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return item_delivery.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //variable names for the bid_item_layout
        private TextView tvBidItemInfo;
        private TextView tvBidItemIndex;
        private EditText etBidAmount;
        private Button btnSubmitBid;
        private Bid saveBid = new Bid();
        //declare itemInfo for format string to put in textbox
        private String itemInfo = "";


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //intialize variable to correct views
            tvBidItemInfo = itemView.findViewById(R.id.tvBidItemInfo);
            tvBidItemIndex = itemView.findViewById(R.id.tvBidItemIndex);
            etBidAmount = itemView.findViewById(R.id.etBidAmount);
            btnSubmitBid = itemView.findViewById(R.id.btnSubmitBid);

        }


        public void bind(CompleteOrder item, int position) {
            tvBidItemIndex.setText("Order Number: " + (position + 1));
            itemInfo = String.format("To customer: %s\nTo Address: %s\nItems:\n%s\n Total Cost: %.2f", item.getName(), item.getAddress(), item.getFormattedList(), item.getTotal());
            tvBidItemInfo.setText(itemInfo);
            btnSubmitBid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //checks bid, if valid record bid
                    double bidAmount = (int) 0;
                    try{
                        bidAmount = Double.parseDouble(etBidAmount.getText().toString());
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(context, "Do not understand value, please input another value", Toast.LENGTH_SHORT).show();
                    }
                    if(bidAmount == 0){
                        Toast.makeText(context, "Bid needs to be greater than 0", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Bidding " + etBidAmount.getText().toString(), Toast.LENGTH_SHORT).show();
                        saveBid.setBid(bidAmount);
                        saveBid.setOrderId(item.getOrderId());
                        saveBid.setDeliveryPerson(CurrentEmployeeInfo.currentEmployeeName);
                        saveBid.saveInBackground();
                        Toast.makeText(context, "Bid Recorded. You can enter another amount.", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }
}
