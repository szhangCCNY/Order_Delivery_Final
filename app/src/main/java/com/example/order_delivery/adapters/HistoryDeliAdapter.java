package com.example.order_delivery.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.MessageFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.model.CompleteOrder;

import java.util.List;

public class HistoryDeliAdapter extends RecyclerView.Adapter<HistoryDeliAdapter.ViewHolder> {
    private Context context;
    private List<CompleteOrder> item_cust;

    public HistoryDeliAdapter(Context context, List<CompleteOrder> item_cust){
        this.context = context;
        this.item_cust = item_cust;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.deli_hist_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompleteOrder item = item_cust.get(position);
        holder.bind(item, position);
    }



    @Override
    public int getItemCount() {
        return item_cust.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //tvCurrentDelivery, tvCurrentDeliveryInfo, btnCompleteDeli
        private TextView tvDeliHistInfo;
        private TextView tvHistItemOrder;
        private ImageButton ibReportCust;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeliHistInfo = itemView.findViewById(R.id.tvDeliHistInfo);
            tvHistItemOrder = itemView.findViewById(R.id.tvHistItemOrder);
            ibReportCust = itemView.findViewById(R.id.ibReportCust);
        }


        public void bind(CompleteOrder item, int position) {
            tvDeliHistInfo.setText("Past Number: " + (position + 1));
            String itemInfo = String.format("Delivered to customer: %s\nTo Address: %s\nItems:\n%s\n Total Cost: %.2f", item.getName(), item.getAddress(), item.getFormattedList(), item.getTotal());
            tvDeliHistInfo.setText(itemInfo);
            ibReportCust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Reporting customer " + item.getName(), Toast.LENGTH_SHORT).show();
                    //bring to compliment/ complaint fragment
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    // Create the CategoryDetailsFragment
                    Fragment msgFragment = new MessageFragment();
                    Bundle bundle = new Bundle();
                    //get target and get get type
                    bundle.putString("type", "complaint");
                    bundle.putString("filedPerson", CurrentEmployeeInfo.currentEmployeeName);
                    bundle.putString("reportedPerson", item.getName());
                    msgFragment.setArguments(bundle);
                    activity.
                            getSupportFragmentManager()
                            .beginTransaction()
                            //flmenucontainer in activity menu
                            .replace(R.id.flDeliveryContainer, msgFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
