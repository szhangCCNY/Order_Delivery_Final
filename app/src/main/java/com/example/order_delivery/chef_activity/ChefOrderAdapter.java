package com.example.order_delivery.chef_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.LoginActivity;
import com.example.order_delivery.R;
import com.example.order_delivery.model.CheckoutList;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.util.List;

/*
    This populates current screen with checkout orders from customer
    chef can complete the items and remove order from list
 */
public class ChefOrderAdapter extends RecyclerView.Adapter<ChefOrderAdapter.ViewHolder> {
    private Context context;
    private List<CheckoutList> item_order;


    public ChefOrderAdapter(Context context, List<CheckoutList> item_order) {
        this.context = context;
        this.item_order = item_order;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chef_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckoutList item = item_order.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return item_order.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChefOrder;
        private TextView tvChefOrderInfo;
        private Button btnChefComplete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChefOrder = itemView.findViewById(R.id.tvChefOrder);
            tvChefOrderInfo = itemView.findViewById(R.id.tvChefOrderInfo);
            btnChefComplete = itemView.findViewById(R.id.btnChefComplete);

        }

        public void bind(CheckoutList item, int position) {

            tvChefOrder.setText(String.valueOf(position + 1));
            tvChefOrderInfo.setText(item.getFormattedList());
            btnChefComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "order complete", Toast.LENGTH_SHORT).show();
                    //save order to complete order
                    CompleteOrder order = new CompleteOrder();
                    order.setList(item.getList());
                    order.setName(item.getCustomerName());
                    order.setOrderStatus(false);
                    System.out.println(item.getDelivery());
                    if(item.getDelivery() == false){
                        order.setDeliveryMethod("pickup");
                        order.setEmployeeResponsible(LoginActivity.loginUsername);
                    }
                    else{
                        order.setDeliveryMethod("delivery");
                        order.setAddress(item.getAddress());
                    }
                    order.setTotal(item.getTotal());
                    order.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            item.deleteInBackground();
                            item_order.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }
}