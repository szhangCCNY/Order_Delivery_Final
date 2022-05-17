package com.example.order_delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.R;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseFile;

import java.util.List;

/*
    This class populates items in the chef menu screen
    chef is allowed to delete menu items by long clicking menu item
 */
public class ChefMenuAdapter extends RecyclerView.Adapter<ChefMenuAdapter.ViewHolder> {
    private Context context;
    private List<sz_item_cust> item;


    public ChefMenuAdapter(Context context, List<sz_item_cust> item) {
        this.context = context;
        this.item = item;

    }

    @NonNull
    @Override
    //this class uses chef_item_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chef_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sz_item_cust item = this.item.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChefItemName;
        private TextView tvChefItemDesc;
        private TextView tvChefItemPrice;
        private ImageView ivChefItemImage;
        private RelativeLayout rlChefItemMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChefItemName = itemView.findViewById(R.id.tvChefItemName);
            tvChefItemDesc = itemView.findViewById(R.id.tvChefItemDesc);
            tvChefItemPrice = itemView.findViewById(R.id.tvChefItemPrice);
            ivChefItemImage = itemView.findViewById(R.id.ivChefItemImage);
            rlChefItemMenu = itemView.findViewById(R.id.rlChefItemMenu);

        }

        public void bind(sz_item_cust item) {
            tvChefItemName.setText(item.getItemName());
            tvChefItemDesc.setText(item.getItemDescription());
            tvChefItemPrice.setText(String.valueOf(item.getItemPrice()));
            ParseFile image = item.getItemImage();
            if (image != null){
                Glide.with(context).load(item.getItemImage().getUrl()).into(ivChefItemImage);
            }
            //delete menu item by long click
            rlChefItemMenu.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    System.out.println(getAdapterPosition());
                    item.deleteInBackground();
                    ChefMenuAdapter.this.item.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    return true;
                }
            });
        }
    }
}