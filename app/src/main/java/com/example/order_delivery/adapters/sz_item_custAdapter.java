package com.example.order_delivery.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.customer_activities.DetailActivity;
import com.example.order_delivery.R;
import com.example.order_delivery.customer_activities.fragments.CommentFragment;
import com.example.order_delivery.model.sz_item_cust;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

/*
    This class populates recyclerview with restaurant menu items
    sees a list of menu items that user is able to order and add to cart
 */
public class sz_item_custAdapter extends RecyclerView.Adapter<sz_item_custAdapter.ViewHolder> {
    private Context context;
    private List<sz_item_cust> item;

    public sz_item_custAdapter(Context context, List<sz_item_cust> item){
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
            //if item is clicked, go to detail activity
            itemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Item clicked in adapter", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    //changed test to item
                    i.putExtra("item", Parcels.wrap(item));
                    context.startActivity(i);
                }
            });
            //if comment is clicked, see comments for current item comments
            ibComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    // Create the CategoryDetailsFragment
                    Fragment menuFragment = new CommentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Code", 0);
                    bundle.putParcelable("Item", item);
                    menuFragment.setArguments(bundle);
                    activity.
                            getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flMenuContainer, menuFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
