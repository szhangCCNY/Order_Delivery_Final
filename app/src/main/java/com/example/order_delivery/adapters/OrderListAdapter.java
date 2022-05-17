package com.example.order_delivery.adapters;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.manager_activities.BidFragment;
import com.example.order_delivery.model.CompleteOrder;

import java.util.List;


/*
    This class populates all orders that are ready to pick up or for delivery for the manager
    manager completes order that requires pickup
    manager also sees bid information when clicked on items that requires delivery
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private List<CompleteOrder> item_order;
    private final int REQUEST_CODE = 30;

    public OrderListAdapter(Context context, List<CompleteOrder> item_order){
        this.context = context;
        this.item_order = item_order;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompleteOrder item = item_order.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_order.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderCustName;
        private TextView tvOrderCustAddress;
        private TextView tvOrderList;
        private TextView tvOrderPrice;
        private TextView tvAssignedTo;
        private TextView tvEmployeeResp;
        private TextView tvOrderDeliveryMethod;
        private LinearLayout OrderItemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCustName = itemView.findViewById(R.id.tvOrderCustName);
            tvOrderCustAddress = itemView.findViewById(R.id.tvOrderCustAddress);
            tvOrderList = itemView.findViewById(R.id.tvOrderList);
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvAssignedTo = itemView.findViewById(R.id.tvAssignTo);
            OrderItemContainer = itemView.findViewById(R.id.OrderItemContainer);
            tvOrderDeliveryMethod = itemView.findViewById(R.id.tvOrderDeliveryMethod);
            tvEmployeeResp = itemView.findViewById(R.id.tvEmployeeResp);
        }

        public void bind(CompleteOrder item) {
            tvOrderCustName.setText(item.getName());
            tvOrderDeliveryMethod.setText(item.getDeliveryMethod());
            tvEmployeeResp.setText("Employee responsible: " + item.getEmployeeResponsible());
            if(item.getOrderStatus() == true){
                OrderItemContainer.setBackgroundResource(R.color.green);
            }
            if(item.getDeliveryMethod().equals("pickup")){
                tvAssignedTo.setVisibility(View.GONE);
                tvOrderCustAddress.setVisibility(View.GONE);

                //order is complete set status to true
                OrderItemContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrderItemContainer.setBackgroundResource(R.color.green);
                        item.setOrderStatus(true);
                        item.saveInBackground();
                    }
                });

            }
            else{
                tvOrderCustAddress.setText("Address: " + item.getAddress());
                tvAssignedTo.setText("Assigned to: " + item.getDeliveryPerson() + "\n" + "Justification: " + item.getDeliverJust());
                OrderItemContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "item clicks: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        //when delivery item is clicked with no delivery person assigned, brings manager to a list of bids
                        if(item.getDeliveryPerson().equals("not assigned")){
                            //convert to fragment
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();

                            // Create the CategoryDetailsFragment
                            Fragment bidFragment = new BidFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("CompleteOrder", item);
                            bidFragment.setArguments(bundle);
                            activity.
                                    getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.flManagerContainer, bidFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }

            tvOrderPrice.setText("Total Cost : $" + item.getTotal());


            String[] test = item.getList().split(",");
            String order = "";
            for (String str : test) {
                String[] temp = str.split(":");
                order += "Item: " + temp[0] + " x" + temp[1] + "\n";
            }

            tvOrderList.setText(order);
            item.saveInBackground();
        }
    }
}