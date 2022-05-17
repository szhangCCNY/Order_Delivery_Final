package com.example.order_delivery.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.MessageFragment;
import com.example.order_delivery.R;
import com.example.order_delivery.local_model.CurrentEmployeeInfo;
import com.example.order_delivery.local_model.CurrentUserInfo;
import com.example.order_delivery.manager_activities.BidFragment;
import com.example.order_delivery.model.CompleteOrder;

import java.util.List;


/*
    This class populates history screen of the customer
    customer sees a list of items that have been delivered or pick up
    customer can then compliments/ complaint the employee responsible for their order

 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<CompleteOrder> item_order;


    public HistoryAdapter(Context context, List<CompleteOrder> item_order) {
        this.context = context;
        this.item_order = item_order;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_layout, parent, false);
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
        private TextView tvHistoryItemNum;
        private TextView tvHistoryItemList;
        private TextView tvDeliveryMethod;
        private TextView tvEmployeeResponsible;
        private ImageButton ibThumbsUp;
        private ImageButton ibThumbsDown;
        private RelativeLayout rlHistoryItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryItemNum = itemView.findViewById(R.id.tvHistoryItemNum);
            tvHistoryItemList = itemView.findViewById(R.id.tvHistoryItemList);
            tvDeliveryMethod = itemView.findViewById(R.id.tvDeliveryMethod);
            tvEmployeeResponsible = itemView.findViewById(R.id.tvEmployeeResponsible);
            ibThumbsUp = itemView.findViewById(R.id.ibThumbsUp);
            ibThumbsDown = itemView.findViewById(R.id.ibThumbsDown);
            rlHistoryItem = itemView.findViewById(R.id.rlHistoryItem);
        }

        public void bind(CompleteOrder item) {
            tvHistoryItemNum.setText(String.valueOf(getAdapterPosition() + 1));
            tvHistoryItemList.setText(item.getFormattedList());
            tvDeliveryMethod.setText(item.getDeliveryMethod());
            if (item.getEmployeeResponsible().length() == 0){
                tvEmployeeResponsible.setText("Employee Responsible: TBD");
            }
            else{
                //each order is assigned to a employee
                //customer compliments/ report responsible employee
                tvEmployeeResponsible.setText("Employee Responsible: " + item.getEmployeeResponsible());
                ibThumbsUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "rating up " + item.getEmployeeResponsible(), Toast.LENGTH_SHORT).show();
                        //bring to compliment/ complaint fragment
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        // Create the message fragment
                        Fragment msgFragment = new MessageFragment();
                        Bundle bundle = new Bundle();
                        //get target and get get type
                        bundle.putString("type", "compliment");
                        bundle.putString("filedPerson", CurrentUserInfo.currentUserName);
                        bundle.putString("reportedPerson", item.getEmployeeResponsible());
                        bundle.putString("employeeResponsible", item.getEmployeeResponsible());
                        //start another fragment to input message
                        msgFragment.setArguments(bundle);
                        activity.
                                getSupportFragmentManager()
                                .beginTransaction()
                                //flmenucontainer in activity menu
                                .replace(R.id.flMenuContainer, msgFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
                ibThumbsDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "rating down " + item.getEmployeeResponsible(), Toast.LENGTH_SHORT).show();
                        //bring to compliment/ complaint fragment
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        // Create the message fragment
                        Fragment msgFragment = new MessageFragment();
                        Bundle bundle = new Bundle();
                        //get target and get get type
                        bundle.putString("type", "complaint");
                        bundle.putString("filedPerson", CurrentUserInfo.currentUserName);
                        bundle.putString("reportedPerson", item.getEmployeeResponsible());
                        msgFragment.setArguments(bundle);
                        activity.
                                getSupportFragmentManager()
                                .beginTransaction()
                                //flmenucontainer in activity menu
                                .replace(R.id.flMenuContainer, msgFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }
        }
    }
}