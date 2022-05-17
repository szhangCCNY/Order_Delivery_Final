package com.example.order_delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    Menu is dedicated for non login users
    see all restaurant menu items and its rating
 */
public class VisitorMenuAdaptor extends RecyclerView.Adapter<VisitorMenuAdaptor.ViewHolder>{
    private Context context;
    private List<sz_item_cust> item;

    public VisitorMenuAdaptor(Context context, List<sz_item_cust> item){
        this.context = context;
        this.item = item;

    }
    @NonNull
    @Override
    //this class uses item_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivItem;
        private TextView tvItemName;
        private TextView tvDescription;
        private TextView tvPrice;
        private TextView tvRating;
        private ImageButton ibComment;
        private RelativeLayout itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            ibComment = itemView.findViewById(R.id.ibComment);
            itemContainer = itemView.findViewById(R.id.itemContainer);
        }


        public void bind(sz_item_cust item) {
            tvItemName.setText(item.getItemName());
            tvDescription.setText(item.getItemDescription());
            tvPrice.setText("$" + item.getItemPrice());
            tvRating.setText(item.getItemRating() + "/5");
            ParseFile image = item.getItemImage();

            if (image != null){
                Glide.with(context).load(item.getItemImage().getUrl()).into(ivItem);
            }
            ibComment.setVisibility(View.GONE);


        }
    }
}
