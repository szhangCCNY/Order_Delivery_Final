package com.example.order_delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order_delivery.R;
import com.example.order_delivery.model.Comments;

import java.util.List;

/*
    This class populates comments screen of the customer
    populates a list of comments and rating that the customer added
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private Context context;
    private List<Comments> item_comments;

    public CommentsAdapter(Context context, List<Comments> item_comments){
        this.context = context;
        this.item_comments = item_comments;

    }
    @NonNull
    @Override
    //this class uses comment_layout
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comments item = item_comments.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        //variable names for comment_layout
        private TextView tvCommentName;
        private RatingBar rbUserStar;
        private TextView tvUserComment;
        private TextView tvCommentUserStatus;
        private LinearLayout itemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initialize variables to the corresponding views
            tvCommentName = itemView.findViewById(R.id.tvCommentName);
            rbUserStar = itemView.findViewById(R.id.rbUserStar);
            tvUserComment = itemView.findViewById(R.id.tvUserComment);
            tvCommentUserStatus = itemView.findViewById(R.id.tvCommentUserStatus);
            itemContainer = itemView.findViewById(R.id.itemContainer);

        }

        public void bind(Comments item) {
            //set the content of the views
            tvCommentName.setText(item.getUsername());
            rbUserStar.setRating(item.getRating());
            tvUserComment.setText(item.getComment());
            tvCommentUserStatus.setText("User Status: " + item.getStatus());

        }
    }
}
