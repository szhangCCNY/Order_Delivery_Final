package com.example.order_delivery.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.R;
import com.example.order_delivery.model.sz_customer;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.util.List;

/*
    This class populates Customer screen of the manager
    manager sees a list of customer
    has option to verify, activate, and blacklist customer
 */
public class CustListAdapter extends RecyclerView.Adapter<CustListAdapter.ViewHolder> {
    private Context context;
    private List<sz_customer> item_cust;

    public CustListAdapter(Context context, List<sz_customer> item_cust){
        this.context = context;
        this.item_cust = item_cust;

    }
    @NonNull
    @Override
    //this class uses cust_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cust_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sz_customer item = item_cust.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_cust.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //these are the variable names for cust_layout
        private TextView tvCustListName;
        private TextView tvCustListId;
        private TextView tvCustListVip;
        private TextView tvCustListVer;
        private TextView tvCustListBlacklist;
        private TextView tvCustWarning;
        private TextView tvCustBalance;
        private TextView tvCustActivate;
        private ImageButton btnBlackList;
        private ImageButton btnActivate;
        private ImageButton btnDeactivate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize the variables to the correct views
            tvCustListName = itemView.findViewById(R.id.tvCustListName);
            tvCustListId = itemView.findViewById(R.id.tvCustListId);
            tvCustListVip = itemView.findViewById(R.id.tvCustListVip);
            tvCustListVer = itemView.findViewById(R.id.tvCustListVer);
            tvCustListBlacklist = itemView.findViewById(R.id.tvCustListBlacklist);
            tvCustWarning = itemView.findViewById(R.id.tvCustWarning);
            tvCustBalance = itemView.findViewById(R.id.tvCustBalance);
            tvCustActivate = itemView.findViewById(R.id.tvCustActivate);
            btnBlackList = itemView.findViewById(R.id.btnBlackList);
            btnActivate = itemView.findViewById(R.id.btnActivate);
            btnDeactivate = itemView.findViewById(R.id.btnDeactivate);
        }


        public void bind(sz_customer item) {
            //set content
            tvCustListName.setText("User name: " +item.getUserName());
            tvCustListId.setText("Id: " + item.getUserId());
            tvCustListVip.setText("Vip Status: " + item.getVip());
            tvCustListVer.setText("Verified: " + item.getVerified());
            tvCustListBlacklist.setText("Blacklist: " + item.getBlackList());
            tvCustBalance.setText("Balance: " + item.getBalance());
            tvCustWarning.setText("Warning: " + item.getWarning());
            tvCustActivate.setText("Active Status: " + item.getActivate());

            //on activiate click, customer is activate and verified
            btnActivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setActivate(true);
                    item.setVerified(true);
                    item.setBlackList(false);
                    item.saveInBackground();
                    tvCustListBlacklist.setText("Blacklist: " + "false");
                    tvCustActivate.setText("Verified: " + "true");
                    tvCustActivate.setText("Active Status: " + "true");
                }
            });

            //on deactivate click, customer account is set not active
            btnDeactivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setActivate(false);
                    item.saveInBackground();
                    tvCustActivate.setText("Active Status: " + "false");
                }
            });

            //on blacklist click, customer is no longer activated and verified, and is blacklisted
            btnBlackList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setBlackList(true);
                    item.setActivate(false);
                    item.setVerified(false);
                    item.saveInBackground();

                    tvCustListBlacklist.setText("Blacklist: " + "true");
                    tvCustActivate.setText("Verified: " + "false");
                    tvCustActivate.setText("Active Status: " + "false");
                }
            });

        }
    }
}
