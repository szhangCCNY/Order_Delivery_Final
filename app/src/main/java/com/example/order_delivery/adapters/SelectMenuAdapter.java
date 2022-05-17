package com.example.order_delivery.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.order_delivery.R;
import com.example.order_delivery.customer_activities.fragments.CommentFragment;
import com.example.order_delivery.customer_activities.fragments.MenuFragment;
import com.example.order_delivery.model.Employee;
import com.parse.ParseFile;

import java.util.List;

/*
    This class shows a menu of all employees in the restaurant
    user is able to discuss in forums or click on chef
    if chef is clicked
        brings user to a list of food menu
 */
public class SelectMenuAdapter extends RecyclerView.Adapter<SelectMenuAdapter.ViewHolder> {
    private Context context;
    private List<Employee> item_employee;
    private final int REQUEST_CODE = 30;

    public SelectMenuAdapter(Context context, List<Employee> item_employee){
        this.context = context;
        this.item_employee = item_employee;

    }
    @NonNull
    @Override
    //this class uses select_menu_layout
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_menu_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee item = item_employee.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_employee.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMenuEmployName;
        private TextView tvEmployeeMessage;
        private ImageView ivChefMenuImage;
        private ImageButton ibMenuEmployComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenuEmployName = itemView.findViewById(R.id.tvMenuEmployName);
            tvEmployeeMessage = itemView.findViewById(R.id.tvEmployeeMessage);
            ivChefMenuImage = itemView.findViewById(R.id.ivChefMenuImage);
            ibMenuEmployComment = itemView.findViewById(R.id.ibMenuEmployComment);
        }

        public void bind(Employee item) {
            tvMenuEmployName.setText(item.getName() + ":" + item.getEmployTitle());
            tvEmployeeMessage.setText("See what people are saying about " + item.getName());
            ParseFile image = item.getCateImage();
            if (image != null){
                Glide.with(context).load(item.getCateImage().getUrl()).into(ivChefMenuImage);
            }
            if(item.getEmployTitle().equals("chef")){
                ivChefMenuImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "image clicked go to respective menu", Toast.LENGTH_SHORT).show();

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();

                        //go to menu fragment
                        Fragment menuFragment = new MenuFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("chefName", item.getName());
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

            //when comment is clicked, then see comments for respective employee
            ibMenuEmployComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    Fragment menuFragment = new CommentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Code", 1);
                    bundle.putParcelable("Employee", item);
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