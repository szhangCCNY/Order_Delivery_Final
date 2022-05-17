package com.example.order_delivery.customer_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.order_delivery.R;
import com.example.order_delivery.customer_activities.fragments.CheckoutFragment;
import com.example.order_delivery.local_model.CartItem;
import com.example.order_delivery.model.sz_item_cust;

import org.parceler.Parcels;

/*
    This activity shows the details of the menu item clicked by the user
    user is able to add/ remove quantity of an item and add to cart
 */
public class DetailActivity extends AppCompatActivity {
    private int count = 0;
    private TextView tvCount;
    private TextView tvItemNameDetail;
    private TextView tvItemDescDetail;
    private TextView tvPriceDetail;
    private ImageView ivImageDetail;
    private sz_item_cust itemTest = new sz_item_cust();
    private CartItem thisItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvCount = findViewById(R.id.tvCount);
        tvItemNameDetail = findViewById(R.id.tvItemNameDetail);
        tvItemDescDetail = findViewById(R.id.tvItemDescDetail);
        tvPriceDetail = findViewById(R.id.tvPriceDetail);
        ivImageDetail = findViewById(R.id.ivItemDetail);

        //the following code sets the require field
        //this works
        itemTest = Parcels.unwrap(getIntent().getParcelableExtra("item"));
//        thisItem = (CartItem) itemTest;
        System.out.println(itemTest.getItemName());
        tvItemNameDetail.setText(itemTest.getItemName());
        tvItemDescDetail.setText(itemTest.getItemDescription());
        tvPriceDetail.setText("$" + itemTest.getItemPrice());
        Glide.with(this).load(itemTest.getItemImage().getUrl()).into(ivImageDetail);

    }

    public void onAddCartClick(View view) {
        thisItem = new CartItem();
        thisItem.setField(itemTest.getItemName(), count, itemTest.getItemImage().getUrl(), itemTest.getItemPrice());
        //when this is click, update
        if (count > 0){
            CheckoutFragment.cartItemList.add(thisItem);
            finish();
        }
        else {
            Toast.makeText(DetailActivity.this, "Cart size:" + CheckoutFragment.cartItemList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRemoveClick(View view) {
        Toast.makeText(DetailActivity.this, "btn remove clicked", Toast.LENGTH_SHORT).show();
        if (count > 0) {
            count--;
            updateCount();
        }
        //update count text
    }

    public void onAddClick(View view) {
        Toast.makeText(DetailActivity.this, "btn add clicked", Toast.LENGTH_SHORT).show();
        if (count < 100) {
            count++;
            updateCount();
        }
        //update count text
    }

    //this just updates count
    public void updateCount(){
        tvCount.setText(Integer.toString(count));
    }

    public void onBackClick(View view) {
        Toast.makeText(DetailActivity.this, "btn back clicked", Toast.LENGTH_SHORT);
        finish();
    }
}