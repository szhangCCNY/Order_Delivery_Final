package com.example.order_delivery;

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

import com.example.order_delivery.adapters.CurrentDeliAdapter;
import com.example.order_delivery.model.CompleteOrder;
import com.example.order_delivery.model.Response;

import java.util.List;

/*
    The CompResponseAdapter populates responses between manager and the person who received a complaint
    uses response model
 */
public class CompResponseAdapter extends RecyclerView.Adapter<CompResponseAdapter.ViewHolder> {
    private Context context;
    private List<Response> item_cust;

    public CompResponseAdapter(Context context, List<Response> item_cust){
        this.context = context;
        this.item_cust = item_cust;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.response_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Response item = item_cust.get(position);
        holder.bind(item, position);
    }



    @Override
    public int getItemCount() {
        return item_cust.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //tvCurrentDelivery, tvCurrentDeliveryInfo, btnCompleteDeli
        private TextView tvResponseUser;
        private TextView tvUserResponseMessage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResponseUser = itemView.findViewById(R.id.tvResponseUser);
            tvUserResponseMessage = itemView.findViewById(R.id.tvUserResponseMessage);
        }


        public void bind(Response item, int position) {
            tvResponseUser.setText(item.getUsername());
            tvUserResponseMessage.setText(item.getMessage());
        }
    }
}
