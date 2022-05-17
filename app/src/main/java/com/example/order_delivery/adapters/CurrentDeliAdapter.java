package com.example.order_delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.model.CompleteOrder;

import java.util.List;

/*
    This class populates current delivery order that is assigned to delivery
    delivery person sees delivery info
    and also has option to complete the order
 */
public class CurrentDeliAdapter extends RecyclerView.Adapter<CurrentDeliAdapter.ViewHolder> {
    private Context context;
    private List<CompleteOrder> item_current_order;

    public CurrentDeliAdapter(Context context, List<CompleteOrder> item_current_order){
        this.context = context;
        this.item_current_order = item_current_order;

    }
    @NonNull
    @Override
    //this class uses current_delivery_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.current_delivery_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompleteOrder item = item_current_order.get(position);
        holder.bind(item, position);
    }



    @Override
    public int getItemCount() {
        return item_current_order.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //variable names for current_delivery_layout
        private TextView tvCurrentDelivery;
        private TextView tvCurrentDeliveryInfo;
        private Button btnCompleteDeli;
        private RelativeLayout rlCurrentDeli;
        private String itemInfo = "";


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrentDelivery = itemView.findViewById(R.id.tvHistItemOrder);
            tvCurrentDeliveryInfo = itemView.findViewById(R.id.tvCurrentDeliveryInfo);
            btnCompleteDeli = itemView.findViewById(R.id.btnCompleteDeli);
            rlCurrentDeli = itemView.findViewById(R.id.rlCurrentDeli);
        }


        public void bind(CompleteOrder item, int position) {
            //format textbox
            tvCurrentDelivery.setText("Order Number: " + (position + 1));
            itemInfo = String.format("To customer: %s\nTo Address: %s\nItems:\n%s\n Total Cost: %.2f", item.getName(), item.getAddress(), item.getFormattedList(), item.getTotal());
            tvCurrentDeliveryInfo.setText(itemInfo);
            if(item.getDeliveryStatus() == true){
                rlCurrentDeli.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
            }
            //when delivery clicks complete, the delivery status is set to true which means order is completed
            //remove item from current delivery list
            btnCompleteDeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.getDeliveryStatus() != true){
                        item.setDeliveryStatus(true);
                        item.saveInBackground();
                        item_current_order.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Delivery complete", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context, "Item has been delivered", Toast.LENGTH_SHORT).show();
                    }
                    //check if it has been assigned
                    //this is important
                }
            });
        }
    }
}
